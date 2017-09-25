package cn.tim.java9.coin;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * User: luolibing
 * Date: 2017/9/25 16:05
 */
public class NewAPI {

    @Test
    public void compactString() {
        String str = "world";
        // 新的String, 新增了一个coder用来作为编码标识
        System.out.println(str.getBytes().length);
    }

    @Test
    public void of() {
        // 返回不可变的集合对象
        Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        Map.of(1, 2, 3, 4);
        desc();
    }

    // 过时注解
    @Deprecated(forRemoval = true, since = "1.8")
    public void desc() {

    }


    @Test
    public void spinHints() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("start thread");
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(10);
        // 提示自旋等待提示，仅仅是个提示，不会产生语义操作影响
        Thread.onSpinWait();
    }


    class EventHandle {
        volatile boolean flag = false;

        public void waitFlag() {
            while (!flag) {
                // 自旋等待
                Thread.onSpinWait();
            }
            execute();
        }

        public void execute() {
            System.out.println("execute");
        }
    }


    @Test
    public void stackWalker() {
        // java9 以前获取调用方法栈的方式
        new RuntimeException().getStackTrace();

        StackWalker.getInstance().forEach(
                System.out::println
        );
    }


    /**
     * java9以前properties是使用iso-8859-1作为字符集，而java9采用utf-8
     * @throws IOException
     */
    @Test
    public void properties() throws IOException {
        Properties properties = new Properties();
        try(InputStream in = getClass().getClassLoader().getResourceAsStream("coin.properties")) {
            properties.load(new InputStreamReader(in, "UTF-8"));
            System.out.println(properties.get("name"));
        }
    }
}
