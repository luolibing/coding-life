package cn.design.patterns.states.simple3;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class NoQuarterState extends DefaultState {

    private GumballMachine gumballMachine;

    public NoQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("you inserted a quarter");
        gumballMachine.setState(gumballMachine.getHasQuarterState());
    }
}
