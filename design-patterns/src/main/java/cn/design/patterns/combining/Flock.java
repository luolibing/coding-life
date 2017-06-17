package cn.design.patterns.combining;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LuoLiBing on 16/6/25.
 * 4 组合
 */
public class Flock implements Quackable {

    private List<Quackable> duckList = new ArrayList<>();

    private Observable observable;

    public Flock() {
        this.observable = new Observable(this);
    }

    public void addDuck(Quackable duck) {
        duckList.add(duck);
    }

    @Override
    public void quack() {
        Iterator<Quackable> iterator = duckList.iterator();
        while(iterator.hasNext()) {
            Quackable duck = iterator.next();
            duck.quack();
        }
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
