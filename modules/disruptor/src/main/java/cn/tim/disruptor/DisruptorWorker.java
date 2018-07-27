package cn.tim.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: luolibing
 * Date: 2018/7/26 7:35
 */
public class DisruptorWorker {

    private int bufferSize = 128;

    private int workerSize = Runtime.getRuntime().availableProcessors() * 2;

    private WorkHandler<EventWrapper<Order>> disruptorTaskHandler() {
        return orderEventWrapper -> {
            Order order = orderEventWrapper.getData();
            if(order == null) {
                return;
            }

            if(order.getOrderId() % 2 == 0) {
                throw new IllegalArgumentException("can not handle the order " + order.getOrderId());
            }

            System.out.println("handle the order " + order.getOrderId());
        };
    }

    private ExceptionHandler<EventWrapper<Order>> exceptionHandler() {
        return new ExceptionHandler<EventWrapper<Order>>() {
            @Override
            public void handleEventException(Throwable throwable, long seq, EventWrapper<Order> orderEventWrapper) {
                Order order = orderEventWrapper.getData();
                if(order == null) {
                    return;
                }
                System.out.println("retry rabbitmq order " + order.getOrderId());
            }

            @Override
            public void handleOnStartException(Throwable throwable) {

            }

            @Override
            public void handleOnShutdownException(Throwable throwable) {

            }
        };
    }

    private EventHandler<EventWrapper<Order>> clearEventHandler() {
        return (orderEventWrapper, l, b) -> orderEventWrapper.clear();
    }

    @SuppressWarnings("unchecked")
    public Disruptor<EventWrapper<Order>> disruptor() {
        AtomicInteger atomic = new AtomicInteger(0);
        Disruptor<EventWrapper<Order>> disruptor = new Disruptor<>(
                EventWrapper::new, bufferSize, r -> {
            return new Thread(r, "worker-" + atomic.getAndIncrement());
        });
        WorkHandler<EventWrapper<Order>>[] workHandlers = new WorkHandler[workerSize];
        for(int i = 0; i < workerSize; i++) {
            workHandlers[i] = disruptorTaskHandler();
        }
        disruptor.setDefaultExceptionHandler(exceptionHandler());
        disruptor.handleEventsWithWorkerPool(workHandlers).then(clearEventHandler());
        disruptor.start();
        return disruptor;
    }

    public static void main(String[] args) throws Exception {
        DisruptorWorker disruptorWorker = new DisruptorWorker();
        Disruptor<EventWrapper<Order>> disruptor = disruptorWorker.disruptor();
        for(int i = 0; i < 1000; i++) {
            int finalI = i;
            disruptor.publishEvent((event, s) -> event.setData(new Order(finalI)));
        }
    }

    static class Order {
        long orderId;

        public Order(long orderId) {
            this.orderId = orderId;
        }

        public long getOrderId() {
            return orderId;
        }
    }

    static class EventWrapper<T> {
        T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void clear() {
            this.data = null;
        }
    }
}
