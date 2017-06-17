package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 * 观察者,只有一个update方法,当被观察者发生变化时,被观察者通知给观察者
 */
public interface Observer {

    void update(QuackObservable observable);
}
