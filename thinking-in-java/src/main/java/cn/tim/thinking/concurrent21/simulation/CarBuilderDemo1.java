package cn.tim.thinking.concurrent21.simulation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by LuoLiBing on 16/11/17.
 * 汽车组装: 任务分发
 */
public class CarBuilderDemo1 {

    // 汽车
    static class Car {
        private final int id;

        // 工序标识
        private boolean engine = false, driveTrain = false, wheels = false;

        public Car(int id) {
            this.id = id;
        }

        public synchronized void addEngine() {
            this.engine = true;
        }

        public synchronized void addDriveTrain() {
            this.driveTrain = true;
        }

        public synchronized void addWheels() {
            this.wheels = true;
        }

        @Override
        public String toString() {
            return "Car[ id = " + id +", engine = " + engine + ", driveTrain = " + driveTrain + ", wheels = " + wheels + "];";
        }
    }

    // 汽车队列
    static class CarQueue extends LinkedBlockingQueue<Car> {}

    // 底盘装配,造车的开始
    static class ChassisBuilder implements Runnable {

        private static int counter = 0;

        private final CarQueue carQueue;

        public ChassisBuilder(CarQueue carQueue) {
            this.carQueue = carQueue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    Car car = new Car(counter++);
                    System.out.println("ChassisBuilder created " + car);
                    carQueue.put(car);
                }
            } catch (InterruptedException e) {
                System.out.println("CarQueue interrupted");
            }
            System.out.println("ChassisBuilder off");
        }
    }

    // 组装车间
    static class Assembler implements Runnable {

        private final CarQueue chassisQueue, finishedQueue;

        private final CyclicBarrier barrier = new CyclicBarrier(4);

        private final RobotPool robotPool;

        private Car car;

        public Assembler(CarQueue chassisQueue, CarQueue finishedQueue, RobotPool robotPool) {
            this.chassisQueue = chassisQueue;
            this.finishedQueue = finishedQueue;
            this.robotPool = robotPool;
        }

        @Override
        public void run() {
            // 组装工序
            try {
                while (!Thread.interrupted()) {
                    // 从汽车基础库中获取汽车
                    car = chassisQueue.take();
                    // 开始组装工序
                    robotPool.hire(EngineRobot.class, this);
                    robotPool.hire(DriveTrainRobot.class, this);
                    robotPool.hire(WheelRobot.class, this);
                    // 等待3个工序都完成
                    barrier.await();
                    // 完成之后将car添加到finishedQueue
                    finishedQueue.put(car);
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting Assembler via Interrupt");
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            // 流水线关闭
            System.out.println("Assembler off");
        }

        public CyclicBarrier barrier() {
            return barrier;
        }

        public Car car() {
            return car;
        }
    }

    // 机器人池管理机器人,分配机器人,并且启动; 归还Robot对象
    static class RobotPool {
        private Set<Robot> robots = new HashSet<>();

        // 往池里面添加
        public synchronized void add(Robot robot) {
            robots.add(robot);
            notifyAll();
        }

        // 雇佣一个机器人干活
        public synchronized void hire(Class<? extends Robot> robotType, Assembler assembler) throws InterruptedException {
            for(Robot robot : robots) {
                // 判断类型
                if(robotType.isInstance(robot)) {
                    // 从池中移除出来
                    robots.remove(robot);
                    // 绑定流水线
                    robot.assignAssembler(assembler);
                    // 开始工作
                    robot.engage();
                    return;
                }
            }
            // 如果没有获取到,则继续等待直到有机器人位置
            wait();
            hire(robotType, assembler);
        }

        // 工作完毕归还
        public synchronized void release(Robot robot) {
            robots.add(robot);
        }
    }

    // 报告器
    static class Reporter implements Runnable {

        private CarQueue carQueue;

        public Reporter(CarQueue carQueue) {
            this.carQueue = carQueue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.println(carQueue.take());
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting Reporter via interrpt");
            }
            System.out.println("Reporter off");
        }
    }

    // 机器人,机器人一上来先停电,然后等待召唤唤醒
    public static abstract class Robot implements Runnable {
        private RobotPool robotPool;

        // 流水线
        protected Assembler assembler;

        private boolean engage = false;

        public Robot(RobotPool robotPool) {
            this.robotPool = robotPool;
        }

        public Robot assignAssembler(Assembler assembler) {
            this.assembler = assembler;
            return this;
        }

        // 开始工作
        public synchronized void engage() {
            this.engage = true;
            notifyAll();
        }

        // 提供的服务
        protected abstract void performService();

        @Override
        public void run() {
            // 机器人默认是停机状态,等待召唤
            try {
                powerDown();
                while (!Thread.interrupted()) {
                    // 提供服务
                    performService();
                    // 本工序完成,通知栅栏
                    assembler.barrier.await();
                    // 完成工作,继续powerDown等待召唤
                    powerDown();
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting " + this + " via interrupt");
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            System.out.println(this + " off");
        }

        // 停机
        private synchronized void powerDown() throws InterruptedException {
            // 停止服务
            engage = false;
            // 不再归属某个流水线
            assembler = null;
            // 归还给RobotPool
            robotPool.release(this);
            // 一直等待,直到再次被开启
            while (!engage) {
                wait();
            }
        }

        @Override
        public String toString() {
            return getClass().getName();
        }
    }

    // 引擎机器人
    public static class EngineRobot extends Robot {

        public EngineRobot(RobotPool robotPool) {
            super(robotPool);
        }

        @Override
        protected void performService() {
            System.out.println(this + " installing egine");
            assembler.car.addEngine();
        }
    }

    //
    public static class DriveTrainRobot extends Robot {

        public DriveTrainRobot(RobotPool robotPool) {
            super(robotPool);
        }

        @Override
        protected void performService() {
            System.out.println(this + " drive train");
            assembler.car.addDriveTrain();
        }
    }

    // 安装轮子机器人
    public static class WheelRobot extends Robot {

        public WheelRobot(RobotPool robotPool) {
            super(robotPool);
        }

        @Override
        protected void performService() {
            System.out.println(this + " add wheels");
            assembler.car.addWheels();
        }
    }

    public static void main(String[] args) throws IOException {
        CarQueue chassisQueue = new CarQueue(), finishQueue = new CarQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        RobotPool robotPool = new RobotPool();
        exec.execute(new EngineRobot(robotPool));
        exec.execute(new DriveTrainRobot(robotPool));
        exec.execute(new WheelRobot(robotPool));
        exec.execute(new Assembler(chassisQueue, finishQueue, robotPool));
        exec.execute(new Reporter(finishQueue));
        exec.execute(new ChassisBuilder(chassisQueue));
        System.in.read();
        exec.shutdownNow();
    }
}
