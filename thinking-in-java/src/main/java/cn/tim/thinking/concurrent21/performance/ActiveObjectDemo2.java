package cn.tim.thinking.concurrent21.performance;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/11/22.
 * TODO 有空把@Active使用注解功能实现,将目标类型转变为活动对象
 */
public class ActiveObjectDemo2 {

    static class Computre {

        private ExecutorService exec = Executors.newSingleThreadExecutor();

        private static Random rand = new Random(47);

        private void pause(int factor) {
            try {
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(factor));
            } catch (InterruptedException e) {
                System.out.println("sleep() interrupted");
            }
        }

        public Future<?> executeTask(Task task) {
            pause(500);
            return exec.submit(task);
        }

        public void shutdown() {
            exec.shutdown();
        }

        public static void main(String[] args) {
            Computre computre = new Computre();
            for(int i = 0; i < 100; i++) {
                computre.executeTask(new PrintInfoTask());
            }

            for(int i = 0; i < 10; i++) {
                computre.executeTask(new ExecuteTask());
            }
            computre.shutdown();
        }
    }

    static abstract class Task implements Runnable {
        protected Long id;
        protected String name;

        public String getTaskName() {
            return name + "#" + id;
        }

        @Override
        public void run() {
            execute();
        }

        protected abstract void execute();
    }

    static class PrintInfoTask extends Task {
        private static long counter = 0;

        public PrintInfoTask() {
            id = counter++;
            name = "printTask";
        }

        @Override
        protected void execute() {
            System.out.println(System.currentTimeMillis() + " : " + getTaskName());
        }
    }

    static class ExecuteTask extends Task {

        private static long counter = 0;

        public ExecuteTask() {
            id = counter++;
            name = "ExecuteTask";

        }

        @Override
        protected void execute() {
            System.out.println(System.currentTimeMillis() + " : " + getTaskName());
        }
    }
}
