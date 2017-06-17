package cn.design.patterns.template.simple1;

/**
 * Created by LuoLiBing on 16/6/12.
 * 咖啡因饮料
 * 模板方法定义了一个算法的步骤,并允许子类为一个或多个步骤提供实现.
 *
 * 模板方法模式:
 * 在一个方法中定义一个算法的骨架,而将一些步骤延迟到子类中.模板方法使得子类可以在不改变算法结构的情况下,重新定义算法中的某些步骤
 *
 */
public abstract class CaffeineBeverage {

    // 制作过程
    final void prepareRecipe() {
        // 烧开水
        boilWater();
        // 冲泡
        brew();
        // 将水倒入杯子
        pourInCup();
        // 加入适当的配料
        addCondiments();
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater() {
        System.out.println("Boiling water");
    }

    void pourInCup() {
        System.out.println("Pouring into cup");
    }
}
