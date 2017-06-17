package cn.design.patterns.template.hook;

/**
 * Created by LuoLiBing on 16/6/12.
 */
public class BeverageTestDrive {

    public static void main(String[] args) {
        CoffeeWithHook coffeeWithHook = new CoffeeWithHook();
        coffeeWithHook.prepareRecipe();
    }
}
