package cn.design.patterns.adapter.simple1;

/**
 * Created by LuoLiBing on 16/6/10.
 * 目标接口Target
 */
public class MallardDuck implements Duck {

    @Override
    public void quack() {
        System.out.println("quack");
    }

    @Override
    public void fly() {
        System.out.println("I'm flying");
    }
}
