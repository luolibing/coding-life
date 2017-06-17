package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 * 2 使用装饰者模式
 * 使用装饰模式来统计叫声
 * 添加行为
 */
public class QuackCounter implements Quackable {

    private Quackable duck;

    private static int numberOfQuack = 0;

    public QuackCounter(Quackable duck) {
        this.duck = duck;
    }

    @Override
    public void quack() {
        duck.quack();
        numberOfQuack++;
    }

    public static int getNumberOfQuack() {
        return numberOfQuack;
    }

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }
}
