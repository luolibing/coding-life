package cn.design.patterns.factory.pizzas;

/**
 * Created by LuoLiBing on 16/5/6.
 */
public class PizzaTestDrive {

    public static void main(String[] args) {
        SimplePizzaFactory factory = new SimplePizzaFactory();
        PizzaStore pizzaStore = new PizzaStore(factory);

        Pizza pizza = pizzaStore.orderPizza("cheese");
        System.out.println("We ordered a " + pizza.getName() + "\n");
        System.out.println(pizza);

        pizza = pizzaStore.orderPizza("veggle");
        System.out.println("We ordered a " + pizza.getName() + "\n");
        System.out.println(pizza);
    }
}
