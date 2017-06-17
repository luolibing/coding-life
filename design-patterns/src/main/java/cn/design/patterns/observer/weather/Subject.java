package cn.design.patterns.observer.weather;

/**
 * Created by LuoLiBing on 16/4/29.
 * 主题接口
 */
public interface Subject {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
