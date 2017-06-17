package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 */
public class MallarkDuck implements Quackable {

    private Observable observable;

    public MallarkDuck() {
        this.observable = new Observable(this);
    }

    @Override
    public void quack() {
        System.out.println("mallarkduck quack");
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
