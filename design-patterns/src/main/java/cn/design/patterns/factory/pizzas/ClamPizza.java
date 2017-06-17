package cn.design.patterns.factory.pizzas;

/**
 * Created by LuoLiBing on 16/5/6.
 */
public class ClamPizza extends Pizza {

    public ClamPizza() {
        name = "Clam Pizza";
        dough = "Clam Crust";
        sauce = "Clam Pizza Sauce";
        toppings.add("Fresh Mozzarella");
        toppings.add("Parmesan");
    }
}
