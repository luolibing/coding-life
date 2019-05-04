package cn.hotspot.classloader;

import org.junit.Test;

import java.lang.invoke.LambdaMetafactory;

/**
 * Created by luolibing on 2019/4/26.
 */
public class Lambda {


    /**
     * lambda的实现原理
     * InnerClasses:
         public static final #62= #61 of #65; //Lookup=class java/lang/invoke/MethodHandles$Lookup of class java/lang/invoke/MethodHandles
         BootstrapMethods:
         0: #34 invokestatic java/lang/invoke/LambdaMetafactory.metafactory:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
         Method arguments:
         #35 ()V
         #36 invokestatic cn/hotspot/classloader/Lambda.lambda$main$0:()V
         #35 ()V

     * @param action
     */
    public void execute(Runnable action) {
        action.run();
    }

    public static void main(String[] args) {
        new Lambda().execute(() -> {
            System.out.println("haha");
        });

        System.out.println(Lambda.class.getClasses());
        System.out.println(Lambda.class.getDeclaredClasses());
    }


    /**
     * https://www.infoq.com/articles/Java-8-Lambdas-A-Peek-Under-the-Hood
     * 为什么不是内部类
     * 匿名内部类性能低下，需要编译出一个内部类文件，并且在使用的时候才会加载这个类。还会占用大量的内存
     *
     * stack=3, locals=3, args_size=1
     *          0: new           #11                 // class cn/hotspot/classloader/Lambda$1
     *          3: dup
     *          4: aload_0
     *          5: invokespecial #12                 // Method cn/hotspot/classloader/Lambda$1."<init>":(Lcn/hotspot/classloader/Lambda;)V
     *          8: astore_1
     *          9: aload_1
     *         10: ldc           #13                 // String 123
     *         12: invokeinterface #14,  2           // InterfaceMethod cn/hotspot/classloader/Lambda$Function.apply:(Ljava/lang/Object;)Ljava/lang/Object;
     *         17: checkcast     #15                 // class java/lang/Integer
     *         20: astore_2
     *         21: getstatic     #7                  // Field java/lang/System.out:Ljava/io/PrintStream;
     *         24: aload_2
     *         25: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
     *         28: return
     *
     */
    @Test
    public void anonymous() {
        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.valueOf(s);
            }
        };
        Integer apply = function.apply("123");
        System.out.println(apply);
    }


    /**
     * 实现的过程
     * 1 通过invokeDynamic指令调用LambdaMetafactory#metafactory生成调用指令 CallSite
     * 2 invokeinterface 调用
     */
    @Test
    public void lambda() {
        Func add = new LambdaTest$$Lambda$1();
        System.out.println(add.exec(1, 2));
    }


    private static int lambda$main$0(int x, int y) {
        return x + y;
    }

    static final class LambdaTest$$Lambda$1 implements Func {
        private LambdaTest$$Lambda$1() {
        }

        public int exec(int x, int y) {
            return lambda$main$0(x, y);
        }
    }

    @FunctionalInterface
    interface Func {
        int exec(int x, int y);
    }
}
