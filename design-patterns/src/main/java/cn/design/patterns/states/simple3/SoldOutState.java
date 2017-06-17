package cn.design.patterns.states.simple3;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class SoldOutState extends DefaultState {
    private GumballMachine gumballMachine;

    public SoldOutState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
}
