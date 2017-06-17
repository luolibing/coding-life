package cn.design.patterns.strategy.well;

/**
 * Created by LuoLiBing on 16/4/28.
 */
public class Squeek implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("咕咕叫");
    }
}
