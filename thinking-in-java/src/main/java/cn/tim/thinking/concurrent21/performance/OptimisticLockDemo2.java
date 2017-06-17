package cn.tim.thinking.concurrent21.performance;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LuoLiBing on 16/11/21.
 * 乐观加锁
 * 1 Atomic乐观加锁
 *   Atomic类允许执行所谓的"乐观加锁",意味着执行某项操作时,实际上没有使用互斥,但是当计算完成,
 *   并且准备更新这个Atomic对象时,使用一个compareAndSet()方法,将旧值和新值一起提交给这个方法,
 *   如果旧值和它在Atomic对象中的不一致,则这个操作失败(意味着其他任务在操作执行期间修改了这个对象).
 *   之所以称为乐观锁,是因为这个地方我们是"乐观的",我们操作这个数据的时候并没有锁定数据,并且希望没有任何其他任务插入并且修改数据.
 *   当产生冲突时,可用的解决方式有两种: 1 重试,当存在较多的写者时,重试的几率会很大 2 忽略,以报错的形式抛出
 *   乐观加锁适合于写操作较少的情况.
 *
 */
public class OptimisticLockDemo2 {

    static class FastSimulation {
        static final int N_ELEMENTS = 100_000;

        static final int N_GENES = 30;

        static final int N_EVOLVERS = 2;

        static final AtomicInteger[][] GRID = new AtomicInteger[N_ELEMENTS][N_GENES];

        static Random rand = new Random(47);

        static class Evolver implements Runnable {

            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    int element = rand.nextInt(N_ELEMENTS);
                    for(int i = 0; i < N_GENES; i++) {

                        int previous = element - 1;
                        if(previous < 0) {
                            previous = N_ELEMENTS - 1;
                        }
                        int next = element + 1;
                        if(next >= N_ELEMENTS) {
                            next = 0;
                        }

                        // 获取旧值
                        int oldValue = GRID[element][i].get();
                        // 计算新值
                        int newValue = oldValue + GRID[previous][i].get() + GRID[next][i].get();
                        newValue /= 3;

                        // 乐观替换
                        if(!GRID[element][i].compareAndSet(oldValue, newValue)) {
                            System.out.println("Old value changed from " + oldValue);
                        }

//                        int oldValue, newValue;
//                        do {
//                            int previous = element - 1;
//                            if(previous < 0) {
//                                previous = N_ELEMENTS - 1;
//                            }
//                            int next = element + 1;
//                            if(next >= N_ELEMENTS) {
//                                next = 0;
//                            }
//
//                            // 获取旧值
//                            oldValue = GRID[element][i].get();
//                            // 计算新值
//                            newValue = oldValue + GRID[previous][i].get() + GRID[next][i].get();
//                            newValue /= 3;
//                        } while (!GRID[element][i].compareAndSet(oldValue, newValue));
                    }
                }
            }
        }

        public static void main(String[] args) throws IOException {
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i < N_ELEMENTS; i++) {
                for(int j = 0; j < N_GENES; j++) {
                    GRID[i][j] = new AtomicInteger(rand.nextInt(1000));
                }
            }

            for(int i = 0; i < N_EVOLVERS; i++) {
                exec.execute(new Evolver());
            }
            System.in.read();
            exec.shutdownNow();
        }
    }
}
