package cn.design.patterns.states.simple3;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public interface State {

    void insertQuarter();
    void ejectQuarter();
    void turnCrank();
    void dispense();
}
