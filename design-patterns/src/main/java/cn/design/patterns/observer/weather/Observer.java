package cn.design.patterns.observer.weather;

/**
 * Created by LuoLiBing on 16/4/29.
 * 观察者
 */
public interface Observer {

    /**
     *
     * @param temp 温度
     * @param humidity 湿度
     * @param pressure
     */
    void update(float temp, float humidity, float pressure);
}
