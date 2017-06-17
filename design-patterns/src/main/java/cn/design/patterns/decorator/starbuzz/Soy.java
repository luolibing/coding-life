package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 * 配料
 */
public class Soy extends CondimentDecorator {

    private Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",soy!!!";
    }

    @Override
    public double cost() {
        return 0.3 + beverage.cost();
    }
}
