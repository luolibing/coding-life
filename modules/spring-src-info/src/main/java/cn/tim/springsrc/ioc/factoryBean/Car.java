package cn.tim.springsrc.ioc.factoryBean;

/**
 * Created by luolibing on 2017/5/9.
 */
public class Car {

    private int maxSpeed;

    private String brand;

    private double price;

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
