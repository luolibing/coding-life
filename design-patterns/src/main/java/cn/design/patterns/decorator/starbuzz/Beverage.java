package cn.design.patterns.decorator.starbuzz;

/**
 * Created by LuoLiBing on 16/5/4.
 * 饮料类
 * 设计原则:
 * 1 对扩展开放,对修改关闭,开放关闭原则
 * 2 装饰模式动态地将责任附加到对象上,若要扩展功能,装饰着提供了比继承更有弹性的替代方案
 * 3 用Milk来装饰DarkRoast咖啡,动态的给咖啡添加配料,可以很好的扩展咖啡配料
 * 4 装饰模式,装饰者与被装饰者属于同一类型,实现同样的接口,可以用装饰者来替代被装饰者,给被装饰者动态的添加行为,例如给咖啡加上牛奶,糖
 */
public abstract class Beverage {

    String description = "unknow beverage";

    public String getDescription() {
        return description;
    }

    /**
     * 计算价格
     * @return
     */
    public abstract double cost();
}
