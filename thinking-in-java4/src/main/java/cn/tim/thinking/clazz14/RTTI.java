package cn.tim.thinking.clazz14;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by LuoLiBing on 16/9/23.
 * 运行时类型信息可以使你可以在程序运行时发现和使用类型信息
 * 它使你从只能在编译器执行面向对象的操作的禁锢中解脱出来
 * 运行时识别对象和类信息的两种方式:
 * 1 传统的RTTI,它假设我们在编译时已经知道了所有的类型
 * 2 反射机制,允许我们在运行时发现和使用类信息
 *
 */
public class RTTI {

    abstract class Shape {
        protected int flag;

        public void draw() {
            // 自动调用this.toString()方法
            System.out.println(this + ".draw()");
        }

        public void sign() {
            flag = 1;
        }

        // 覆写了父类的toString()方法,将其改为抽象方法
        public String toString() {
            return flag + "";
        }
    }

    class Circle extends Shape {

        @Override
        public String toString() {
            return "Circle " + super.toString();
        }

    }

    class Square extends Shape {

        @Override
        public String toString() {
            return "Square " + super.toString();
        }
    }

    class Triangle extends Shape {

        @Override
        public String toString() {
            return "Triangle " + super.toString();
        }
    }

    class Rhomboid extends Shape {

        @Override
        public String toString() {
            return "Rhomboid " + super.toString();
        }
    }

    @Test
    public void test1() {
        List<Shape> shapeList = Arrays.asList(new Circle(), new Square(), new Triangle());
        // 实际上ArrayList里面是使用了Object[]存储elements,都会当做Object,当从list中取出元素时,会根据泛型对其进行强转. (E) elementData[index]
        // 在Java中,所有的类型转换都是在运行时进行正确性检查,这就是RTTI,在运行时识别一个对象的类型.
        // RTTI (run-time type Identification)运行时类型识别
        shapeList.get(1);
        for(Shape shape : shapeList) {
            if(shape instanceof Circle) {
                shape.sign();
            }
            shape.draw();
        }
        System.out.println();
        Shape rhomboid = new Rhomboid();
        rhomboid.draw();
        if(rhomboid instanceof Rhomboid) {
            ((Rhomboid) rhomboid).draw();
        }

        if(rhomboid instanceof Circle) {
            ((Circle) rhomboid).draw();
        }
    }


    /**
     * RTTI工作是由成为Class对象的特殊对象完成的,它包含与类相关的信息.
     * 每个类都有一个Class对象,编写一个新类编译就会产生一个Class文件.然后当需要使用这个类时,使用JVM类加载器进行加载过程最终产生Class对象
     *
     * 类加载器
     * 类加载器子系统包含一条类加载器链,但只有一个原生类加载器,它是JVM实现的一部分. 原生类加载器加载的是所谓的可信类,包括java API类.
     * 所有类都是在对其第一次使用时,动态加载到JVM中的.当程序窗机看第一个对类的静态成员的引用时,就会加载这个类.这证明构造函数也是类的静态方法.
     *
     *
     */
    @Test
    public void test2() {

        System.out.println("inside main");
        // 构造函数加载
        new Candy();
        System.out.println("After creating Candy");
        try {
            // 使用Class.forName()加载,内部类加载方式 RTTI$Gum
            Class.forName("cn.tim.thinking.clazz14.RTTI$Gum");
        } catch (ClassNotFoundException e) {
            System.out.println("could't find gum");
        }
        System.out.println("After Class.forName(Gum)");
        new Cookie();
        System.out.println("After");
    }

    static class Candy extends Gum {
        static {
            System.out.println("Loading Candy");
        }
    }

    static class Gum {
        static {
            System.out.println("Loading Gum");
        }
    }

    static class Cookie {
        static  {
            System.out.println("Loading Cookie");
        }
    }


    interface HasBatteries {}
    interface Waterproof {}
    interface Shoots extends Spoot {}
    interface Spoot {}

    class Toy {
        Toy() {}
        Toy(int i) {}
    }

    class FancyToy extends Toy implements HasBatteries, Waterproof, Shoots {
//        FancyToy() {super(1);}
    }

    public void printInfo(Class clazz) {
        System.out.println("className: " + clazz.getName() + " is interface? [" + clazz.isInterface() + "]");
        System.out.println("Simple name: " + clazz.getSimpleName());
        System.out.println("Canonical name: " + clazz.getCanonicalName());
    }

    @Test
    public void test3() {
        Class c  = null;
        try {
            c = Class.forName("cn.tim.thinking.clazz14.RTTI$FancyToy");
        } catch (ClassNotFoundException e) {
            System.out.println("can't find FancyToy");
            System.exit(1);
        }
        printInfo(c);
        System.out.println();

        // 打印接口
        for(Class face : c.getInterfaces()) {
            printInfo(face);
            System.out.println();
        }

        // 初始化
        Class up = c.getSuperclass();
        Object obj = null;
        try {
            // 使用newInstance()方法需要有一个无参构造函数
            obj = up.newInstance();
        } catch (InstantiationException e) {
            System.out.println("can not instantiate");
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println("can not access");
            System.exit(1);
        }
        printInfo(obj.getClass());
    }

    public static void main(String[] args) {
        String basePackage = "cn.tim.thinking.clazz14.RTTI$";
        List<Shape> shapes = new ArrayList<>();
        for(String arg : args) {
            try {
                Class<?> clazz = Class.forName(basePackage + arg);
                shapes.add((Shape) clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test4() {
        System.out.println("class:");
        printExtendInfo(ArrayList.class);
        System.out.println();
        System.out.println("interface:");
        printInterfaceInfo(ArrayList.class);
        System.out.println();
        System.out.println("detail: ");
        printClassDetail(ArrayList.class);
    }

    public void printExtendInfo(Class clazz) {
        System.out.println(clazz.getName());
        Class superclass = clazz.getSuperclass();
        if(superclass != null) {
            printExtendInfo(superclass);
        }
    }

    public void printInterfaceInfo(Class clazz) {
        Class[] interfaces = clazz.getInterfaces();
        for(Class in : interfaces) {
            if(in.getInterfaces() != null) {
                System.out.println(in.getName());
                if(in.getSuperclass() != null) {
                    printExtendInfo(in.getSuperclass());
                }
            }
        }
    }

    public void printClassDetail(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            System.out.println(field.getType().getName() + " :" + field.getName());
        }
    }

    @Test
    public void test5() {
        char[] chars = new char[5];
        int[] ints = new int[] {2};
        List<String> list = new ArrayList<>();
        System.out.println(chars.getClass());
        // 判断是否是一个基本类型使用isPrimitive()方法 isArray()是否是一个数组,isEnum枚举, chars不是一个对象类型,也不是一个基本类型,是一个数组
        System.out.println(chars.getClass().isPrimitive());
        System.out.println(chars.getClass().isArray());
        System.out.println(list.getClass().isPrimitive());
    }

    /**
     * 类字面常量: 使用类字面常量更安全,因为编译时就已经受到检查,因此更高效. 使用类字面常量不会自动初始化该class对象.
     * 类加载过程:
     * 1 加载,由类加载器执行的.查找字节码文件,并且从这些字节码中创建一个Class对象
     * 2 链接,验证类中的字节码,为静态域分配存储空间,如果必需的话,将解析这个类创建的对其他类的所有引用
     * 3 初始化.如果该类有超类,先对其进行初始化,执行静态初始化器和静态快
     */
    @Test
    public void test6() throws ClassNotFoundException {
        Class<FancyToy> fancyToyClass = FancyToy.class;
        ClassLoader classLoader = fancyToyClass.getClassLoader();
        classLoader.loadClass(FancyToy.class.getName());
        Class<Integer> type = Integer.TYPE; // Class.getPrimitiveClass("int")
    }

    static class Initable {
        final static int staticFinal = 47;
        final static int staticFinal2 = rand.nextInt(100);
        static  {
            System.out.println("Init Initable");
        }
    }

    static class Initable2 {
        static int staticNonFinal = 147;
        static  {
            System.out.println("init Initable2");
        }
    }

    static class Initable3 {
        static int staticNonFinal = 74;
        static  {
            System.out.println("init Initable3");
        }
    }

    static Random rand = new Random(47);

    /**
     * 初始化有效地实现了尽可能的惰性
     * 1 仅使用.class类字面常量来获得对类的引用不会引发初始化, 但是使用Class.forName()立即就进行了初始化
     * 2 如果一个static final值是"编译期常量",那么这个值不需要进行初始化就可以被读取.而同样是final static Initable.staticFinal2并不是编译期常量,所以不会引发加载
     * 3 如果一个static域不是final的,那么在对它访问时,总是要求在它被读取之前,要先进行链接(为这个域分配存储空间)和初始化(初始化该存储空间),所以会要求加载
     */
    @Test
    public void test7() {
        // 类字面常量并不会导致类被加载
        Class<Initable> initable = Initable.class;
        System.out.println("After creating Initable ref");
        // final static常量 例如final static int i = 10;并不会导致类被加载
        System.out.println(Initable.staticFinal);
        // final static常量,但是调用了random.next()方法,类被加载
        System.out.println(Initable.staticFinal2);

        // 而使用非final型的static int i = 147;却会导致类被加载
        System.out.println(Initable2.staticNonFinal);

        try {
            // class.forName()会触发类加载
            Class<?> initable3 = Class.forName("cn.tim.thinking.clazz14.RTTI$Initable3");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("After creating Initable3 ref");
        System.out.println(Initable3.staticNonFinal);
    }

    @Test
    public void test8() {
        Class integerClass = int.class;
        // 泛型
        Class<Integer> integerClass1 = int.class;

        // error Number class并不是Integer class的父类, Class<Number> integerClass2 = int.class;
        Class<?> integerClass2 = int.class;
        // Class<?>优于平凡的Class,即使它们是等价的. 使用class可能要等到运行时才能发现错误,而使用泛型会在编译器发现错误的存在
        // 使用通配符与extends关键字相结合,创建一个范围.
        Class<? extends Number> bounded = int.class;
        bounded = double.class;
    }

    static class CountedInteger {
        private static long counter;
        private final long id = counter ++;
        public String toString() {
            return Long.toString(id);
        }
    }

    public class FilledList<T> {
        private Class<T> type;
        public FilledList(Class<T> type) { this.type = type; }
        public List<T> create(int nElements) {
            List<T> list = new ArrayList<>();
            for(int i = 0; i < nElements; i++) {
                try {
                    // Class<T>加了泛型,使用newInstance时不需要进行强转
                    list.add(type.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }
    }

    @Test
    public void test9() {
        FilledList<CountedInteger> filled = new FilledList<>(CountedInteger.class);
        List<CountedInteger> list = filled.create(10);
        list.forEach(System.out::println);
    }

    @Test
    public void test10() throws IllegalAccessException, InstantiationException {
        Class<FancyToy> ftClass = FancyToy.class;
        FancyToy fancyToy = ftClass.newInstance();
        Class<? super FancyToy> up = ftClass.getSuperclass();
        // getSuperclass只会返回? super class泛型,返回某个类的超类 Class<Toy> superclass = ftClass.getSuperclass();
        // 由于不知道up的具体类型,所以newInstance的时候会返回Object类型
        Object o = up.newInstance();
        String[] array = new String[] {,};
        System.out.println(array.length);
    }

    @Test
    public void test11() {
    }
}
