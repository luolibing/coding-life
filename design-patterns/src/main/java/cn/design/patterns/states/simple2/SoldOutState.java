package cn.design.patterns.states.simple2;

/**
 * Created by LuoLiBing on 16/6/20.
 */
public class SoldOutState implements State {

    private GumballMachine gumballMachine;

    public SoldOutState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("gumball has sold out");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("gumball has sold out");
    }

    @Override
    public void turnCrank() {
        System.out.println("gumball has sold out");
    }

    @Override
    public void dispense() {
        System.out.println("gumball has sold out");
    }
}
