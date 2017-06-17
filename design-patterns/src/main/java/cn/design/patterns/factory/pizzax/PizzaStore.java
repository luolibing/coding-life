package cn.design.patterns.factory.pizzax;

/**
 * Created by LuoLiBing on 16/5/7.
 * pizza店铺
 *
 * 工厂方法模式:
 * 1 定义了一个创建对象的接口,但由子类决定要实例化的类是哪一个.工厂方法让类把实例化推迟到子类
 * 2 所有产品实现同一个接口,工厂也一样要实现同一个工厂类接口,将创建对象的动作交给子类工厂实现
 *
 * 依赖倒置原则
 * 1 要依赖抽象,不要依赖具体类=针对接口编程,不针对实现编程
 * 2
 *
 */
public abstract class PizzaStore {

    abstract Pizza createPizza(String item);

    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        System.out.println("--- Making a " + pizza.getName() + " ---");
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
