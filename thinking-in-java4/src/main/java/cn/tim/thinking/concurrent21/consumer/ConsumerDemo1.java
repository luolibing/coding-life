package cn.tim.thinking.concurrent21.consumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/10/23.
 * 饭店厨师与服务员, 生产者与消费者
 *
 *
 */
public class ConsumerDemo1 {

    // 餐
    static class Meal {
        private final int orderNum;

        public Meal(int orderNum) {
            this.orderNum = orderNum;
        }

        public String toString() {
            return "Meal " + orderNum;
        }
    }

    /**
     * 服务员
     */
    static class WaitPerson implements Runnable {

        private Restaurant restaurant;

        public WaitPerson(Restaurant restaurant) {
            this.restaurant = restaurant;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    synchronized (this) {
                        // 等待厨师准备好餐
                        while (restaurant.meal == null) {
                            wait();
                        }
                    }
                    // 送餐
                    System.out.println("WaitPerson got " + restaurant.meal);

                    // 送餐完毕
                    synchronized (restaurant.chef) {
                        // 将餐置为空
                        restaurant.meal = null;
                        restaurant.chef.notifyAll(); // 通知厨师
                    }

                    // 清洁, 不能把chef.notifyAll和busyBoy.notifyAll放在一起,因为一次只能锁一个对象,所以如果没有锁住某个对象会报错
                    synchronized (restaurant.busyBoy) {
                        restaurant.clean = false;
                        restaurant.busyBoy.notifyAll(); // 通知保洁
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("WaitPerson interrupted");
            }
        }
    }

    /**
     * 厨师
     * 只有厨师才知道什么时候应该停工
     */
    static class Chef implements Runnable {

        private Restaurant restaurant;

        private int count = 0;

        public Chef(Restaurant restaurant) {
            this.restaurant = restaurant;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    synchronized (this) {
                        // 等待服务员取走餐,惯用法是使用while()循环判断条件
                        while (restaurant.meal != null) {
                            wait();
                        }
                    }

                    // 达到上限停工
                    if(++ count == 100) {
                        System.out.println("Out of food, closing");
                        // 发送中断信号给所有的线程
                        restaurant.exec.shutdownNow();
                        // 如果使用return ,最后一个菜做好之后不会被使用
                        // return;
                    }

                    // 准备餐
                    System.out.println("Order up! ");
                    synchronized (restaurant.waitPerson) {
                        restaurant.meal = new Meal(count); // 做好
                        restaurant.waitPerson.notifyAll(); // 通知服务员
                    }
                    // 就算接收到信号也要等到sleep这样的阻塞语句时才会抛出异常,跳出循环
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Chef interrupted");
            }
        }
    }

    static class BusyBoy implements Runnable {

        private final Restaurant restaurant;

        public BusyBoy(Restaurant restaurant) {
            this.restaurant =restaurant;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    synchronized (this) {
                        while (restaurant.clean) {
                            wait();
                        }
                    }

                    // 清洁
                    System.out.println("cleanup");

                    // 清洁完毕
                    synchronized (restaurant.waitPerson) {
                        restaurant.clean = true;
                        restaurant.waitPerson.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Clean interrupted");
            }
        }
    }

    /**
     * 饭店
     */
    static class Restaurant {
        private Meal meal;

        ExecutorService exec = Executors.newCachedThreadPool();

        private final Chef chef = new Chef(this);

        private boolean clean = true;

        private final WaitPerson waitPerson = new WaitPerson(this);

        private final BusyBoy busyBoy = new BusyBoy(this);

        public Restaurant() {
            exec.execute(chef);
            exec.execute(waitPerson);
            exec.execute(busyBoy);
        }

        public static void main(String[] args) {
            new Restaurant();
        }
    }
}
