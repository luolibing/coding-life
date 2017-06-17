package cn.design.patterns.factory.pizzax;

/**
 * Created by LuoLiBing on 16/5/7.
 */
public class PizzaStoreTest {

    public static void main(String[] args) {
        // 创建出一个NYStylePizzaStore 纽约披萨工厂
        PizzaStore nyStore = new NYPizzaStore();
        // 由这个纽约披萨工厂生产出来的pizza为纽约风味的披萨
        Pizza cheesePizza = nyStore.orderPizza("cheese");
        System.out.println(cheesePizza);

        Pizza clamPizza = nyStore.orderPizza("clam");
        System.out.println(clamPizza);
    }
}
