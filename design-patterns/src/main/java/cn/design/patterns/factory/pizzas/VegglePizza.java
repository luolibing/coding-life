package cn.design.patterns.factory.pizzas;

/**
 * Created by LuoLiBing on 16/5/6.
 */
public class VegglePizza extends Pizza {

    public VegglePizza() {
        name = "Veggle Pizza";
        dough = "Veggle Crust";
        sauce = "Veggle Pizza Sauce";
        toppings.add("Fresh Mozzarella");
        toppings.add("Parmesan");
    }
}
