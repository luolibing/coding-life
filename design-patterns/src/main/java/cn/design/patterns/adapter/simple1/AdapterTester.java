package cn.design.patterns.adapter.simple1;

/**
 * Created by LuoLiBing on 16/6/10.
 * 适配器测试  client需要一个duck对象,使用turkeyAdapter来适配被适配者turkey
 */
public class AdapterTester {

    public static void main(String[] args) {
        // 正常的鸭子
        MallardDuck duck = new MallardDuck();
        duck.quack();
        duck.fly();

        // 用适配器制作出来的火鸡鸭子
        WildTurkey turkey = new WildTurkey();
        Duck turkeyDuck = new TurkeyAdapter(turkey);
        turkeyDuck.quack();
        turkeyDuck.fly();
    }
}
