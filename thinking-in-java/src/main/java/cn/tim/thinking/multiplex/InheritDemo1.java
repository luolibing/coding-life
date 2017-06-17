package cn.tim.thinking.multiplex;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/14.
 * 继承
 */
public class InheritDemo1 {

    class Art {

        String s = getName();

        Art() {
            System.out.println("Art constructor");
        }

        String getName() {
            System.out.println("Art.getName()");
            return "jack";
        }
    }

    class Drawing extends Art {
        private int age = getAge();

        Drawing() {
            System.out.println("Drawing constructor");
        }

        public int getAge() {
            return age;
        }
    }

    // 子类会隐式或者显式调用父类的构造函数. 同时子类拥有一个父类的子对象,这个子对象和你用基类直接创建的对象是一样的.
    // 二者的区别在于后者用于外部,而基类的子对象被包装在导出类对象内部.
    // 基类在导出类构造器可以访问它之前,就已经完成了初始化. 所以初始化的顺序是先初始化基类, 然后才是子类
    public class Cartoon extends Drawing {
        public Cartoon() {
            super();
            System.out.println("Cartoon constructor");
        }
    }

    @Test
    public void constructor() {
        Cartoon x = new Cartoon();
    }

    class A {
        public String name = getName();

        A(int i) {
            System.out.println("A constructor");
        }

        public String getName() {
            System.out.println("getName() = rose");
            return "rose";
        }
    }

    class B {
        B(int i) {
            System.out.println("B constructor");
        }
    }

    class C extends A {
        private B b = new B(10);

        C() {
            super(10);
            System.out.println("C constructor");
        }
    }

    @Test
    public void b() {
        C c = new C();
    }

    class Game {
        Game(int i) {
            System.out.println("Game constructor");
        }
    }

    class BoardGame extends Game {

        BoardGame(int i) {
            super(i);
            System.out.println("BoardGame constructor");
        }

        BoardGame() {
            super(1);
        }
    }

    class Chess extends BoardGame {

        Chess() {
            // 显式调用基类带参构造器
            super(11);
            System.out.println("Chess constructor");
        }
    }

    /**
     * 带参数的基类构造器
     * 如果父类中只有一个带参的构造函数, 必须使用super关键词显式调用基类构造器, 而且调用基类构造器是导出类构造器的第一件事, 否则编译器会报错.
     */
    @Test
    public void constructorWithArgs() {
        new Chess();
    }

    class A1{
        A1() {

        }

        A1(int i) {

        }
    }

    class B1 extends A1 {
        // 如果A1既有无参构造函数,还有有参构造函数,那么导出类可以不显式的调用基类的构造函数, 默认是调用基类的无参构造函数
    }
}
