package cn.design.patterns.factory.pizzas;

/**
 * Created by LuoLiBing on 16/5/6.
 */
public class SimplePizzaFactory {

    public Pizza createPizza(String type) {
        Pizza pizza = null;
        if("cheese".equals(type)) {
            pizza = new CheesePizza();
        } else if("veggle".equals(type)) {
            pizza = new VegglePizza();
        } else if("clam".equals(type)) {
            pizza = new ClamPizza();
        } else if("pepperoni".equals(type)) {
            pizza = new PepperoniPizza();
        }
        return pizza;
    }
}
