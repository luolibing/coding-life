package cn.tim.thinking.clazz14.error;

import org.junit.Test;

/**
 * Created by LuoLiBing on 17/3/9.
 */
public class ClassNotFoundExceptionSample {

    /**
     * 1 如果程序编译通过之后,程序跑到sout的时候, 要加载Helper, 这个时候会因为类找不到而抛出ClassNotFoundException,
     * 从而导致类未能成功加载抛NoClassDefFoundError
     *
     * 2 当类能找到, 但是对应的类并不是我们想要的类文件时, 也会抛出NoClassDefFoundError错误
     */
    @Test
    public void testSayHello() {
        System.out.println("start");
//        Helper.sayHello();
        System.out.println("end");
    }


    public static void main(String[] args) {

    }
}
