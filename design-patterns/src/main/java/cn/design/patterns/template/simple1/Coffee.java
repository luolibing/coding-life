package cn.design.patterns.template.simple1;

/**
 * Created by LuoLiBing on 16/6/12.
 */
public class Coffee extends CaffeineBeverage {

    @Override
    void brew() {
        // 加入咖啡
        System.out.println("Dripping Coffee through filter");
    }

    @Override
    void addCondiments() {
        // 咖啡加糖或者牛奶
        System.out.println("Adding sugar and milk");
    }
}
