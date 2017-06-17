package cn.design.patterns.observer.weather;

/**
 * Created by LuoLiBing on 16/4/29.
 * 这个只关注温度与湿度
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {

    private float temperature;

    private float humidity;

    private WeatherData weatherData;

    /**
     * 观察者需要传入一个主体Subject,然后将观察者注册到订阅名单里面
     * @param weatherData
     */
    public CurrentConditionsDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println(
                String.format("当前情况: 温度 -> %s, 湿度 -> %s", temperature, humidity));
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        display();
    }
}
