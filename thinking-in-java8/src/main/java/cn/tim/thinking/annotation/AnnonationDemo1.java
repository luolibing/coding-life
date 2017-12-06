package cn.tim.thinking.annotation;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by LuoLiBing on 17/1/7.
 * 注解
 */
public class AnnonationDemo1 {

    /**
     * 注解(也被称为元数据)
     * JAVASE5内置的三种注解:
     * @Override @Deprecated @SuppressWarnings
     *
     * 元注解: @Target和@Retention
     * @Target 是用来定义你的注解将应用在什么地方(例如方法或者一个域). CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE
     * @Retention 用来定义该注解在哪一个级别可用, 例如在源码中(SOURCE), 类文件中(CLASS)或者运行时(RUNTIME).
     * @Documented 将此注解包含在Javadoc中
     * @Inherited 允许子类继承父类中的注解
     * 在注解中, 一般都会包含一些元素以表示某些值. 当分析处理注解时, 程序或工具可以利用这些值. 注解的元素看起来像接口的方法, 只不过可以指定默认值.
     * 没有元素的注解称为标记注解(marker annotation), 例如@Test
     *
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UseCase {
        int id();
        String description() default "no description";
    }

    class PasswordUtils {
        @UseCase(id = 47, description = "Passwords must contain at least one numeric")
        public boolean validatePassword(String password) {
            return (password.matches("\\w*\\d\\w*"));
        }

        @UseCase(id = 48)
        public String encryptPassword(String password) {
            return new StringBuilder(password).reverse().toString();
        }

        @UseCase(id = 49, description = "New password can't equal previously used ones")
        public boolean checkForNewPassword(List<String> prevPasswords, String password) {
            return ! prevPasswords.contains(password);
        }
    }

    public static class UseCaseTracker {
        public static void trackUseCases(List<Integer> useCases, Class<?> clazz) {
            // 获取到这个类的所有方法, 然后获取到方法上对应的注解@UseCase, 然后就可以获取注解对象上的元素了
            for(Method m : clazz.getDeclaredMethods()) {
                UseCase uc = m.getAnnotation(UseCase.class);
                // 如果没有被UseCase标注, 则返回null
                if(uc != null) {
                    System.out.println("Found Use Case : " + uc.id() + " " + uc.description());
                    useCases.remove(new Integer(uc.id()));
                }
            }

            for(int i : useCases) {
                System.out.println("Warning: Missing use case-" + i);
            }
        }

        public static void main(String[] args) {
            List<Integer> useCases = new ArrayList<>();
            Collections.addAll(useCases, 47, 48, 49, 50);
            trackUseCases(useCases, PasswordUtils.class);
        }
    }


    /**
     * 标注注解
     *
     * 注解元素可用的类型有如下:
     * 所有的基本类型(int, float, boolean等)
     * String
     * Class
     * enum
     * Annotation (允许注解嵌套)
     * 以及以上类型数组
     *
     * 默认值限制
     * 默认值不能有不确定的值, 也就是说, 元素必须要么具有默认值, 要么在使用注解时提供元素的值.
     * 对于非基本类型的元素, 默认值和自定义值都不允许为Null, 我们只能用空字符串或者负数来表示某个元素不存在.
     *
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SimulatingNull {
        int id() default -1;
        String description() default "";
    }


    @Test
    public void time() {
        LocalTime localTime = LocalTime.of(19, 0, 0);
        System.out.println(localTime);
    }

}
