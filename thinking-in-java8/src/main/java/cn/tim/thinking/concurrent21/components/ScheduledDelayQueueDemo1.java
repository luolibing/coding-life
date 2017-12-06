package cn.tim.thinking.concurrent21.components;

import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/12.
 * 使用DelayQueue代替ScheduledExecutor定时任务调度器
 * 能实现定时任务,但是要实现fixedRate这种方式有点麻烦
 *
 */
public class ScheduledDelayQueueDemo1 {

    // 延迟任务
    abstract class Event implements Runnable, Delayed {
        protected final long delayTime;
        private long trigger;

        protected Event(long delayTime) {
            this.delayTime = delayTime;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        // 时间排序
        public int compareTo(Delayed arg) {
            Event that = (Event) arg;
            return trigger < that.trigger ? -1 : (trigger > that.trigger ? 1 : 0);
        }

        // 支持重新启动,设置开始时间
        public void start() {
            trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS);
        }
    }

    // 控制器
    static class Controller implements Runnable {

        private DelayQueue<Event> queue;

        public Controller(DelayQueue<Event> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Event event = queue.take();
                    System.out.println(event);
                    event.run();
                }
            } catch (InterruptedException e) {

            }
            System.out.println("Finished Controller");
        }

        public void addEvent(Event e) {
            e.start();
            queue.put(e);
        }
    }

    // 总控
    class GreenhouseControls extends Controller {

        public GreenhouseControls(DelayQueue<Event> queue) {
            super(queue);
        }

        private boolean light;

        class LightOn extends Event {

            protected LightOn(long delayTime) {
                super(delayTime);
            }

            @Override
            public void run() {
                light = true;
            }

            @Override
            public String toString() {
                return "Light is on";
            }
        }

        class LightOff extends Event {

            protected LightOff(long delayTime) {
                super(delayTime);
            }

            @Override
            public void run() {
                light = false;
            }

            @Override
            public String toString() {
                return "Light is off";
            }
        }

        private boolean water;

        class WaterOn extends Event {

            protected WaterOn(long delayTime) {
                super(delayTime);
            }

            @Override
            public void run() {
                water = true;
            }

            @Override
            public String toString() {
                return "Turning greenhouse water on";
            }
        }

        class WaterOff extends Event {

            protected WaterOff(long delayTime) {
                super(delayTime);
            }

            @Override
            public void run() {
                water = false;
            }

            @Override
            public String toString() {
                return "Turning greenhouse water off";
            }
        }


        class Bell extends Event {

            protected Bell(long delayTime) {
                super(delayTime);
            }

            @Override
            public void run() {
                addEvent(this);
            }

            @Override
            public String toString() {
                return "Bing!";
            }
        }

        // 重新启动
        class Restart extends Event {

            private Event[] events;

            protected Restart(long delayTime, Event[] events) {
                super(delayTime);
                this.events = events;
                for(Event e : events) {
                    addEvent(e);
                }
            }

            @Override
            public void run() {
                for(Event e : events) {
                    addEvent(e);
                }
                addEvent(this);
            }

            @Override
            public String toString() {
                return "Restarting system";
            }
        }

        // 终止程序
        class Terminate extends Event {

            private ExecutorService exec = Executors.newCachedThreadPool();

            protected Terminate(long delayTime, ExecutorService exec) {
                super(delayTime);
                this.exec = exec;
            }

            @Override
            public void run() {
                exec.shutdownNow();
            }

            @Override
            public String toString() {
                return "Terminating";
            }
        }

//        public static void main(String[] args) {
//            ExecutorService exec = Executors.newCachedThreadPool();
//            DelayQueue<Event> queue = new DelayQueue<>();
//            GreenhouseControls gc = new GreenhouseControls(queue);
//            gc.addEvent(gc.new Bell(900));
//            Event[] events = {
//                    gc.new LightOn(200),
//                    gc.new LightOff(400),
//                    gc.new WaterOn(600),
//                    gc.new WaterOff(800)
//            };
//            gc.addEvent(gc.new Restart(2000, events));
//            exec.execute(gc);
//        }
    }

}
