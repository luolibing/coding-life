package cn.design.patterns.factory.pizzas;

/**
 * Created by LuoLiBing on 16/5/6.
 */
public class PepperoniPizza extends Pizza {

    public PepperoniPizza() {
        name = "Pepperoni Pizza";
        dough = "Pepperoni Crust";
        sauce = "Pepperoni Pizza Sauce";
        toppings.add("Fresh Mozzarella");
        toppings.add("Parmesan");
    }
}
