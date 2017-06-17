package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 * 鸭鸣器,能叫但不是鸭子
 */
public class DuckCall implements Quackable {

    private Observable observable;

    public DuckCall() {
        this.observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("Kwak");
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }

    @Override
    public void notifyObservers() {
        observable.notifyObservers();
    }
}
