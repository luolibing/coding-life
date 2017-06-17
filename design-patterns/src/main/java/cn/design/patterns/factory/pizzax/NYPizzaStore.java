package cn.design.patterns.factory.pizzax;

/**
 * Created by LuoLiBing on 16/5/7.
 */
public class NYPizzaStore extends PizzaStore {

    @Override
    Pizza createPizza(String item) {
        if("cheese".equals(item)) {
            return new NYStyleCheesePizza();
        } else if("clam".equals(item)) {
            return new NYStyleClamPizza();
        } else if("veggle".equals(item)) {
            return new NYStyleVegglePizza();
        } else if("pepperoni".equals(item)) {
            return new NYStylePepperoniPizza();
        }
        return null;
    }
}
