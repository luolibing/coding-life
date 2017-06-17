package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 * 配料
 */
public class Whip extends CondimentDecorator {

    private Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",whip!!!";
    }

    @Override
    public double cost() {
        return 0.4 + beverage.cost();
    }
}
