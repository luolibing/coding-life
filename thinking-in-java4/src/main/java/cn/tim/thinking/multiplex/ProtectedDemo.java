package cn.tim.thinking.multiplex;

import cn.tim.thinking.io.BinaryFileDemo2;
import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/14.
 * 关键字protected的作用, 指明就类用户而言,这是private的,但是对于任何继承于此类的导出类或其他任何位于同一个包内的类来说,它却是可以访问的. protected同时也具有包访问权限
 */
public class ProtectedDemo {

    // protected权限, 是子类 + 包访问权限
    class Villain {
        // 一般情况还是将字段设置为private, 提供一个protected的修改方法
        private String name;

        public Villain(String name) {
            this.name = name;
        }

        protected void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "I'm a Villain and my name is " + name;
        }
    }

    class Orc extends Villain {
        private int orcNumber;

        public Orc(String name, int orcNumber) {
            super(name);
            this.orcNumber = orcNumber;
        }

        public void change(String name, int orcNumber) {
            // 因为父类的setName()方法是protected的,所以子类可以访问到
            setName(name);
            this.orcNumber = orcNumber;
        }

        @Override
        public String toString() {
            return "Orc " + orcNumber + ": " + super.toString();
        }
    }

    @Test
    public void orc() {
        Orc orc = new Orc("Limburger", 12);
        System.out.println(orc);
        orc.change("Bob", 20);
        System.out.println(orc);
        // sayHello()是protected访问权限
        // BinaryFileDemo2.sayHello();
//        BinaryFileDemoImpl.say();
        BinaryFileDemo2 binary = new BinaryFileDemoImpl();
//        binary.sayHello();
        BinaryFileDemoImpl.sayHello();
    }

    static class BinaryFileDemoImpl extends BinaryFileDemo2 {
        public static void say() {
            // 静态方法继承, 不能通过super对象调用
            BinaryFileDemo2.sayHello();
        }

        // 静态方法继承不存在覆盖方法
        public static void sayHello() {
            System.out.println("BinaryFileDemoImpl");
        }
    }
}
