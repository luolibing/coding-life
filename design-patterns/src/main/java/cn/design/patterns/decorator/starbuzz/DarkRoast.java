package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 * 咖啡类
 */
public class DarkRoast extends Beverage {

    public DarkRoast() {
        description = "dark roast Coffee!!!";
    }

    @Override
    public double cost() {
        return 10.99;
    }
}
