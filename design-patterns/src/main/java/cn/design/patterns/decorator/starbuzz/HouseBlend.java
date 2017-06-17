package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 * 咖啡类
 */
public class HouseBlend extends Beverage {

    public HouseBlend() {
        description = "house blend Coffee!!!";
    }

    @Override
    public double cost() {
        return 25;
    }
}
