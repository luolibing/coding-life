package cn.design.patterns.strategy.well;

/**
 * Created by LuoLiBing on 16/4/28.
 * 1 多用组合,少用继承
 * 2 封装变化,变化与不变分离的部分进行分离
 * 3 针对接口编程,而不是针对实现
 *
 * duck并不是实现各种类型的鸭子,而是委托delegate给其他接口
 *
 * duck写完之后就可以不再修改,除非给duck添加新的行为类型
 * 例如swimBehavior,要新增一种鸭子只需要自定义一个duck继承Duck
 *
 * 同样,要新增一种飞行方式,也只需要新增一个继承自FlyBehavior类
 *
 * '有一个'可能比'是一个'更好,有一个使用的是委托的做法
 * 策略模式定义了算法族,分别封装起来,让他们之间可以互相替换,此模式让算法的变化独立于使用算法的客户
 *
 * oo 基础: 抽象 封装 多态 继承
 *
 */
public abstract class Duck {

    private FlyBehavior flyBehavior;

    private QuackBehavior quackBehavior;

    public Duck() {}

    public void swim() {
        System.out.println("All ducks float, even decoys!");
    }

    abstract void display();

    public void performQuack() {
        display();
        quackBehavior.quack();
    }

    public void performFly() {
        display();
        flyBehavior.fly();
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
