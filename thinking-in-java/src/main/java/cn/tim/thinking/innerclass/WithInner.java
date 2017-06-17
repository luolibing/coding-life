package cn.tim.thinking.innerclass;

/**
 * Created by LuoLiBing on 16/12/21.
 * 因为内部类的构造器必须链接到指向外围类对象的引用, 所以在继承内部类的时候, 事情会变得有点复杂.
 * 问题在于, 那个指向外围类对象的秘密的引用必须被初始化,而在导出类中不再存在可连接的默认对象. 要解决这个问题,必须使用显式的方式添加关联
 */
public class WithInner {
    class Inner {}
}

// No enclosing instance of type WithInner is in scope, 没有外围对象WithInner在这个作用域中
class InheritInner extends WithInner.Inner {

    // 继承自Inner的类, 并没有默认子自带WithInner外围类的引用, 而指向外围类对象的引用必须被初始化.
    InheritInner(WithInner wi) {
        // 这个时候只能显式的进行关联. 不能只传递一个指向外围类对象的引用. 必须早构造器中使用如下语法  enclosingClassReference.super();
        wi.super();
    }

    public static void main(String[] args) {
        WithInner wi = new WithInner();
        InheritInner ii = new InheritInner(wi);
    }
}


class Outer {
    class Inner {
        private String name;

        public Inner(String n) {
            this.name = n;
        }

        @Override
        public String toString() {
            return "Inner{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}

class Outer1 {
    class Inner1 extends Outer.Inner {
        public Inner1(String n, Outer outer) {
            outer.super(n);
        }
    }
}

class Outers {
    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer1 outer1 = new Outer1();
        Outer1.Inner1 inner1 = outer1.new Inner1("luolibing", outer);
        System.out.println(inner1);
    }
}