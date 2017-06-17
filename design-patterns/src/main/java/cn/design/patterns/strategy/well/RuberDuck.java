package cn.design.patterns.strategy.well;

/**
 * Created by LuoLiBing on 16/4/28.
 */
public class RuberDuck extends Duck {

    public RuberDuck() {
        setFlyBehavior(new FlyWithWings());
        setQuackBehavior(new Quack());
    }

    @Override
    void display() {
        System.out.println("i am a duck ruber!!!");
    }
}
