package cn.design.patterns.strategy.well;

/**
 * Created by LuoLiBing on 16/4/28.
 */
public class FlyNoWay implements FlyBehavior {

    @Override
    public void fly() {
        System.out.println("i can't fly!!!");
    }
}
