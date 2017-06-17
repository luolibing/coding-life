package cn.design.patterns.observer.weatherobserver;

import java.util.Observable;

/**
 * Created by LuoLiBing on 16/4/29.
 * 将之前主体主动推送的发送改为由观察者主动来拉取数据的方式
 * 这样我们只需要将主体传给观察者,至于观察者需要多少数据,由他自己决定
 *
 * JDK提供的API,将维护观察者的工作全部给封装了起来,我们不需要再自行去实现维护观察者的代码
 */
public class WeatherData extends Observable {

    private float temperature;

    private float humidity;

    private float pressure;

    public WeatherData() {
    }

    /**
     * 变化的时候通知给ObserverList数组
     */
    public void measurementsChanged() {
        // 通过一个changed字段决定是否需要通知给订阅者,例如变化没有超多0.5度不发送通知,这个地方可以灵活处理
        setChanged();
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
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
