package cn.tim.thinking.clazz14;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by LuoLiBing on 16/9/27.
 */
public class InstanceOfTest {


    class Building {}
    class House extends Building {}

    /**
     * 由于编译器知道某个类是另一个类的子类,所以允许自由的向上转型;无法知道给定的父类型对象具体存储的是什么类型,所以不允许向下转型,除非显示强转. 运行时还会进行类型检查.
     * RTTI第三种方式dog instanceof Dog
     */
    @Test
    public void instanceOf() {
        House house = new House();
        // instanceof 可以判断一个实例是否是该类或者从该类的衍生类
        System.out.println(house instanceof House);
        System.out.println(house instanceof Building);
    }

    /**
     * 动态instanceOf
     * 使用Class.isInstance()方法可以使用通用的Class进行判断
     * clazz.isInstance(houseInstance),查询实例是否属于某个类或者由其衍生
     */
    @Test
    public void dynamicInstanceOf() {
        House house = new House();
        System.out.println(Building.class.isInstance(house));
        System.out.println(House.class.isInstance(house));
        System.out.println(ArrayList.class.isInstance(new ArrayList<>()));
    }

    /**
     * isAssignableFrom
     * BaseType.isAssignableFrom(childType)判断一个Class是否从另一个继承而来
     */
    @Test
    public void isAssignableFrom() {
        System.out.println(Building.class.isAssignableFrom(Building.class));
        System.out.println(Building.class.isAssignableFrom(House.class));
    }

    class Base {}
    class Derived extends Base {}

    /**
     * instanceOf或者isInstance指的是你是这个类吗,或者你是这个类的派生类吗, 而用==或者equals比较的是实际的对象,并没有考虑继承
     */
    @Test
    public void test1() {
        Base base = new Base();
        System.out.println(base instanceof Base);
        System.out.println(base instanceof Derived);

        System.out.println(base.getClass().equals(Base.class));
    }
}
