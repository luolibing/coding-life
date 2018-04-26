package cn.tim.thinking.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/21.
 * 不可变String:
 * String对象是不可变的, 每一个看起来会修改String值的方法, 实际上都是创建了一个全新的String对象, 以包含修改后的字符串内容. 也可以说String只是可读的.
 */
public class StringDemo1 {

    static class Immutable {
        // 一旦upcase()运行结束,s就消失了. upcase()的返回值,其实也只是最终结果的引用
        // !!! 对于一个方法而言,参数是为该方法提供信息的,而不是想让该方法改变自己的.!!!
        public static String upcase(String s) {
            return s.toUpperCase();
        }

        public static void main(String[] args) {
            String q = "howdy";
            System.out.println(q);
            // q传递给upcase()方法时, 实际传递的是引用的一个拷贝.
            // 每当把String对象作为方法的参数时,都会复制一份引用,
            // 而该引用所指的对象起始一直待在单一的物理位置上, 从未动过.
            String qq = upcase(q);
            System.out.println(qq);
            System.out.println(q);
        }
    }

    /**
     * 重载 "+"与StringBuilder
     * String重载了操作符+, java不允许程序员虫子啊任何操作符
     * 字符串拼接的字节码
     *
     0: ldc           #2                  // String mango
     2: astore_1
     3: new           #3                  // class java/lang/StringBuilder
     6: dup
     7: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
     10: ldc           #5                  // String abc
     12: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
     15: aload_1
     16: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
     19: ldc           #7                  // String def
     21: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
     24: bipush        47
     26: invokevirtual #8                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
     29: invokevirtual #9                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
     32: astore_2
     33: getstatic     #10                 // Field java/lang/System.out:Ljava/io/PrintStream;
     36: aload_2
     37: invokevirtual #11                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     40: return
     *
     * 编译器自动创建了StringBuilder对象来append字符串,并且最终调用toString()方法,生成结果, 对字符串的拼接进行了优化. 但是能够优化到什么程度, 值得商榷.
     *
     */
    @Test
    public void concateation() {
        String mango = "mango";
        String s = "abc" + mango + "def" + 47;
        System.out.println(s);
    }



    /**
     * 采用+重载的方式进行循环拼接, 会在循环体内部, 每次都构造一个StringBuilder对象, 然后append(), 最后再toString().
     * 这样每循环一次, 就会走一遍这样的流程, 创建一个新的StringBuilder对象. 这样性能会差一些
     */
    @Test
    public void implicit() {
        String result = "";
        String[] array = {"aa", "bb", "cc", "dd"};
        for(int i = 0; i < array.length; i++) {
            result += array[i];
        }
        System.out.println(result);
    }


    /**
     * 而采用显式的StringBuilder时, 循环部分代码更简短,简单, 而且只会生成一个StringBuilder对象,
     * StringBuilder允许你预先为其制定大小, 避免多次重新分配缓冲.
     *
     * 总结: !!!如果编写比较简单的字符串拼接, 可以使用+操作符, 编译器会帮助优化, 但是如果需要使用循环,那么最好使用自建的StringBuilder对象!!!
     */
    @Test
    public void explicit() {
        StringBuilder result = new StringBuilder();
        String[] array = {"aa", "bb", "cc", "dd"};
        for(int i = 0; i < array.length; i++) {
            result.append(array[i]);
        }
        System.out.println(result.toString());
    }


    /**
     * 如果我们想走捷径使用  builder.append(a + ":" + b); 那么编译器又会调入陷阱, 从而为你另外创建一个StringBuilder对象处理括号内的字符串操作
     */
    @Test
    public void usingStringBuilder() {
        Random rand = new Random(47);
        StringBuilder result = new StringBuilder("[");
        for(int i = 0; i < 25; i++) {
            // result.append(rand.nextInt(100) + ", "); 使用这种方式, 还不如直接使用+操作符, 捷径走不得
            result.append(rand.nextInt(100));
            result.append(", ");
        }
        result.delete(result.length() -2, result.length());
        result.append("]");
        System.out.println(result);
    }


    class Coffee {

    }


    // List的toString()会循环调用Coffee.toString()方法, 然后拼接
    @Test
    public void arrayListDisplay() {
        List<Coffee> coffees = new ArrayList<>();
        coffees.add(new Coffee());
        coffees.add(new Coffee());
        coffees.add(new Coffee());
        coffees.add(new Coffee());
        System.out.println(coffees);
    }


    /**
     * 无意识递归
     */
    class InfiniteRecursion {
        @Override
        public String toString() {
            // + this, 会自动转换为 + this.toString(), 然后又回到了这个类的toString(), 这样就形成了无限递归
            return "InfiniteRecursion address : " + this + "\n";
        }
    }

    @Test
    public void recursion() {
        System.out.println(new InfiniteRecursion());
    }

    class InfiniteRecursion1 {
        @Override
        public String toString() {
            // 这个地方需要调用Object.toString()打印出对象地址, 而不是使用this
            return "InfiniteRecursion address : " + super.toString() + "\n";
        }
    }

    @Test
    public void recursion1() {
        System.out.println(new InfiniteRecursion1());
    }

    @Test
    public void bytes() {
        byte[] bytes = "abcd!".getBytes();
        for(byte b : bytes) {
            System.out.println(b);
        }
    }
}
