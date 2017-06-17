package cn.tim.thinking.concurrent21.components;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 16/11/12.
 * 定时任务调度器
 */
public class ScheduledExecutorDemo {

    static class GreenhouseScheduler {
        private volatile boolean light = false;
        private volatile boolean water = false;
        private String thermostat  = "Day";

        public synchronized String getThermostat() {
            return thermostat;
        }

        public synchronized void setThermostat(String value) {
            this.thermostat = value;
        }

        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);

        // 定点延迟执行
        public void schedule(Runnable event, long delay) {
            scheduler.schedule(event, delay, TimeUnit.MILLISECONDS);
        }

        // 重复执行
        public void repeat(Runnable event, long initialDelay, long period) {
            scheduler.scheduleAtFixedRate(event, initialDelay, period, TimeUnit.MILLISECONDS);
        }

        class LightOn implements Runnable {

            @Override
            public void run() {
                System.out.println("Turning on lights");
                light = true;
            }
        }

        class LightOff implements Runnable {

            @Override
            public void run() {
                System.out.println("Turning off lights");
                light = false;
            }
        }

        class WaterOn implements Runnable {

            @Override
            public void run() {
                System.out.println("Turning greenhouse water on");
                water = true;
            }
        }

        class WaterOff implements Runnable {

            @Override
            public void run() {
                System.out.println("Turning greenhouse water off");
                water = false;
            }
        }

        class ThermostatNight implements Runnable {

            @Override
            public void run() {
                System.out.println("Thermostat to night setting");
                setThermostat("Night");
            }
        }

        class ThermostatDay implements Runnable {

            @Override
            public void run() {
                System.out.println("Thermostat to day setting");
                setThermostat("Day");
            }
        }

        class Bell implements Runnable {

            @Override
            public void run() {
                System.out.println("Bing!");
            }
        }

        // 终止程序
        class Terminate implements Runnable {

            @Override
            public void run() {
                System.out.println("Terminating");
                scheduler.shutdownNow();
                new Thread() {
                    @Override
                    public void run() {
                        for(DataPoint d : data) {
                            System.out.println(d);
                        }
                    }
                }.start();
            }
        }

        static class DataPoint {
            final Calendar time;
            final float temperature;
            final float humidity;

            public DataPoint(Calendar time, float temperature, float humidity) {
                this.time = time;
                this.temperature = temperature;
                this.humidity = humidity;
            }

            public String toString() {
                return time.getTime() +
                        String.format(" temperature: %1$.1f humidity: %2$.2f",
                                temperature, humidity);
            }
        }

        private Calendar lastTime = Calendar.getInstance();

        {
            lastTime.set(Calendar.MINUTE, 30);
            lastTime.set(Calendar.SECOND, 00);
        }

        private float lastTemp = 65.0f;
        private int tempDirection = + 1;
        private float lasthumidity = 50.0f;
        private int humidityDirection = +1;
        private Random rand = new Random(47);
        List<DataPoint> data = Collections.synchronizedList(new ArrayList<>());

        class CollectData implements Runnable {

            @Override
            public void run() {
                System.out.println("Collecting data");
                synchronized (GreenhouseScheduler.this) {
                    lastTime.set(Calendar.MINUTE, lastTime.get(Calendar.MINUTE) + 30);
                    if(rand.nextInt(5) == 4) {
                        tempDirection = - tempDirection;
                    }
                    lastTemp = lastTemp + tempDirection * (1.0f + rand.nextFloat());
                    if(rand.nextInt(5) == 4) {
                        humidityDirection = -humidityDirection;
                    }
                    lasthumidity = lasthumidity + humidityDirection * rand.nextFloat();
                    data.add(new DataPoint((Calendar) lastTime.clone(), lastTemp, lasthumidity));
                }
            }
        }

        public static void main(String[] args) {
            // 定时任务.
            GreenhouseScheduler gh = new GreenhouseScheduler();
            gh.schedule(gh.new Terminate(), 5000);
            gh.repeat(gh.new Bell(), 0, 1000);
            gh.repeat(gh.new ThermostatNight(), 0, 2000);
            gh.repeat(gh.new LightOn(), 0, 200);
            gh.repeat(gh.new LightOff(), 0, 400);
            gh.repeat(gh.new WaterOn(), 0, 600);
            gh.repeat(gh.new WaterOff(), 0, 800);
            gh.repeat(gh.new ThermostatDay(), 0, 1400);
            gh.repeat(gh.new CollectData(), 500, 500);
        }
    }
}
