package cn.tim.thinking.innerclass;

/**
 * Created by LuoLiBing on 16/12/21.
 * 内部类可以被覆盖吗
 * 创建一个内部类, 然后继承其外围类并重新定义此内部类. 重新定义并不能覆盖掉外部类父类中的内部类.
 */
public class InnerOverrideDemo {

}

class Egg {

    private Yolk y;

    protected class Yolk {
        public Yolk() {
            System.out.println("Egg.Yolk()");
        }
    }

    public Egg() {
        System.out.println("new Egg()");
        y = new Yolk();
    }
}

// 看起来BigEgg继承自Egg, 应该会覆盖掉父类Yolk的定义, 但是并没有起作用. 这两个内部类是完全独立的两个实体, 各自在自己的命名空间内.
class BigEgg extends Egg {
    public class Yolk {
        public Yolk() {
            System.out.println("BigEgg.Yolk()");
        }
    }

    public static void main(String[] args) {
        new BigEgg();
    }
}


// 显式继承内部类
class Egg2 {
    protected class Yolk {
        public Yolk() {
            System.out.println("Egg2.Yolk()");
        }

        public void f() {
            System.out.println("Egg2.Yolk.f()");
        }
    }

    private Yolk y = new Yolk(); // 使用的是自身的Yolk()

    public Egg2() {
        System.out.println("new Egg2()");
    }

    public void insertYolk(Yolk yy) {
        y = yy;
    }

    public void g() {
        y.f();
    }
}

class BigEgg2 extends Egg2 {
    public class Yolk extends Egg2.Yolk {
        public Yolk() {
            System.out.println("BigEgg2.Yolk()");
        }

        @Override
        public void f() {
            System.out.println("BigEgg2.Yolk.f()");
        }
    }

    public BigEgg2() {
        insertYolk(new Yolk()); // 子类new Yolk()也是子类自定义的Yolk对象
    }

    public static void main(String[] args) {
        Egg2 e2 = new BigEgg2();
        e2.g();
    }
}


// 局部内部类: 在代码块里创建的内部类, 典型的方式就是在一个方法体中创建. 局部内部类不能有访问说明符, 因为它不是外围类的一部分;
// 但是它可以访问当前代码块内的常量,以及此外围类的所有成员. 使用局部内部类而不适用匿名内部类的唯一理由是, 需要不止一个该内部类的对象.
interface Counter {
    int next();
}

class LocalInnerClass {
    private int count = 0;

    Counter getCounter(final String name) {
        // 局部内部类
        class LocalCounter implements Counter {
            public LocalCounter() {
                System.out.println("LocalCounter()");
            }

            @Override
            public int next() {
                System.out.println(name);
                return count++;
            }
        }
        return new LocalCounter();
    }

    Counter getCounter2(final String name) {
        // 匿名内部类
        return new Counter() {
            {
                System.out.println("Counter()");
            }

            @Override
            public int next() {
                System.out.println(name);
                return count++;
            }
        };
    }

    public static void main(String[] args) {
        LocalInnerClass lic = new LocalInnerClass();
        Counter
                c1 = lic.getCounter("Local inner"),
                c2 = lic.getCounter2("Anonymous inner");
        for(int i = 0; i < 5; i++) {
            System.out.print(c1.next());
        }

        for(int i = 0; i < 5; i++) {
            System.out.print(c2.next());
        }
    }
}


/**
 * 内部类标识符: LocalInnerClass.class, LocalInnerClass$1.class, LocalInnerClass$1LocalCounter.class
 * 如果内部类是嵌套在别的内部类之中,只需要直接将他们的名字加载其外围类标识符与$的后面.  匿名内部类使用$1(2,3,4,5)接数字的方式.
 *
 */
