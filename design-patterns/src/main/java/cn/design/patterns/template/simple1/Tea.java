package cn.design.patterns.template.simple1;

/**
 * Created by LuoLiBing on 16/6/12.
 */
public class Tea extends CaffeineBeverage {

    @Override
    void brew() {
        // 加入茶叶
        System.out.println("steeping the tea");
    }

    @Override
    void addCondiments() {
        // 加入柠檬
        System.out.println("adding lemon");
    }
}
