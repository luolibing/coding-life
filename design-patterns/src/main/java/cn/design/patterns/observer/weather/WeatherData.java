package cn.design.patterns.observer.weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 16/4/28.
 * 主动推的方式,缺点有时候观察者只需要部分数据,而不是全部数据,需要数据的时候观察者主动去拉可能更合适
 * 观察者定义了对象之间一对多的关系
 *
 * 松耦合的威力
 * 1 当两个对象之间松耦合,他们依然可以交互,但是不太清楚彼此细节,修改一方并不会影响到另一方
 * 2 为了交互对象之间的松耦合设计而努力
 *
 * 观察者模式的实现:
 * 1 包含一个主体,多个观察者
 * 2 观察者模式即订阅与发布模式
 * 3 创建观察者需要传入主体,将观察者注册到订阅者列表当中,观察者也可以取消对主体的订阅
 * 4 一旦主体发生变化,可以通知给所有的订阅者
 */
public class WeatherData implements Subject {

    private float temperature;

    private float humidity;

    private float pressure;

    private List<Observer> observerList;

    public WeatherData() {
        observerList = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        if(observer != null) {
            observerList.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    /**
     * 通知给消费者
     */
    @Override
    public void notifyObservers() {
        for(Observer observer : observerList) {
            observer.update(temperature, humidity, pressure);
        }
    }

    /**
     * 气象站调用的方法
     * @param temperature
     * @param humidity
     * @param pressure
     */
    void measurementsChanged(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
