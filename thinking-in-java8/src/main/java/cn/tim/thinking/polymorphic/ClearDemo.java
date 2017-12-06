package cn.tim.thinking.polymorphic;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/12/15.
 * 继承与清理
 * 清理的顺序与声明的顺序相反, 先基类后子类, 声明顺序的反顺序
 */
public class ClearDemo {

    class Characteristic {
        private String s;

        Characteristic(String s) {
            this.s = s;
            System.out.println("Creating Characteristic " + s);
        }

        protected void dispose() {
            System.out.println("disposing Characteristic " + s);
        }
    }

    class Description {
        private String s;

        Description(String s) {
            this.s = s;
            System.out.println("Creating Description " + s);
        }

        protected void dispose() {
            System.out.println("disposing Description " + s);
        }
    }

    class LivingCreature {
        private Characteristic p = new Characteristic("ch");

        private Description d = new Description("desc");

        LivingCreature() {
            System.out.println("LivingCreature");
        }

        protected void dispose() {
            System.out.println("LivingCreature dispose");
            d.dispose();
            p.dispose();
        }
    }

    class Animal extends LivingCreature {

        private Characteristic p = new Characteristic("has heart");

        private Description d = new Description("Animal not Vegetable");

        Animal() {
            System.out.println("Animal()");
        }

        protected void dispose() {
            System.out.println("Animal dispose");
            d.dispose();
            p.dispose();
            super.dispose();
        }
    }

    class Amphibian extends Animal {

        private Characteristic p = new Characteristic("can live in water");

        private Description d = new Description("Both water and land");

        Amphibian() {
            System.out.println("Amphibian()");
        }

        protected void dispose() {
            System.out.println("Amphibian dispose");
            d.dispose();
            p.dispose();
            super.dispose();
        }
    }

    @Test
    public void living() {
        Amphibian a = new Amphibian();
        a.dispose();
    }


    /**
     * 只有对象自己创建了自己的成员对象,并且知道他们应该存活多久, 因此知道何时去调用dispose()方法;
     * 但是如果这些成员对象存在于其他一个或多个对象共享的情况, 问题就变复杂了; 这种情况不能简单的调用dispose(), 也许就必须使用引用计算来跟踪依旧访问者共享对象的对象数量.
     */
    static class Shared {
        private int refCount = 0;

        private static long counter = 0;

        private final long id = counter++;

        public Shared() {
            System.out.println("Creating " + this);
        }

        public void addRef() {
            refCount ++;
        }

        protected void dispose() {
            // 引用-1, 判断是否还有关联对象,没有的话,就可以清理这个共享对象了
            if(-- refCount == 0) {
                System.out.println("Disposing " + this);
            }
        }

        @Override
        public String toString() {
            return "Shared " + id;
        }
    }

    static class Composing {
        private Shared shared;

        private static long counter = 0;

        private final long id = counter++;

        public Composing(Shared shared) {
            System.out.println("Creating " + this);
            this.shared = shared;
            // 进行关联, 然后引用计数自增1
            this.shared.addRef();
        }

        protected void dispose() {
            System.out.println("disposing " + this);
            shared.dispose();
        }

        @Override
        public String toString() {
            return "Composing " + id;
        }
    }


    /**
     * Shared维护了一个被引用次数的变量refCount, 每当被引用的时候自增1, 然后dispose()的时候减1,然后判断是否等于0, 等于0表明没有关联的对象了
     */
    static class ReferenceCounting {

        private static Shared shared = new Shared();

        public static void main(String[] args) throws InterruptedException {
            // 共享对象
            Composing[] composings = {
                    new Composing(shared),
                    new Composing(shared),
                    new Composing(shared),
                    new Composing(shared),
            };

            for(Composing composing : composings) {
                composing.dispose();
            }
            System.gc();
            TimeUnit.SECONDS.sleep(10);
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("invoke finalize");
            System.out.println("shared.refCount = " + shared.refCount);
        }
    }


    /**
     * 构造器内部的多态方法行为:
     *
     * 在基类构造器内部调用正在构造的对象的某个动态绑定方法, 在一般的方法内部,
     * 动态绑定的调用时在运行时才决定的, 因为对象无法知道它是属于方法所在的类, 还是这个类的导出类.
     * 这个时候, 整个对象可能只是部分形成, 在导出类对象还没正确初始化的时候, 动态绑定方法调用却向外深入到继承层次结构内部, 调用导出类的方法. 这是非常危险的.
     * 用尽可能简单的方法使对象进入正常状态; 如果可以的话, 避免调用其他方法. 在构造器内唯一能够安全调用的那些方法是基类中的final方法(也可以是private方法)
     */
    class Glyph {
        void draw() {
            System.out.println("Glyph.draw()");
        }

        Glyph() {
            System.out.println("Glyph() before draw()");
            // 由于方法动态绑定, 这个时候会根据具体类型,如果是导出类就绑定到导出类, 而导出类还没有进行初始化, 所以对应的成员变量都还为0或者null
            // 在其他任何事物发生之前, 将分配给对象的存储空间初始化成二进制的0
            draw();
            System.out.println("Glyph() after draw()");
        }
    }

    class RoundGlyph extends Glyph {
        private int radius = 1;

        public RoundGlyph(int r) {
            this.radius = r;
            System.out.println("RoundGlyph.RoundGlyph(), radius = " + radius);
        }

        @Override
        void draw() {
            System.out.println("RoundGlyph.draw(), radius = " + radius);
        }
    }

    @Test
    public void glyph() {
        new RoundGlyph(10);
    }
}
