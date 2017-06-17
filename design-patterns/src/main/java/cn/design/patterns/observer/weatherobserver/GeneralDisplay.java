package cn.design.patterns.observer.weatherobserver;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by LuoLiBing on 16/5/3.
 */
public class GeneralDisplay implements Observer, DisplayElement {

    private Observable observable;
    private float temperature;
    private float humidity;

    public GeneralDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature
                + "F degrees and " + humidity + "% humidity");
    }

    @Override
    public void update(Observable observable, Object arg) {
        if(observable instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) observable;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }
}
