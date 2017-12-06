package cn.tim.thinking.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by LuoLiBing on 16/12/11.
 * 持久性
 *
 *
 */
public class PersistentDemo1 {

    static class House implements Serializable {}

    static class Animal implements Serializable {

        private String name;

        private House house;

        public Animal(String name, House house) {
            this.name = name;
            this.house = house;
        }

        @Override
        public String toString() {
            return "Animal{" +
                    "name='" + name + '\'' +
                    ", house=" + house +
                    '}';
        }
    }


    /**
     * 多个对象指向同一个对象: 多个Animal都指向同一个house
     *
     * 我们可以通过一个字节数组来使用对象序列化,从而实现对任何可Serializable对象的深度复制. 深度复制意味着我们复制的是整个对象网,而不仅仅是基本对象及其引用.
     * 以下实验的结论:
     *
     * 1 同一个对象,分别用两个不同的流将其序列化,然后反序列化得到的对象结果都不一样
     * 2 同一个对象,用同一个流将其序列化多次,然后反序列化得到的对象指向的是同一个地址,包括其中的对象引用.
     *   系统无法知道另一个流内的对象是第一个流内对象的别名,因此会产生出完全不同的对象网
     * 3 序列化和反序列化得到的对象,属于不同的内存地址.equals的结果为false
     *
     * 只要将任何对象序列化到单一流中,就可以恢复出与我们写出时一样的对象网.
     * 如果我们想保存系统状态,最安全的做法是将其作为"原子"操作进行序列化.将构成系统状态的所有对象置入单一容器内,并在一个操作中将该容器直接写出.
     *
     */
    static class Animals {
        public static void main(String[] args) throws IOException, ClassNotFoundException {
            ByteArrayOutputStream buffer1 = new ByteArrayOutputStream();
            ObjectOutputStream o1 = new ObjectOutputStream(buffer1);
            House house = new House();
            Animal[] animals = {
                    new Animal("monkey", house),
                    new Animal("cat", house),
                    new Animal("sheep", house)
            };
            // 同一个对象写入两次
            List<Animal> list = Arrays.asList(animals);
            System.out.println(list);

            o1.writeObject(list);
            o1.writeObject(list);
            o1.close();

            ByteArrayOutputStream buffer2 = new ByteArrayOutputStream();
            ObjectOutputStream o2 = new ObjectOutputStream(buffer2);
            o2.writeObject(list);
            o2.close();

            ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(buffer1.toByteArray()));
            ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(buffer2.toByteArray()));
            Object object = in1.readObject();
            System.out.println("equals = " + object.equals(animals));
            System.out.println();
            System.out.println(in1.readObject());
            System.out.println(in2.readObject());
        }
    }


    abstract static class Shape implements Serializable {
        public final static int RED = 1, BLUE = 2, GREEN = 3;

        private int xPos, yPos, dimension;

        private static Random rand = new Random(47);

        private static int counter = 0;

        public abstract void setColor(int newColor);

        public abstract int getColor();

        public Shape(int xVal, int yVal, int dim) {
            this.xPos = xVal;
            this.yPos = yVal;
            this.dimension = dim;
        }

        @Override
        public String toString() {
            return "Shape{" +
                    "color=" + getColor() +
                    ",xPos=" + xPos +
                    ", yPos=" + yPos +
                    ", dimension=" + dimension +
                    '}';
        }

        public static Shape randomFactory() {
            int xVal = rand.nextInt(100);
            int yVal = rand.nextInt(100);
            int dim = rand.nextInt(100);
            switch (counter ++ %3) {
                default:
                case 0: return new Circle(xVal, yVal, dim);
                case 1: return new Square(xVal, yVal, dim);
                case 2: return new Line(xVal, yVal, dim);
            }
        }
    }

    static class Circle extends Shape {

        private static int color = RED;

        public Circle(int xVal, int yVal, int dim) {
            super(xVal, yVal, dim);
        }

        @Override
        public void setColor(int newColor) {
            color = newColor;
        }

        @Override
        public int getColor() {
            return color;
        }

        public static void serializeStaticState(ObjectOutputStream os) throws IOException {
            os.writeInt(color);
        }

        public static void deserializeStaticState(ObjectInputStream in) throws IOException {
            color = in.readInt();
        }
    }

    static class Square extends Shape {

        private static int color;

        @Override
        public void setColor(int newColor) {
            color = newColor;
        }

        @Override
        public int getColor() {
            return color;
        }

        public Square(int xVal, int yVal, int dim) {
            super(xVal, yVal, dim);
            color = RED;
        }

        public static void serializeStaticState(ObjectOutputStream os) throws IOException {
            os.writeInt(color);
        }

        public static void deserializeStaticState(ObjectInputStream in) throws IOException {
            color = in.readInt();
        }
    }

    static class Line extends Shape {

        private static int color = RED;

        public Line(int xVal, int yVal, int dim) {
            super(xVal, yVal, dim);
        }

        @Override
        public void setColor(int newColor) {
            color = newColor;
        }

        @Override
        public int getColor() {
            return color;
        }

        public static void serializeStaticState(ObjectOutputStream os) throws IOException {
            os.writeInt(color);
        }

        public static void deserializeStaticState(ObjectInputStream in) throws IOException {
            color = in.readInt();
        }
    }

    /**
     * 如果想保存static字段,可以直接对Class对象进行序列化,这样就可以很容易地保存static字段;
     * 尽管class类是Serializable的,但是它却不能按照我们期望的方式运行. 所以假如我们想序列化static值,必须自己手动去实现.
     * static字段理论上应该是transient,因为static字段属于class的一部分,这个东西是所以实例共有的
     *
     */
    static class StoreCADState {
        public static void main(String[] args) throws IOException, ClassNotFoundException {
            List<Class<? extends Shape>> shapeTypes = new ArrayList<>();
            shapeTypes.add(Circle.class);
            shapeTypes.add(Square.class);
            shapeTypes.add(Line.class);

            List<Shape> shapes = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                shapes.add(Shape.randomFactory());
            }

            for(int i = 0; i < 10; i++) {
                shapes.get(i).setColor(Shape.GREEN);
            }
            System.out.println("before serializable = " + shapes);

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CADState.out"));
            // 很奇怪,这个地方要是把静态变量序列化放到实例变量后面竟然会报EOFException
            Square.serializeStaticState(out);
            Circle.serializeStaticState(out);
            Line.serializeStaticState(out);
            out.writeObject(shapes);
        }
    }

    static class RecoveringCADState  {
        public static void main(String[] args) throws IOException, ClassNotFoundException {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("CADState.out"));
//            List<Class<? extends Shape>> shapeTypes = (List<Class<? extends Shape>>) in.readObject();
//            System.out.println(shapeTypes);
//            Line.deserializeStaticState(in);
            System.out.println(Circle.RED);
            Square.deserializeStaticState(in);
            Circle.deserializeStaticState(in);
            Line.deserializeStaticState(in);
            List<Shape> shapes = (List<Shape>) in.readObject();
            System.out.println(shapes);
            System.out.println(Circle.color);
        }
    }

    static class A {
        public static void sayHello() {
            System.out.println("SayHello");
        }
    }

    static class B extends A {

    }

    public static void main(String[] args) {
        B.sayHello();
    }
}
