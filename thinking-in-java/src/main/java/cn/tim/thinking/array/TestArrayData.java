package cn.tim.thinking.array;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/26.
 */
public class TestArrayData {

    static class Banana {
        private static int counter;
        private int id = counter++;

        @Override
        public String toString() {
            return "Banana: [" + id + "]";
        }
    }

    /**
     * Arrays.fill(): 用同一个值填充各个位置, 如果是对象, 就是复制同一个引用进行填充.
     */
    @Test
    public void fill() {
        int size = 6;
        byte[] b = new byte[size];
        Arrays.fill(b, 0, size, (byte)1);
        System.out.println(Arrays.toString(b));

        Banana banana = new Banana();
        Banana[] bananas = new Banana[size];
        Arrays.fill(bananas, banana);
        System.out.println(Arrays.toString(bananas));

        banana.id = 10;
        System.out.println("after update Banana's id");
        System.out.println(Arrays.toString(bananas));
    }


    /**
     * 数据生成器, 测试数组生成器
     * @param <T>
     */
    public interface Generator<T> {
        T next();
    }

    // 使用内部类, 生成各种类型的数据
    public static class CountingGenerator {
        public static class Boolean implements Generator<java.lang.Boolean> {
            private boolean value = false;
            @Override
            public java.lang.Boolean next() {
                value = !value;
                return value;
            }
        }

        public static class Byte implements Generator<java.lang.Byte> {
            private byte value = 0;
            @Override
            public java.lang.Byte next() {
                return value++;
            }
        }

        public static class Character implements Generator<java.lang.Character> {

            static char[] chars = ("abcdefghijklmnopqrstuvwxyz".toCharArray());

            int index = -1;

            @Override
            public java.lang.Character next() {
                index = (index + 1) % chars.length;
                return chars[index];
            }
        }

        public static class Integer implements Generator<java.lang.Integer> {
            private int value = 0;
            @Override
            public java.lang.Integer next() {
                return value++;
            }
        }
    }

    static class generatorsTest {
        public static int size = 10;
        // 利用反射找出某个类中所有的public内部类, 而这里的内部类又都实现了Generator接口, 所以可以向上转型, 统一调用next()方法.
        public static void test(Class<?> surroundingClass) {
            for(Class<?> type : surroundingClass.getClasses()) {
                System.out.println(type.getSimpleName() + ": ");
                try {
                    Generator<?> g = (Generator<?>) type.newInstance();
                    for(int i = 0; i < size; i++) {
                        System.out.printf(g.next() + " ");
                    }
                    System.out.println();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
            test(CountingGenerator.class);
        }
    }


    /**
     * 随机数生成器
     */
    public static class RandomGenerator {
        private static Random r = new Random(47);

        public static class Boolean implements Generator<java.lang.Boolean> {

            @Override
            public java.lang.Boolean next() {
                return r.nextBoolean();
            }
        }

        public static class Character implements Generator<java.lang.Character> {

            @Override
            public java.lang.Character next() {
                return CountingGenerator.Character.chars[
                        r.nextInt(CountingGenerator.Character.chars.length)];
            }
        }

        public static class Integer implements Generator<java.lang.Integer> {

            private int mod = 10000;

            @Override
            public java.lang.Integer next() {
                return r.nextInt(mod);
            }
        }
    }

    @Test
    public void randomTest() {
        generatorsTest.test(RandomGenerator.class);
    }


    public static class CollectionData<T> {
        private List<T> data;

        public CollectionData(Generator<T> generator, int size) {
            data = new ArrayList<>();
            for(int i = 0; i < size; i++) {
                data.add(generator.next());
            }
        }

        public T[] toArray(T[] t) {
            return data.toArray(t);
        }
    }

    /**
     * 生成随机数组
     */
    public static class Generated {
        public static <T> T[] array(T[] a, Generator<T> gen) {
            return new CollectionData<T>(gen, a.length).toArray(a);
        }

        // 使用反射动态创建具有恰当类型和数量的新数组.
        public static <T> T[] array(Class<T> type, Generator<T> gen, int size) throws IllegalAccessException, InstantiationException {
            T[] t = (T[]) Array.newInstance(type, size);
            return new CollectionData<T>(gen, size).toArray(t);
        }
    }

    @Test
    public void testGenerated() throws InstantiationException, IllegalAccessException {
        Integer[] a = {9, 8, 7, 6};
        System.out.println(Arrays.toString(a));
        a = Generated.array(a, new CountingGenerator.Integer());
        System.out.println(Arrays.toString(a));

        Integer[] b = Generated.array(Integer.class, new CountingGenerator.Integer(), 10);
        System.out.println(Arrays.toString(b));
    }


    /**
     * 泛型不能用于基本类型, 所以将包装类型转换为相应的基本类型数组需要手动创建转换器.
     */
    static class ConvertTo {
        public static int[] primitive(Integer[] in) {
            int[] result = new int[in.length];
            for(int i = 0; i < in.length; i++) {
                result[i] = in[i]; // 自动拆箱
            }
            return result;
        }
    }

    @Test
    public void primitiveConversionDemonstration() throws InstantiationException, IllegalAccessException {
        Integer[] a = Generated.array(Integer.class, new CountingGenerator.Integer(), 10);
        int[] b = ConvertTo.primitive(a);
        System.out.println(Arrays.toString(b));
    }
}
