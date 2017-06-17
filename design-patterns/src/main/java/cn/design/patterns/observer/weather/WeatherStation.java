package cn.design.patterns.observer.weather;

/**
 * Created by LuoLiBing on 16/4/29.
 */
public class WeatherStation {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        Observer currentDisplay = new CurrentConditionsDisplay(weatherData);
        Observer statissticsDisplay = new StatissticsDisplay(weatherData);

        weatherData.measurementsChanged(80, 65, 30.4f);
        System.out.println();
        weatherData.measurementsChanged(82, 70, 29.2f);
        System.out.println();
        // 取消订阅
        weatherData.removeObserver(statissticsDisplay);
        weatherData.removeObserver(currentDisplay);

        weatherData.measurementsChanged(78, 90, 29.2f);

//        ((DisplayElement) currentDisplay).display();
//        ((DisplayElement) statissticsDisplay).display();
    }
}
