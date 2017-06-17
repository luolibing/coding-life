package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 */
public class DuckSimulator {

    public static void main(String[] args) {
        new DuckSimulator().simulate();
    }

    void simulate() {
        // 加入工厂模式
        AbstractDuckFactory duckFactory = new DuckFactory();
        Quackable mallarkDuck = duckFactory.createMallardDuck();
        Quackable redheadDuck = duckFactory.createRedheadDuck();
        Quackable duckCall = duckFactory.createDuckCall();
        Quackable rubberDuck = duckFactory.createRubberDuck();
        Goose goose = new Goose();
        // 适配成鹅
        Quackable gooseDuck = new GooseAdapter(goose);

        // 统计
        AbstractDuckFactory countingFactory = new CountingDuckFactory();
        Quackable quackCounter = countingFactory.createMallardDuck();
        quackCounter.quack();
        quackCounter = countingFactory.createRedheadDuck();
        quackCounter.quack();
        quackCounter = countingFactory.createDuckCall();
        quackCounter.quack();
        quackCounter = countingFactory.createRubberDuck();
        quackCounter.quack();
        quackCounter = new QuackCounter(gooseDuck);
        quackCounter.quack();
        System.out.println("count=" + QuackCounter.getNumberOfQuack());

        System.out.println("\nDuck Simulator");
        simulate(mallarkDuck);
        simulate(redheadDuck);
        simulate(duckCall);
        simulate(rubberDuck);
        simulate(gooseDuck);

        // 鸭子组合
        Flock flockOfDucks = new Flock();
        flockOfDucks.addDuck(mallarkDuck);
        flockOfDucks.addDuck(redheadDuck);
        flockOfDucks.addDuck(duckCall);
        flockOfDucks.addDuck(rubberDuck);
        flockOfDucks.addDuck(gooseDuck);

        // 添加观察者
        Observer observer = new Quackologist();
        flockOfDucks.registerObserver(observer);
        simulate(flockOfDucks);
    }

    void simulate(Quackable duck) {
        duck.quack();
    }
}
