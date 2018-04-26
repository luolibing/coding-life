package cn.tim.thinking.exception12;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by LuoLiBing on 16/9/21.
 * 异常处理的必要性
 * 强制规定来消除错误处理过程中随心所欲的因素.
 * 只需要在一个地方处理错误,把描述在正常执行的过程中做什么事与出了问题处理代码分离开
 * 普通问题是在当前华景下能够获得足够的信息,总能处理这个错误.而对于异常清醒,就不能继续下去,因为在当前环境无法获得必要的信息来解决问题.只能从当前环境跳出,交给上一级环境
 * 往往发生异常的地方与处理异常的地方距离很远,总是在一个合适的地方来正确处理异常
 *
 * 异常参数
 * 异常也需要创建异常对象,分配堆内存,初始化等等.抛出异常将对象引用传递给throw
 *
 * 捕获异常
 * try {
 *     // 监控区域 (guarded region)
 * } catch(Exception1 e1) {
 *
 * } catch(Exception2 e2) {
 *
 * }
 * 当监控区域抛出异常时,异常处理机制将搜寻异常参数与异常类型匹配的第一个处理程序,进入catch子句进行处理.一旦catch子句结束,结束搜索.
 * 终止与恢复
 * 终止模型,一旦发生错误,错误很致命以至于无法恢复继续执行
 * 恢复模型,发生的错误能够通过修改参数行为达到修复的目的.这个时候不能抛出,而是调用其他方法来恢复,并且放入到while里面直到恢复正常为止
 *
 * 异常方法申明,在方法中抛出异常(非RuntimeException异常),如果不进行捕获,则必须在方法声明中指出. 也可以在声明中抛出异常,而实际方法过程中不抛出,这样可以事先占位
 * 这种在编译时被强制检查的异常称为被检查的异常
 *
 * 可以使用catch(Exception e) {} 捕获所有异常,但是需要放在catch子句的最后,不然会拦截到其他子类的catch,编译器会禁止这种方式
 *
 * 异常使用指南
 * 1 在恰当的级别处理问题,只有在知道该如何处理的情况下才捕获异常
 * 2 解决问题并且重新调用产生异常的方法
 * 3 进行少许修补,然后绕过异常发生的地方继续执行
 * 4 用别的数据进行计算,以代替方法预计会返回的值
 * 5 把当前运行环境下能做的事情尽量做完,然后把相同的异常重新抛到高层
 * 6 把当前运行环境下能做的事情尽量做完,然后把不同的异常抛出到高层
 * 7 终止程序
 * 8 进行简化
 * 9 让类库和程序更安全
 *
 */
public class ExceptionTest {

    class SimpleException extends Exception {

        public final void say() {
            System.out.println("say");
        }
    }

    class MyException extends Exception {
        public MyException() {
            super();
        }

        public MyException(String message) {
            super(message);
        }
    }

    public void f1() throws SimpleException {
        // 将消息发送给标准错误输出
        System.err.println("throws SimpleException from f()");
        throw new SimpleException();
    }

    public void f2() throws MyException {
        System.err.println("throws SimpleException from f()");
        throw new MyException();
    }

    @Test
    public void test1() {
        try {
            f1();
        } catch (SimpleException e) {
            e.printStackTrace();
            System.out.println("Caught it!");
        }
    }

    @Test
    public void test2() {
        try {
            f2();
        } catch (MyException e) {
            // 默认输出到标准错误输出,也可以重定向到System.out上
            e.printStackTrace(System.err);
        }
    }

    public void f3(String message) throws MyException {
        System.err.println("throws SimpleException from f()");
        throw new MyException(message);
    }

    @Test
    public void test3() {
        try {
            f3("执行");
        } catch (MyException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("finally execute");
        }
    }

    @Test
    public void test4() {
        String str = null;
        try {
            System.out.println(str.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        int[] arr = new int[2];
        System.out.println(arr[1]);
        try {
            System.out.println(arr[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class StringException extends Exception {
        private String message;

        public StringException() {
            super();
        }

        public StringException(String message) {
            super(message);
            this.message = message;
        }

        public StringException(Throwable cause) {
            super(cause);
        }

        //        @Override
//        public synchronized Throwable fillInStackTrace() {
//            return this;
//        }

        public void echoError() {
            System.out.println("error Message: " + message);
        }
    }

    public void f4(String message) throws StringException {
        System.err.println("throws SimpleException from f()");
        throw new StringException(message);
    }

    @Test
    public void test6() {
        try {
            f4("hello world!!");
        } catch (StringException e) {
            e.echoError();
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.toString());
            e.printStackTrace(); //  标准错误输出
            e.printStackTrace(System.out); // 制定流
            e.fillInStackTrace(); // 重新抛出异常或错误
        }
    }

    @Test
    public void test7() {
        int[] arr = new int[10];
        int index = 20;
        while (true) {
            try {
                System.out.println("index=" + index);
                System.out.println(arr[index--]);
                System.out.println("great");
                return;
            } catch (Exception e) {
                System.err.println("重试中!!" + index);
            }
        }
    }

    static class LoggingException extends Exception {

        private static Logger logger = Logger.getLogger("LoggingException");

        public LoggingException() {
            StringWriter trace = new StringWriter();
            printStackTrace(new PrintWriter(trace));
            new ArrayList<>();
            logger.severe(trace.toString());
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    public void f5() throws LoggingException {
        System.err.println("throws SimpleException from f()");
        throw new LoggingException();
    }

    @Test
    public void test8() {
        try {
            f5();
        } catch (LoggingException e) {
//            System.err.println("Caught" + e);
        }

        try {
            f5();
        } catch (LoggingException e) {
//            System.err.println("Caught" + e);
        } catch (Exception e) {

        }
    }

    // 编写一个专门用来记录异常的日志信息
    static class LoggingException2 {
        private static Logger logger = Logger.getLogger("LoggingException2");

        static void logException(Exception e) {
            StringWriter trace = new StringWriter();
            e.printStackTrace(new PrintWriter(trace));
            logger.severe(trace.toString());
        }
    }

    @Test
    public void test9() {
        try {
            f5();
        } catch (LoggingException e) {
            LoggingException2.logException(e);
        }
    }

    @Test
    public void test10() {
        try {
            f6("删除");
        } catch (StringException | SimpleException | LoggingException e) {
            e.printStackTrace();
        }
    }

    public void f6(String message) throws StringException, SimpleException, LoggingException {
        System.err.println("throws SimpleException from f()");
        throw new StringException(message);
    }

    @Test
    public void test11() {
        try {
            f4("string 异常");
        } catch (StringException e) {
            e.printStackTrace();
            // getStackTrace返回一个由栈轨迹中元素所构成的数组,每个元素代表栈中的一帧.元素0时站定元素,是调用序列中的最后一个方法(发生异常的最后一个方法)
            // 数组中最后一个元素栈底是调用序列中的第一个调用方法,即第一个入口
            // 在这里第一个方法是 com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
            // 最后一个调用方法是 cn.tim.thinking.exception.ExceptionTest.f4()
            StackTraceElement[] stackTrace = e.getStackTrace();
            for(StackTraceElement stackTraceElement : stackTrace) {
                // StackTraceElement包括declaringClass,methodName方法名,fileName java文件名,lineNumber行数
                System.out.println(stackTraceElement);
            }
        }
    }

    public void f7() throws StringException, LoggingException, SimpleException {
        try {
            f6("f6 异常信息");
        } catch (Exception e) {
            // 重新抛出异常,如果不调用e.fillInStackTrace(),轨迹栈最后一个调用会记录会之前的f6();
            // 如果调用e.fillInStackTrace()方法,会重新填入轨迹栈,之前调用信息会丢失,这里成为新的异常发生地
            e.fillInStackTrace();
            throw e;
        }
    }

    @Test
    public void test12() {
        try {
            f7();
        } catch (StringException e) {
            e.printStackTrace();
        } catch (LoggingException e) {
            e.printStackTrace();
        } catch (SimpleException e) {
            e.printStackTrace();
        }
    }


    public void f8() throws StringException {
        try {
            f1();
        } catch (SimpleException e) {
            // 重新抛出异常,之前的异常轨迹栈会丢失,取而代之的是新抛出的异常,抛出的异常对象会被垃圾回收器自动清理掉
            throw new StringException(e.getMessage());
        }
    }

    @Test
    public void test13() {
        try {
            f8();
        } catch (StringException e) {
            e.printStackTrace();
        }
    }


    public void f9() throws StringException {
        try {
            f1();
        } catch (SimpleException e) {
            // 异常链捕获一个异常后抛出另一个异常,并且希望把原始的异常信息保存下来,这叫做异常链,能够精确定位到异常发生的地方
            // 可以使用initCause(throwable)来进行传递,而不是使用构造方法
            // 带有cause参数构造器的有Error,Exception,RuntimeException
            StringException stringException = new StringException(e.getMessage());
            stringException.initCause(e);
            throw stringException;
        }
    }

    @Test
    public void test14() {
        try {
            f9();
        } catch (StringException e) {
            e.printStackTrace();
        }
    }

    public void f10() throws StringException {
        try {
            f1();
        } catch (SimpleException e) {
            throw new StringException();
        }
    }

    public void f11() {
        try {
            f1();
        } catch (SimpleException e) {
            RuntimeException runtimeException = new RuntimeException();
            runtimeException.initCause(e);
            throw runtimeException;
        }
    }

    @Test
    public void test15() {
        try {
            f10();
        } catch (StringException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test16() {
        f11();
    }

    public static void main(String[] args) {
        new ExceptionTest().test11();
    }
}
