package cn.tim.thinking.multiplex;

import org.junit.Test;

/**
 * Created by LuoLiBing on 16/12/14.
 *
 * 代理: 继承和组合的之间的中庸之道
 *
 *
 */
public class ProxyDemo1 {

    // 方式一: 继承
    // 飞船控制器
    public class SpaceShipControls {
        void up(int v) {}

        void down(int v) {}

        void left(int v) {}

        void right(int v) {}

        void forward(int v) {}

        void back(int v) {}

        void turboBoost() {}
    }


    class SpaceShip extends SpaceShipControls {
        private String name;

        public SpaceShip(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // SpaceShip并非真正的SpaceShipControls, 而仅仅是想让SpaceShip拥有SpaceShipControls的功能
    // 更准确地说, SpaceShip包含了SpaceShipControls, 这个地方用继承并不合适
    @Test
    public void spaceShip1() {
        SpaceShip spaceShip = new SpaceShip("NSEA Protector");
        spaceShip.forward(100);
    }


    // 方式二: 代理,
    public class SpaceShipDelegation {
        private String name;

        private SpaceShipControls controls = new SpaceShipControls();

        public SpaceShipDelegation(String name) {
            this.name = name;
        }

        void up(int v) {
            controls.up(v);
        }

        void down(int v) {
            controls.down(v);
        }

        void left(int v) {
            controls.left(v);
        }

        void right(int v) {
            controls.right(v);
        }

        void forward(int v) {
            controls.forward(v);
        }

        void back(int v) {
            controls.back(v);
        }

        void turboBoost() {
            controls.turboBoost();
        }
    }

    @Test
    public void proxy1() {
        new SpaceShipDelegation("嫦娥一号");
    }


    // 结合使用组合和继承
    class Plate {
        Plate(int i) {
            System.out.println("Plate constructor");
        }
    }

    class DinnerPlate extends Plate {

        DinnerPlate(int i) {
            super(i);
            System.out.println("DinnerPlate constructor");
        }
    }

    class Utensil {
        Utensil(int i) {
            System.out.println("Utensil constructor");
        }
    }

    class Spoon extends Utensil {

        Spoon(int i) {
            super(i);
            System.out.println("Spoon constructor");
        }
    }

    class Fork extends Utensil {

        Fork(int i) {
            super(i);
            System.out.println("Fork constructor");
        }
    }

    class Knife extends Utensil {

        Knife(int i) {
            super(i);
            System.out.println("Knife constructor");
        }
    }

    class Custom {
        Custom(int i) {
            System.out.println("Custom constructor");
        }
    }

    public class PlaceSetting extends Custom {

        private Spoon sp;

        private Fork fork;

        private Knife knife;

        private DinnerPlate dinnerPlate;

        PlaceSetting(int i) {
            super(i);
            sp = new Spoon(i + 2);
            fork = new Fork(i + 3);
            knife = new Knife(i + 4);
            dinnerPlate = new DinnerPlate(i + 5);
            System.out.println("PlateSetting constructor");
        }
    }

    @Test
    public void plateSetting() {
        PlaceSetting placeSetting = new PlaceSetting(10);
    }


    /**
     * 确保正确清理
     * java没有析构函数, 因为在java中我们习惯只是忘掉而不是销毁对象,并且让垃圾回收器在必要时释放其内存.
     * 单有时候类可能在其生命周期中需要执行一些必需的清理活动. 但是我们并不知道垃圾回收器合适将会被调用, 或者它是否会被调用.
     * 因此需要手动显式编写一个特殊方法来做这件事,并要确保客户端程序员知晓他们必须要调用这一方法. 首要任务就是, 必须将这已清理动作置于finally子句之中.
     * 清理的顺序很重要,  防止某个子对象依赖于另一个子对象情形的发生. 一般而言,清理的顺序与创建的顺序相反(例如先清理子类, 然后再清理基类)
     * 不能依赖垃圾回收器去做任何事情, 如果需要进行清理,最好是编写自己的清理方法,但不要使用finalize()方法
     *
     */
    class Shape {
        Shape(int i) {
            System.out.println("Shape constructor");
        }

        // 清理工作
        void dispose() {
            System.out.println("Shape dispose");
        }

        void draw() {
            System.out.println("Shape draw");
        }
    }

    class Circle extends Shape {

        Circle(int i) {
            // 创建顺序: 先基类,后子类
            super(i);
            System.out.println("Drawing Circle");
        }

        // 执行完本身的清理工作之后,再调用父类的清理工作
        void dispose() {
            // 清理顺序: 先子类,后基类
            System.out.println("Erasing Circle");
            super.dispose();
        }
    }

    class Triangle extends Shape {

        Triangle(int i) {
            super(i);
            System.out.println("Drawing Triangle");
        }

        void dispose() {
            System.out.println("Erasing Triangle");
            super.dispose();
        }
    }

    class Line extends Shape {

        private int start, end;

        Line(int start, int end) {
            super(start);
            this.start = start;
            this.end = end;
            System.out.println("Drawing Line: " + start + ", " + end);
        }

        void dispose() {
            System.out.println("Erasing Line: " + start + ", " + end);
            super.dispose();
        }
    }


    public class CADSystem extends Shape {

        private Circle c;

        private Triangle t;

        private Line[] lines = new Line[3];

        CADSystem(int i) {
            super(i + 1);
            for(int j = 0; j < lines.length; j++) {
                lines[j] = new Line(j, j * j);
            }
            c = new Circle(1);
            t = new Triangle(1);
            System.out.println("Combined constructor");
        }

        @Override
        void dispose() {
            System.out.println("CADSystem.dispose()");
            // 清理顺序
            t.dispose();
            c.dispose();
            for(int i = lines.length; i >=0; i--) {
                lines[i].dispose();
            }
            super.dispose();
        }
    }

    @Test
    public void cadSystem() {
        CADSystem x = new CADSystem(10);
        try {
            x.draw();
        } finally {
            // 一般将清理工作防止在finally中,确保出现异常也能正确的清理
            x.dispose();
        }
    }

    class Homer {
        char doh(char c) {
            System.out.println("doh(char)");
            return 'd';
        }

        float doh(float f) {
            System.out.println("doh(float)");
            return 1.0f;
        }
    }

    class Milhouse {}

    class Bart extends Homer {
        void doh(Milhouse m) {
            System.out.println("doh(Milhouse)");
        }
    }

    @Test
    public void bart() {
        Bart b = new Bart();
        b.doh('a');
        b.doh(2.0f);
        b.doh(new Milhouse());
    }


    /**
     * 组合与继承
     * 组合和继承都允许在新的类中放置子对象, 组合是显式地, 而继承是隐式处理.
     * 组合技术通常用于想在新类中使用现有类的功能而非它的接口这种情形. 在新类中嵌入某个对象,让其实现所需的功能, 新类用户只能看到为新类所定义的接口,而非所嵌入对象的接口. 为了达到这种目的,一般讲嵌入类设置为private;
     *      有时,允许类的用户直接访问新类中的组合成为也是极具意义的,也就是说设置为public.如果成员自身都隐藏了具体实现,那么这种做法是安全的. 但是最好设置为final类型. 这样使得端口更加易于理解.
     * 继承时候,使用某个现有类,并开发一个它的特殊版本.这意味着你再使用一个通用类,并为了谋者特殊需要而将其特殊化. is-a的关系用来表示继承,  而has-a的关系则用来表示组合
     *
     */
    class Engine {
        public void start() {}

        public void rev() {}

        public void stop() {}

        public void service() {}
    }

    class Wheel {
        public void inflate(int psi) {}
    }

    class Window {
        public void rollup() {}

        public void rolldown() {}
    }

    class Door {
        public final Window window = new Window();

        public void open() {}

        public void close() {}
    }

    class Car {
        public final Engine engine = new Engine();

        public final Wheel[] wheel = new Wheel[4];

        public final Door left = new Door(), right = new Door();

        public Car() {
            for(int i = 0; i < 5; i++) {
                wheel[i] = new Wheel();
            }
        }
    }

    @Test
    public void car() {
        Car car = new Car();
        // 直接让成员本身为public,直接访问,有点类似于DSL
        car.left.window.rollup();
        car.wheel[0].inflate(72);
        car.engine.service();
    }
}
