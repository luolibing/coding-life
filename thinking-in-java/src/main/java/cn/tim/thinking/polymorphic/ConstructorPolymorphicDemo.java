package cn.tim.thinking.polymorphic;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/15.
 * 构造器与多态
 * 构造器是隐式的static方法, 所以也不具备多态性.
 *
 * 基类的构造器总是在导出类的构造过程中被调用, 而且按照继承层次逐渐向上链接, 以使得每个基类的构造器都能得到调用.
 * 这样做是有意义的, 因为构造器具有意向特殊任务: 检查对象是否被正确地构造. 导出类只能访问它自己的成员,不能访问基类的成员,
 * 只有基类构造器才能对自己的元素进行初始化,因此必须令所有构造器都得到调用, 否则就不能正确构造完整对象. 这就是为什么要强制每个导出类都必须调用构造器的原因.
 */
public class ConstructorPolymorphicDemo {

    class Meal {
        Meal() {
            System.out.println("Meal()");
        }
    }

    class Bread {
        Bread() {
            System.out.println("Bread()");
        }
    }

    class Cheese {
        Cheese() {
            System.out.println("Cheese()");
        }
    }

    class Lettuce {
        Lettuce() {
            System.out.println("Lettuce()");
        }
    }

    class Lunch extends Meal {
        Lunch() {
            System.out.println("Lunch()");
        }
    }

    class PortableLunch extends Lunch {
        PortableLunch() {
            System.out.println("PortableLunch()");
        }
    }

    class SandWich extends PortableLunch {
        private Bread b = new Bread();
        private Cheese c = new Cheese();
        private Lettuce l = new Lettuce();

        public SandWich() {
            System.out.println("SandWich()");
        }
    }

    /**
     * 调用顺序:
     * 1 调用基类构造器, 从根目录到导出类
     * 2 按声明顺序调用成员的初始化方法
     * 3 调用导出类构造器主题
     */
    @Test
    public void sandwich() {
        new SandWich();
    }
}
