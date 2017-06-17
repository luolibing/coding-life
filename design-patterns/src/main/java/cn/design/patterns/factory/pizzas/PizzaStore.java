package cn.design.patterns.factory.pizzas;

/**
 * Created by LuoLiBing on 16/5/6.
 * 设计原则:
 * 1 开放关闭原则,对扩展开放,对修改关闭,将创建对象new的动作封装到factory当中,可以将不变的动作封装到Pizza类中
 * 2 通过一个简单工厂来封装创建对象的细节,一个简单工厂可以创建出一个接口类型的东西,例如一个会制造披萨的披萨工厂
 *   披萨类是一个抽象类,抽象类会有一个默认的制作披萨的过程
 * 3 将一个披萨工厂factory传入到PizzaStore当中,所有的pizza的制作都封装到这个factory当中,制作披萨的方法一般是静态的
 */
public class PizzaStore {

    private SimplePizzaFactory simplePizzaFactory;

    public PizzaStore(SimplePizzaFactory simplePizzaFactory) {
        this.simplePizzaFactory = simplePizzaFactory;
    }

    public Pizza orderPizza(String type) {
        Pizza pizza = simplePizzaFactory.createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
