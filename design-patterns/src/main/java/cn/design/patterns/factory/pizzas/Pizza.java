package cn.design.patterns.factory.pizzas;

import java.util.ArrayList;

/**
 * Created by LuoLiBing on 16/5/6.
 */
public abstract class Pizza {

    /**
     * 披萨名称
     */
    String name;
    /**
     * 面团
     */
    String dough;
    /**
     * 酱料
     */
    String sauce;
    /**
     * 加的东西
     */
    ArrayList<String> toppings = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void prepare() {
        System.out.println("Preparing " + name);
    }

    public void bake() {
        System.out.println("Baking " + name);
    }

    public void cut() {
        System.out.println("Cutting " + name);
    }

    public void box() {
        System.out.println("Boxing " + name);
    }

    public String toString() {
        StringBuilder display = new StringBuilder();
        display.append("---- ")
                .append(name)
                .append(" ----\n")
                .append(dough)
                .append("\n")
                .append(sauce)
                .append("\n");
        for (String topping : toppings) {
            display.append(topping + "\n");
        }
        return display.toString();
    }
}
