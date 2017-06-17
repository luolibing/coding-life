package cn.tim;

import java.util.concurrent.ExecutionException;

/**
 * Created by LuoLiBing on 15/8/8.
 */
public class ExecutorTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for(int i=0; i<10; i++) {
            Thread th = new Thread(new ExecuteThread());
            th.start();
        }
    }

    static class ExecuteThread implements Runnable {
        public void run() {
            ExecutorImpl executor = new ExecutorImpl();
            for(int i=0; i<40; i++) {
                Entity entity = null;
                try {
                    entity = executor.execute();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(entity.getId() + ":" + entity.getMsg());
            }
        }
    }

}
