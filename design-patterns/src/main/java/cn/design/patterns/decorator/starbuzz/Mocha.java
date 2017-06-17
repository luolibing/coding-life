package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 * 配料
 */
public class Mocha extends CondimentDecorator {

    private Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",mocha!!!";
    }

    @Override
    public double cost() {
        return 0.2 + beverage.cost();
    }
}
