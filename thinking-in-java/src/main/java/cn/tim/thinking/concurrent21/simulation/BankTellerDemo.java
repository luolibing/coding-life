package cn.tim.thinking.concurrent21.simulation;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/14.
 * 银行收纳员仿真
 */
public class BankTellerDemo {

    // 顾客
    static class Customer {
        private static int counter = 0;
        private final int id = counter++;
        private final int serviceTime;
        public Customer(int serviceTime) {
            this.serviceTime = serviceTime;
        }

        public int getServiceTime() {
            return serviceTime;
        }

        @Override
        public String toString() {
            return "Customer" + id + " serviceTime=[" + serviceTime + "] ";
        }
    }

    // 顾客队列
    static class CustomerLine extends ArrayBlockingQueue<Customer> {

        public CustomerLine(int maxSize) {
            super(maxSize);
        }

        @Override
        public String toString() {
            if(size() == 0) {
                return "Empty";
            }
            StringBuilder sb = new StringBuilder();
            this.forEach(sb::append);
            return sb.toString();
        }
    }

    // 顾客生成器
    static class CustomerGenerator implements Runnable {

        private CustomerLine customerLine;

        private Random rand = new Random(47);

        public CustomerGenerator(CustomerLine customerLine) {
            this.customerLine = customerLine;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                    customerLine.put(new Customer(rand.nextInt(5000)));
                }
            } catch (InterruptedException e) {
                System.out.println("停止继续排队");
            }
        }
    }

    // 收纳员
    static class Teller implements Runnable, Comparable<Teller> {

        private static int counter = 0;

        private final int id = counter++;

        private int servedCount = 0;

        private CustomerLine customerLine;

        private boolean servingCustomerLine = true;

        public Teller(CustomerLine customerLine) {
            this.customerLine = customerLine;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Customer customer = customerLine.take();
                    // 模拟服务
                    TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                    synchronized (this) {
                        servedCount ++;
                        // 判断服务状态,如果是做其他事情等待
                        while (!servingCustomerLine) {
                            wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(this + " interrupted");
            }
            System.out.println(this + " 下班了");
        }

        // 关闭服务窗口做其他事情,例如整理资料等等
        public synchronized void doSomethingElse() {
            servingCustomerLine = false;
            servedCount = 0;
        }

        // 开启服务窗口
        public synchronized void serveCustomerLine() {
            assert !servingCustomerLine : "already serving " + this;
            servingCustomerLine  = true;
            notifyAll();
        }

        @Override
        public String toString() {
            return "Teller " + id + ";";
        }

        public String shortString() {
            return "T" + id;
        }

        // 需要对收纳元进行排序
        @Override
        public int compareTo(Teller o) {
            return servedCount > o.servedCount ? 1 : (servedCount < o.servedCount ? -1 : 0);
        }
    }

    // 收纳管理
    static class TellerManager implements Runnable {

        private ExecutorService exec;

        private BlockingQueue<Teller> workingTellers = new PriorityBlockingQueue<>();

        private Queue<Teller> doSomethingElseTellers = new LinkedList<>();

        private final int adjustmentTime;

        private final CustomerLine customerLine;

        TellerManager(ExecutorService exec, int adjustmentTime, CustomerLine customerLine, int initTellerSize) {
            this.exec = exec;
            this.adjustmentTime = adjustmentTime;
            this.customerLine = customerLine;

            // 刚开始的时候会有一个初始化的收纳员个数
            for(int i = 0; i < initTellerSize; i++) {
                Teller teller = new Teller(customerLine);
                exec.execute(teller);
                workingTellers.add(teller);
            }
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    // 每隔一段时间调整一次
                    TimeUnit.MILLISECONDS.sleep(adjustmentTime);
                    adjustTellerNumber();
                    System.out.println(customerLine + " {");
                    for(Teller teller : workingTellers) {
                        System.out.print(teller.shortString() + " ");
                    }
                    System.out.println("}");
                }
            } catch (InterruptedException e) {
                System.out.println(this + " interrupted");
            }
            System.out.println("下班停止调度");
        }

        // 调整收纳员工作,智能控制的部分
        public void adjustTellerNumber() {
            // 排队的数量大于服务收纳员2倍时增加收纳员
            if(customerLine.size() / workingTellers.size() > 2) {
                // 有在做其他事情的收纳员推上服务台
                if(doSomethingElseTellers.size() > 0) {
                    Teller teller = doSomethingElseTellers.remove();
                    teller.serveCustomerLine();
                    workingTellers.offer(teller);
                    return;
                }

                // 否则,召唤收纳员
                Teller teller = new Teller(customerLine);
                exec.execute(teller);
                workingTellers.add(teller);
                return;
            }

            // 如果排队的人足够少,可以撤下一个收纳员,做其他的事情
            if(doSomethingElseTellers.size() > 1 && customerLine.size() / workingTellers.size() < 2) {
                reassignOneTeller();
            }

            // 如果没有人排队,只需要留下一个收纳员即可
            if(customerLine.size() == 0) {
                while (workingTellers.size() > 1) {
                    reassignOneTeller();
                }
            }
        }

        // 撤下收纳员做其他的事情
        private void reassignOneTeller() {
            Teller teller = workingTellers.poll();
            teller.doSomethingElse();
            doSomethingElseTellers.offer(teller);
        }
    }

    public static void main(String[] args) throws IOException {
        final int MAX_LINE_SIZE = 50;
        final int ADJUSTMENT_PERIOD = 1000;
        final int INIT_TELLER_SIZE = 2;
        ExecutorService exec = Executors.newCachedThreadPool();
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        exec.execute(new CustomerGenerator(customers));
        exec.execute(new TellerManager(exec, ADJUSTMENT_PERIOD, customers, INIT_TELLER_SIZE));
        System.out.println("Press 'Enter' to quit");
        System.in.read();
        exec.shutdownNow();
    }
}
