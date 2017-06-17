package cn.design.patterns.states.simple3;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class SoldState extends DefaultState {

    private GumballMachine gumballMachine;

    public SoldState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void dispense() {
        gumballMachine.releaseBall();
        if(gumballMachine.getCount() > 0) {
            gumballMachine.setState(gumballMachine.getNoQuarterState());
        } else {
            System.out.println("out of gumballs!!");
            gumballMachine.setState(gumballMachine.getSoldOutState());
        }
    }
}
