package cn.design.patterns.states.simple2;

/**
 * Created by LuoLiBing on 16/6/20.
 * 售出状态,下一个状态是直接分配出糖果
 */
public class SoldState implements State {

    private GumballMachine gumballMachine;

    public SoldState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter() {
        System.out.println("please wait, we're already giving you a gumball");
    }

    @Override
    public void ejectQuarter() {
        System.out.println("sorry,you already turned the crank");
    }

    @Override
    public void turnCrank() {
        System.out.println("turning twice doesn't get you another gumball!!");
    }

    /**
     * 分配糖果,如果分配之后还有糖果则为没有投入25分的状态,否则为售罄状态
     */
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
