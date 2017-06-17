package cn.design.patterns.combining;

/**
 * Created by LuoLiBing on 16/6/25.
 * 3 工厂设计模式
 */
public abstract class AbstractDuckFactory {
    public abstract Quackable createMallardDuck();
    public abstract Quackable createRedheadDuck();
    public abstract Quackable createDuckCall();
    public abstract Quackable createRubberDuck();
}
