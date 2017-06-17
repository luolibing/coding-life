package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/5.
 */
public class StarbuzzCoffee {

    public static void main(String[] args) {
        Beverage beverage1 = new Espresso();
        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());

        Beverage beverage2 = new DarkRoast();
        beverage2 = new Milk(beverage2);
        beverage2 = new Soy(beverage2);
        beverage2 = new Milk(beverage2);
        System.out.println(beverage2.getDescription() + " $" + beverage2.cost());
    }
}
