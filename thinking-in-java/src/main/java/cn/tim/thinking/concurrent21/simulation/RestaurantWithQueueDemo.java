package cn.tim.thinking.concurrent21.simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/14.
 * 饭店仿真
 *
 */
public class RestaurantWithQueueDemo {

    // 订单
    static class Order {
        private static int counter = 0;

        private final int id = counter ++;

        private final Customer customer;

        private final WaitPerson waitPerson;

        private final Food food;

        public Order(Customer customer, WaitPerson waitPerson, Food food) {
            this.customer = customer;
            this.waitPerson = waitPerson;
            this.food = food;
        }

        public Customer getCustomer() {
            return customer;
        }

        public WaitPerson getWaitPerson() {
            return waitPerson;
        }

        public Food item() {
            return food;
        }

        @Override
        public String toString() {
            return "Order: " + id + " item: " + food + " for: " + customer + " served by: " + waitPerson;
        }
    }

    // 盘子
    static class Plate {
        private final Order order;

        private final Food food;

        public Plate(Order order, Food food) {
            this.order = order;
            this.food = food;
        }

        public Order getOrder() {
            return order;
        }

        public Food getFood() {
            return food;
        }

        @Override
        public String toString() {
            return food.toString();
        }
    }

    // 顾客
    static class Customer implements Runnable {
        private static int counter = 0;

        private final int id = counter++;

        private final WaitPerson waitPerson;

        // 没有容量的queue,put会被阻塞住,直到调用take为止,手把手递交
        private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<>();

        public Customer(WaitPerson waitPerson) {
            this.waitPerson = waitPerson;
        }

        public void deliver(Plate p) throws InterruptedException {
            placeSetting.put(p);
        }

        @Override
        public void run() {
            for(Course course : Course.values()) {
                // 每类菜单随机点一样
                Food food = course.randomSelection();
                try {
                    // 服务员接受下单
                    waitPerson.placeOrder(this, food);
                    // 等待上菜
                    System.out.println(this + " eating " + placeSetting.take());
                } catch (InterruptedException e) {
                    System.out.println(this + " waiting for " + course + " interrupted");
                    break;
                }
            }
        }

        @Override
        public String toString() {
            return "Customer " + id + " ";
        }
    }

    // 一道菜
    enum Course {

        RICE(new Food[]{Food.RICE}),
        MEAT(new Food[]{Food.RICE, Food.MEAT, Food.BREAD, Food.EAG}),
        VEGETABLE(new Food[]{Food.RICE, Food.MILK, Food.VEGETABLE}),
        SOUP(new Food[]{Food.MILK, Food.TEA});

        private Food[] foods;

        private Course(Food[] foods) {
            this.foods = foods;
        }

        private static Random rand = new Random(47);

        public Food randomSelection() {
            return foods[rand.nextInt(foods.length)];
        }
    }

    // 服务员
    static class WaitPerson implements Runnable {

        private static int counter = 0;

        private final int id = counter++;

        // 餐厅
        private final Restaurant restaurant;

        // 订单Queue
        private BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<>();

        public WaitPerson(Restaurant restaurant) {
            this.restaurant = restaurant;
        }

        /**
         * 服务员下单
         * @param customer
         * @param food
         */
        public void placeOrder(Customer customer, Food food) {
            try {
                restaurant.orders.put(new Order(customer, this, food));
            } catch (InterruptedException e) {
                System.out.println(this + " placeOrder interrupted");
            }
        }

        /**
         * 等待厨师通知菜单
         */
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Plate plate = filledOrders.take();
                    System.out.println(this + "received " + plate + " delivering to  " + plate.getOrder().getCustomer());
                    // 递送盘子
                    plate.getOrder().getCustomer().deliver(plate);
                }
            } catch (InterruptedException e) {
                System.out.println(this + " interrupted");
            }
        }

        @Override
        public String toString() {
            return "Customer " + id + " ";
        }
    }

    // 食物
    enum Food {
        RICE, MEAT, VEGETABLE, EAG, BREAD, MILK, TEA
    }

    // 餐厅
    static class Restaurant implements Runnable {
        // 服务员
        private List<WaitPerson> waitPersons = new ArrayList<>();

        // 厨师
        private List<Chef> chefs = new ArrayList<>();

        // exec
        private ExecutorService exec;

        private final static Random rand = new Random(47);

        // 订单队列
        public BlockingQueue<Order> orders = new LinkedBlockingQueue<>();

        public Restaurant(ExecutorService e, int nWaitPersons, int nChefs) {
            this.exec = e;
            // 初始化服务员
            for(int i = 0; i < nWaitPersons; i++) {
                WaitPerson waitPerson = new WaitPerson(this);
                waitPersons.add(waitPerson);
                exec.execute(waitPerson);
            }

            // 初始化厨师
            for(int i = 0; i < nChefs; i++) {
                Chef chef = new Chef(this);
                chefs.add(chef);
                exec.execute(chef);
            }
        }

        @Override
        public void run() {
            try {
                // 餐厅运营
                while (!Thread.interrupted()) {
                    // 随机服务员
                    WaitPerson wp = waitPersons.get(rand.nextInt(waitPersons.size()));
                    // 模拟顾客
                    Customer c = new Customer(wp);
                    exec.execute(c);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(200));
                }
            } catch (InterruptedException e) {
                System.out.println("Restaurant interrupted");
            }
            System.out.println("Restaurant closing");
        }

    }

    // 厨师
    static class Chef implements Runnable {
        private static int counter = 0;

        private final int id = counter++;

        // 餐厅
        private final Restaurant restaurant;

        private static Random rand = new Random(47);

        public Chef(Restaurant restaurant) {
            this.restaurant = restaurant;
        }

        @Override
        public void run() {
            try {
                // 接受订单
                while (!Thread.interrupted()) {
                    // 接单
                    Order order = restaurant.orders.take();
                    // 获取订单中的食物
                    Food food = order.item();
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                    // 将食物放到盘子中
                    Plate plate = new Plate(order, food);
                    // 将盘子放入到waitPerson中的队列中
                    order.getWaitPerson().filledOrders.put(plate);
                }
            } catch (InterruptedException e) {
                System.out.println(this + " interrupted");
            }
            System.out.println(this + " off duty");
        }

        @Override
        public String toString() {
            return "Chef " + id + " ";
        }
    }

    public static void main(String[] args) throws IOException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(exec, 5, 2);
        exec.execute(restaurant);
        System.out.println("Press 'Enter' to quit");
        System.in.read();
        exec.shutdownNow();
    }
}