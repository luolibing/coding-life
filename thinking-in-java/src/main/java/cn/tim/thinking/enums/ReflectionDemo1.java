package cn.tim.thinking.enums;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by LuoLiBing on 16/12/11.
 * values()方法:
 *
 * Explor编译之后的代码可以看出:
 * 1 编译器自动给Enum类添加了final型的,所以不能被继承
 * 2 编译器自动给Enum类添加了static的values()和valueOf()方法, 由于values()和valueOf()是编译器自动添加的,
 *   所以如果向上转型为Enum是不能调用values()和valueOf(param). 可以使用Class中的getEnumConstants()方法,得到与values()一样的效果.
 * 3 Enum类枚举成员都是final static, 名字为对应的名字HERE, THERE等等
 * 4 而且还有一个static初始化子句
 * 5 所有的枚举都是继承自Enum,由于类型擦除的原因,反编译无法得到Enum的完整信息,所以得到的父类只是一个原始的Enum,而非事实上的Enum<Explore>
 *
 * final class cn.tim.thinking.enums.ReflectionDemo1$Explor extends java.lang.Enum<cn.tim.thinking.enums.ReflectionDemo1$Explor> {
     public static final cn.tim.thinking.enums.ReflectionDemo1$Explor HERE;
     public static final cn.tim.thinking.enums.ReflectionDemo1$Explor THERE;
     public static cn.tim.thinking.enums.ReflectionDemo1$Explor[] values();
     public static cn.tim.thinking.enums.ReflectionDemo1$Explor valueOf(java.lang.String);
     static {};
 }
 *
 *
 */
public class ReflectionDemo1 {

    enum Explor { HERE, THERE }

    static class Reflection {
        public static Set<String> analyze(Class<?> enumClass) {
            System.out.println("----- Analyzing " + enumClass + " -----");
            System.out.println("Interfaces: ");
            for(Type t : enumClass.getGenericInterfaces()) {
                System.out.println(t);
            }
            System.out.println("Base: " + enumClass.getSuperclass());
            System.out.println("Methods: ");
            Set<String> methods = new TreeSet<>();
            for(Method m : enumClass.getMethods()) {
                methods.add(m.getName());
            }
            System.out.println(methods);
            System.out.println("getEnumConstants() " + Arrays.toString(enumClass.getEnumConstants()));
            return methods;
        }

        public static void main(String[] args) {
            Set<String> exploreMethods = analyze(Explor.class);
            Set<String> enumMethods = analyze(Enum.class);
            System.out.println("Explore.containsAll(Enum)? " + exploreMethods.containsAll(enumMethods));
            System.out.println("Explore.removeAll(Enum) : ");
            exploreMethods.removeAll(enumMethods);
            System.out.print(exploreMethods);
        }
    }


    // enum已经继承自Enum类,不能再继承其他类,但是可以实现某个接口
    enum CartoonCharacter implements Generator<CartoonCharacter> {
        SLAPPY, SPANKY, PUNCHY, SILLY, BOUNCY, NUTTY, BOB;

        private Random rand = new Random(47);

        @Override
        public CartoonCharacter next() {
            return values()[rand.nextInt(values().length)];
        }
    }

    static class EnumImplementation {
        public static <T> void printNext(Generator<T> generator) {
            System.out.println(generator.next() + ",");
        }

        public static void main(String[] args) {
            CartoonCharacter cc = CartoonCharacter.BOB;
            for(int i = 0; i < 10; i++) {
                printNext(cc);
            }
        }
    }

    interface Generator<T> {
        T next();
    }
}
