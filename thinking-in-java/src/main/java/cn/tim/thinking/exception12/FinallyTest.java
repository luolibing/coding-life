package cn.tim.thinking.exception12;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 16/9/23.
 * 对于一些代码,可能希望无论try块中是否有异常抛出他们都能得到执行.通常适用于回收资源等情况
 *
 *
 */
public class FinallyTest {

    @Test
    public void test1() {
        Path path = Paths.get("/share/testluolibing");
        try {
            if(Files.notExists(path)) {
                Files.createDirectory(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            for(int i = 0 ; i < 10; i++) {
                System.out.println("i=" + i);
            }
        } finally {
            System.out.println("finally!!");
        }
    }

    class FourException extends Exception {}

    @Test
    public void test2() {
        System.out.println("entering first try block");
        try {
            System.out.println("entering second try block");
            try {
                throw new FourException();
            } finally {
                System.out.println("finally in 2nd try block");
            }
        } catch (FourException e) {
            System.out.println("Caught FourException in 1st try block");
        } finally {
            System.out.println("finally in 1st try block");
        }
    }

    public void f1(int i) {
        System.out.println("initialization that requires cleanup");
        try {
            System.out.println("point 1");
            if(i == 1) return;
            System.out.println("point 2");
            if(i == 2) return;
            System.out.println("point 3");
            if(i == 3) return;
            System.out.println("end");
            return;
        } finally {
            System.out.println("performing cleanup");
        }

    }

    @Test
    public void test3() {
        for(int i = 0; i<3; i++) {
            f1(i);
        }
    }

    class VeryImportantException extends Exception {
        public String toString() {
            return "A very important exception!";
        }
    }

    class HuHumException extends Exception {
        public String toString() {
            return "A trivial exception!";
        }
    }

    public class LostMessage {
        void f() throws VeryImportantException {
            throw new VeryImportantException();
        }

        void dispose() throws HuHumException {
            throw new HuHumException();
        }

    }

    @Test
    public void test4() {
        try {
            LostMessage lm = new LostMessage();
            try {
                // 异常丢失, f()方法抛出异常之后,由于本身只有finally语句, 所以等执行完finally之后才会被外层的try给捕获;
                // 但是当这个时候finally中也抛出异常时,f()抛出的异常最终被finally中的异常所取代
                lm.f();
            } finally {
                lm.dispose();
                System.out.println("finally");
            }
        } catch (Exception e) {
//            System.out.println(e);
            e.printStackTrace();
        }

    }

    @Test
    public void test5() {
        try {
            try {
                throw new FourException();
            } finally {
                // 不适用catch异常,使用finally直接return异常直接丢失,不会抛出去
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Parent {
        public void f() throws FourException {
            System.out.println();
            throw new FourException();
        }
    }

    class Child extends Parent {

        // 覆盖方法时,只能抛出在基类方法列出的异常
        @Override
        public void f() {

        }
    }

    /**
     * 异常的限制
     *
     */
    @Test
    public void test6() throws FourException {
        Parent child = new Child();
        child.f();
    }


    class BaseballException extends Exception {}

    class Foul extends BaseballException {}

    class Strike extends BaseballException {}

    abstract class Inning {
        public Inning() throws BaseballException {

        }

        public Inning(String str) throws BaseballException {

        }

        public void event() throws BaseballException {

        }

        public abstract void atBat() throws Strike, Foul, UmipireArgument;

        public void walk() throws FourException {}

        public void rainHard() throws RainedOut, UmipireArgument {

        }
    }

    class StormException extends Exception {}

    class RainedOut extends StormException {}

    class PopFoul extends Foul {}

    class UmipireArgument extends Exception {}

    interface Storm {
        void event() throws RainedOut;

        void rainHard() throws RainedOut, UmipireArgument;
    }

    class StormyInning extends Inning implements Storm {

        /**
         * event方法不能添加任何异常,因为接口和父类都抛出了异常
         * 不能子类抛出异常父类不抛,子类可以不抛出父类异常.但是当向上转型调用时,编译器会提示处理异常
         * 异常声明不属于方法类型的一部分,因此不能基于异常说明来重载方法,异常接口说明在继承和覆盖当中逐步变小
         *
         */
        @Override
        public void event() {

        }

        // 因为默认会调用父类的super()无参方法,当父类无参构造函数抛出异常时,子类构造函数必须得抛出这个异常
        // 子类可以扩展父类构造函数的异常声明
        public StormyInning() throws BaseballException, RainedOut, FourException {
        }

        public StormyInning(String s) throws BaseballException {
            super();
        }

        @Override
        public void atBat() throws PopFoul, UmipireArgument {

        }

        @Override
        public void rainHard() throws UmipireArgument {

        }

        @Override
        public void walk() {

        }
    }

    @Test
    public void test7() {
        try {
            StormyInning si = new StormyInning();
            si.atBat();
        } catch (BaseballException|FourException |UmipireArgument| RainedOut e) {
            e.printStackTrace();
        }

        try {
            Inning in = new StormyInning();
            in.atBat();
        } catch (BaseballException| UmipireArgument|FourException e) {
            e.printStackTrace();
        } catch (RainedOut rainedOut) {
            rainedOut.printStackTrace();
        }

        try {
            StormyInning stormyInning = new StormyInning("aaa");
            stormyInning.walk();

            // 使用父类,还是需要捕获异常 ((Inning) stormyInning).walk();
        } catch (BaseballException e) {
            e.printStackTrace();
        }
    }
}
