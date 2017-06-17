package cn.design.patterns.strategy.well;

/**
 * Created by LuoLiBing on 16/4/28.
 */
public class Quack implements QuackBehavior {

    @Override
    public void quack() {
        System.out.println("嘎嘎叫");
    }
}
