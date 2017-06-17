package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 */
public class RubberDuck implements Quackable {
    private Observable observable;

    public RubberDuck() {
        this.observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("squeak");
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
