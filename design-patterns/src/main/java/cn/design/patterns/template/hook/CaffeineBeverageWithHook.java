package cn.design.patterns.template.hook;

/**
 * Created by LuoLiBing on 16/6/12.
 *
 * 钩子hook
 * 钩子是一种被声明在模板类中,只有空实现或者默认的实现.
 * 钩子的存在可以让子类有能力对算法的不通电进行挂钩,要不要挂钩由子类自行决定.
 * 要不要加配料可以由子类决定,默认是加配料
 *
 * 什么时候使用抽象方法,什么时候使用钩子:
 * 1 当你的子类必须提供算法中某个方法或者步骤的实现时,则使用抽象方法
 * 2 如果算法中的这个部分是可选的,就用钩子
 *
 * 好莱坞原则:
 * 别调用我们,我们会调用你
 * 好莱坞原则可以给我们一种防止依赖腐败的方法.例如高层组件依赖底层组件,然后底层组件又依赖高层,又依赖边测组件
 *
 */
public abstract class CaffeineBeverageWithHook {

    // 制作过程
    final void prepareRecipe() {
        // 烧开水
        boilWater();
        // 冲泡
        brew();
        // 将水倒入杯子
        pourInCup();
        // 加入适当的配料
        if(customerWantsCondiments()) {
            addCondiments();
        }
    }

    abstract void brew();

    abstract void addCondiments();

    boolean customerWantsCondiments() {
        return true;
    }

    void boilWater() {
        System.out.println("Boiling water");
    }

    void pourInCup() {
        System.out.println("Pouring into cup");
    }
}
