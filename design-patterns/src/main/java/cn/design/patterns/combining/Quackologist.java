package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 * 观察者
 */
public class Quackologist implements Observer {
    @Override
    public void update(QuackObservable observable) {
        System.out.println("quack:" + observable + " just quacked");
    }
}
