package cn.design.patterns.strategy.well;

/**
 * Created by LuoLiBing on 16/4/28.
 */
public class FlyWithWings implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("i can fly!!!");
    }
}
