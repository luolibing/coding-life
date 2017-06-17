package cn.design.patterns.strategy.well;

/**
 * Created by LuoLiBing on 16/4/28.
 */
public class MiniDuckSimulator {

    public static void main(String[] args) {
        Duck mallardDuck = new MallardDuck();
        Duck rubberDuck = new RuberDuck();
        Duck decoy = new RedheadDuck();

        mallardDuck.performQuack();
        rubberDuck.performQuack();
        decoy.performQuack();

        // 运行时修改行为
        mallardDuck.setQuackBehavior(new MuteQuack());
        mallardDuck.performQuack();
    }
}
