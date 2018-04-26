package cn.tim.thinking.concurrent21.thread;

/**
 * Created by LuoLiBing on 16/10/8.
 * 并发的多面性
 * 1 更快的执行,在单处理器上,因为阻塞的存在,并发能够提升cpu的使用率,提高吞吐量
 * 2 改进代码设计
 *
 * 基本的线程机制
 * 将程序划分为多个分离的独立运行的任务.每个任务都由执行线程来驱动
 *
 * Runnable接口只有一个方法,即run()
 */
public class RunnableDemo {

    /**
     * 线程可以驱动任务,因此需要一种描述任务的方式,这个由Runnable接口来提供
     * 使用静态的taskCount来对初始化的实例进行记录,其中的id是通过taskCount自增得来
     * Thread.yield()的调用是对线程调度器的一种建议,它在声明"我已经执行完生命周期中最重要的部分了,此刻正是切换给其他任务执行一段时间的大好时机"
     */
    public static class LiftOff implements Runnable {

        protected int countDown = 5;

        private static int taskCount = 0;

        private final int id = taskCount++;

        public LiftOff() {}

        public LiftOff(int countDown) {
            this.countDown = countDown;
        }

        public String status() {
            return "threadId: " + Thread.currentThread().getId() + ", #" + id + "(" + (countDown > 0 ? countDown : "Liftoff!" + ").");
        }

        @Override
        public void run() {
            while (countDown-- > 0) {
                System.out.println(status());
                Thread.yield();
            }
        }
    }

    /**
     * 使用Runnable的run方法启动,并不会产生任何内在的线程能力.要实现线程行为,必须显示地讲一个任务附着在线程上.
     * @param args
     */
    public static void main(String[] args) {
        // main函数和LiftOff.run()运行在同一个线程内
        System.out.println("mainThreadId: " + Thread.currentThread().getId());
        LiftOff launch = new LiftOff();
        launch.run();
    }

    static class RunnableDemo1 implements Runnable {

        private static int count = 0;

        private final int id  = count++;

        public RunnableDemo1() {
            System.out.println("start id=" + id);
        }

        @Override
        public void run() {
            System.out.println(id + "run1");
            Thread.yield();
            System.out.println(id + "run2");
            Thread.yield();
            System.out.println(id + "run3");
            System.out.println("end id=" + id);
        }

        public static void main(String[] args) {
            for(int i = 0; i < 3; i++) {
                new Thread(new RunnableDemo1()).start();
            }
        }
    }
}
