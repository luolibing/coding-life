package cn.tim.thinking.innerclass;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/18.
 * 在方法和作用于内的内部类
 */
public class InMethodInnerClass {

    /**
     * 在方法里面或者任意的作用域内定义内部类的理由:
     * 1 实现了某类型的接口,于是可以创建并返回对其的引用
     * 2 要解决一个复杂的问题, 想创建一个类来辅助你的解决方案, 但是又不希望这个类是公共可用的.
     */

    interface Destination {
        String readLabel();
    }

    interface Contents {
        int value();
    }

    // 方式一: 定义在方法中的类
    class Parcel5 {
        // 向上转型
        public Destination destination(String s) {
            // 内部类定义在destination方法中, 所以PDestination类在该方法之外无法访问. 所以在这个作用域外面可以定义相同名字的内部类, 并不会产生冲突.
            class PDestination implements Destination {

                private String label;

                public PDestination(String whereTo) {
                    this.label = whereTo;
                }

                @Override
                public String readLabel() {
                    return label;
                }
            }
            return new PDestination(s);
        }
    }

    @Test
    public void parcel5() {
        Parcel5 x = new Parcel5();
        System.out.println(x.destination("Tomcat"));
    }


    //
    class Parcel6 {
        private void internalTracking(boolean b) {
            if(b) {
                // 类创建在if作用域内,并不是说类的创建是有条件的, 它其实与别的类一起编译过了. 然后再if()作用域之外, 他是不可用的.
                class TrackingSlip {
                    private String id;

                    TrackingSlip(String s) {
                        this.id = s;
                    }

                    String getSlip() {
                        return id;
                    }
                }
                TrackingSlip ts = new TrackingSlip("xMan");
                String s = ts.getSlip();
                System.out.println(s);
            }

            // TrackSlip ts = new TrackSlip("x"); 离开了作用域, 无法再访问TrackSlip
        }

        public void track() {
            internalTracking(true);
        }
    }

    @Test
    public void parcel6() {
        Parcel6 x = new Parcel6();
        x.track();
    }

    interface Interface9 {
        void f();
    }

    class Outer9 {
        public Interface9 g(String name) {
            if(name!= null) {
                class Inner implements Interface9 {

                    public String name;

                    public Inner(String n) {
                        this.name = n;
                    }

                    @Override
                    public void f() {
                        System.out.println("Inner.f(), name = " + name);
                    }
                }
                return new Inner(name);
            }
            return null;
        }

        public Interface9 h(String n) {
            return new Interface9() {

                private String name;

                {
                    this.name = n;
                }

                @Override
                public void f() {
                    System.out.println("Inner.h(), name = " + name);
                }
            };
        }
    }

    @Test
    public void outer9() {
        Interface9 i = new Outer9().g("luo");
        i.f();
    }


    interface Interface10 {
        void f();
    }

    class Outer10 {
        private class Inner implements Interface10 {
            private String name;

            public Inner(String n) {
                this.name = n;
            }

            @Override
            public void f() {
                System.out.println("Inner.f(), name = " + name);
            }

            public void k() {
                System.out.println("Inner.k()");
            }
        }

        public Interface10 g(String name) {
            return new Inner(name);
        }
    }

    @Test
    public void outer10() {
        Outer10 x = new Outer10();
        Interface10 luo = x.g("luo");
        luo.f();
        // (Inner)luo.k(); 无法将其向下强转, 内部类被完全隐藏了.
    }
}
