package cn.design.patterns.states.simple3;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public abstract class DefaultState implements State {

    public void insertQuarter() {
        throw new GumballException();
    }

    public void ejectQuarter() {
        throw new GumballException();
    }

    public void turnCrank() {
        throw new GumballException();
    }

    public void dispense() {
        throw new GumballException();
    }
}
