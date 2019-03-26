package com.tim.spi.api;

public class Connector {

    private Driver driver;

    public Connector(Driver driver) {
        this.driver = driver;
    }

    public void connect() {
        String result = driver.connect("127.0.0.1", 80);
        System.out.println("connect result : " + result);
    }

    public void disconect() {
        String result = driver.disConnect();
        System.out.println("disconnect result : " + result);
    }
}
