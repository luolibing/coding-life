package com.tim.spi.api;

import java.util.Iterator;
import java.util.ServiceLoader;

public class DriverManager {

    private final static Driver driver;

    static {
        driver = loadDriver();
    }

    private static Driver loadDriver() {

        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        Iterator<Driver> iterator = serviceLoader.iterator();
        if(!iterator.hasNext()) {
            throw new IllegalArgumentException("not support driver impl");
        }

        Driver driver = iterator.next();
        if(iterator.hasNext()) {
            throw new IllegalArgumentException("support two driver impl");
        }
        return driver;
    }

    public static Driver getDriver() {
        return driver;
    }
}
