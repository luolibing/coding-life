package cn.design.patterns.strategy.well;

/**
 * Created by LuoLiBing on 16/4/28.
 */
public class MallardDuck extends Duck {

    public MallardDuck() {
        setFlyBehavior(new FlyWithWings());
        setQuackBehavior(new Squeek());
    }

    @Override
    void display() {
        System.out.println("i am duck mallard");
    }
}
