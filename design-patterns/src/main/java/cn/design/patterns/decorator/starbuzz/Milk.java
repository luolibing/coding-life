package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 * 用milk来装饰咖啡
 */
public class Milk extends CondimentDecorator {

    private Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",milk!!!";
    }

    @Override
    public double cost() {
        return 0.9 + beverage.cost();
    }
}
