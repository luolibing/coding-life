package cn.tim.thinking.initialize5;

/**
 * Created by LuoLiBing on 16/8/15.
 */
public class GcTest {
    public static void main(String[] args) throws Throwable {
        try {
            Gc gc = new Gc(1);
            // 适当的处理
            gc.allocateMemory();
            // 没有引用,会调用gc & finalize方法
            new Gc(2);

            for(int i = 0; i< 10000; i++) {
                // 适当的处理
                new Gc(i);
            }
            // System.gc()用于强制进行终结动作,即使不这么做,通过重复地执行程序,最终也能被触发
            System.gc();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        TimeUnit.MINUTES.sleep(2);
    }
}
