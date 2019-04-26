package cn.hotspot.classloader;

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
}
