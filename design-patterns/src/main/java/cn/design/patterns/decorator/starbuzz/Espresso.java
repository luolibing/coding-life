package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 * 咖啡类
 */
public class Espresso extends Beverage {

    public Espresso() {
        description = "espresso Coffee!!!";
    }

    @Override
    public double cost() {
        return 8;
    }
}