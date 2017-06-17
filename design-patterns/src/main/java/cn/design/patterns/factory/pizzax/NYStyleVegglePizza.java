package cn.design.patterns.factory.pizzax;

/**
 * Created by LuoLiBing on 16/5/7.
 */
public class NYStyleVegglePizza extends Pizza {

    public NYStyleVegglePizza() {
        name = "NY Style Sauce and Cheese Pizza";
        dough = "Thin Crust Dough";
        sauce = "Marinara Sauce";

        toppings.add("Grated Reggiano Cheese");
    }
}
