package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 * 主体被观察者
 */
public interface QuackObservable {

    void registerObserver(Observer observer);
    void notifyObservers();
}
