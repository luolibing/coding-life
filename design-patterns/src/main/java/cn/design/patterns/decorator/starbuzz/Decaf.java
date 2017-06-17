package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 * 咖啡类
 */
public class Decaf extends Beverage {

    public Decaf() {
        description = "decaf Coffee!!!";
    }

    @Override
    public double cost() {
        return 9.9;
    }
}
