package cn.tim.thinking.concurrent21.thread;

/**
 * Created by LuoLiBing on 16/10/17.
 * 线程变种方式一
 *
 * 继承自Thread类,直接在构造函数中调用start()方法,初始化即启动
 **/
public class SimpleThreadDemo extends Thread {

    private int countDown = 5;
    private static int threadCount = 0;
    public SimpleThreadDemo() {
        super(Integer.toString(threadCount ++));
        // 构造即启动线程
        start();
    }

    public String toString() {
        return "#" + getName() + "(" + countDown + ").";
    }

    public void run() {
        while (true) {
            System.out.println(this);
            if(--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            new SimpleThreadDemo();
        }
    }
}

/**
 * 自管理线程
 */
class SelfManaged implements Runnable {
    private int countDown = 5;
    private Thread t = new Thread(this);

    public SelfManaged() {
        t.start();
    }

    public String toString() {
        return Thread.currentThread().getName() + "(" + countDown + ").";
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this);
            while (--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++) {
            new SelfManaged();
        }
    }
}
