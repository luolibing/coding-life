package cn.tim.thinking.exception12;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by LuoLiBing on 16/9/23.
 */
public class MatchTest {

    class Annoyance extends Exception {}

    class Sneeze extends Annoyance {}

    class Trap extends Sneeze {}

    @Test
    public void test1() {
        // 只能是子类排在父类的前面捕获异常
        try {
            throw new Sneeze();
        } catch (Sneeze e) {
            e.printStackTrace();
        } catch (Annoyance e) {
            e.printStackTrace();
        }

        // 使用基类捕获子类异常,之后就不能再跟子类异常,因为前面的基类已经拦截了,子类永远得不到执行,所以编译器会提示这个错误
        try {
            throw new Sneeze();
        } catch (Annoyance e) {
            e.printStackTrace();
        }
    }

    class A  {
        public void f() throws Annoyance {
            throw new Annoyance();
        }
    }

    class B extends A {
        @Override
        public void f() throws Sneeze {
            throw new Sneeze();
        }
    }

    class C extends B {
        @Override
        public void f() throws Trap {
            throw new Trap();
        }
    }

    @Test
    public void test2() {
        A c = new C();
        try {
            c.f();
        } catch (Annoyance annoyance) {
            // 实际的异常是子类异常Trap
            annoyance.printStackTrace();
        }
    }

    /**
     * 异常处理的一个重要原则是: 只有在你知道如何处理的情况下才捕获异常.异常处理的一个重要目标就是把错误处理的代码同错误发生的地点相分离.
     * 被检查的异常往往会在我们还没准备好处理错误的时候被迫加上catch子句,但是却不知道如何处理,这就导致了吞食则有害的问题;
     */
    @Test
    public void test3() {
        try {
            throw new Trap();
        } catch (Annoyance e) {
            // 异常吞食
        }
    }

    @Test
    public void test4() {
        try {
            throw new Trap();
        } catch (Trap e) {
            // 将编译期异常转换为运行时异常
            throw new RuntimeException(e);
        }
    }

    class WrapCheckedException {
        void throwRuntimeException(int type) {
            try {
                switch (type) {
                    case 0: throw new FileNotFoundException();
                    case 1: throw new IOException();
                    case 2: throw new RuntimeException("where am i");
                    default: return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class SomeOtherException extends Exception {}

    @Test
    public void test5() {
        WrapCheckedException wce = new WrapCheckedException();
        wce.throwRuntimeException(3);
        for(int i = 0; i < 4; i++) {
            try {
                if (i < 3) {
                    wce.throwRuntimeException(i);
                } else {
                    throw new SomeOtherException();
                }
            } catch (SomeOtherException e) {
                System.out.println(e);
            } catch (RuntimeException e) {
                try {
                    // 通过cause找出发生异常的原始点
                    throw e.getCause();
                } catch (FileNotFoundException e1) {
                    System.out.println("fileNotFound" + e1);
                } catch (IOException e1) {
                    System.out.println("IOException" + e1);
                } catch (Throwable e1) {
                    System.out.println("throwable " + e1);
                }
            }
        }
    }
}
