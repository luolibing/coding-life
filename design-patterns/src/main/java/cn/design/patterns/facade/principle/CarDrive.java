package cn.design.patterns.facade.principle;

/**
 * Created by LuoLiBing on 16/6/10.
 */
public class CarDrive {

    public static void main(String[] args) {
        new Car().start(new Key());
    }
}
