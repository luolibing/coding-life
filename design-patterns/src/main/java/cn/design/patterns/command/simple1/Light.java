package cn.design.patterns.command.simple1;

/**
 * Created by LuoLiBing on 16/6/3.
 * 具体目标执行实现端
 */
public class Light {

    public Light() {}

    public void on() {
        System.out.println("Light is on!!!");
    }

    public void off() {
        System.out.println("Light is off!!!");
    }
}
