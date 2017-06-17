package cn.design.patterns.states.simple2;

/**
 * Created by LuoLiBing on 16/6/20.
 * 定义一个状态接口,这个接口包含所有动作
 */
public interface State {
    void insertQuarter();
    void ejectQuarter();
    void turnCrank();
    void dispense();
}
