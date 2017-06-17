package cn.design.patterns.states.simple2;

/**
 * Created by LuoLiBing on 16/6/20.
 * 没有投入25分
 */
public class NoQuarterState implements State {

    private GumballMachine gumballMachine;

    public NoQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    /**
     * 没有投入25的时候投入25分,状态变为有25分的状态
     */
    @Override
    public void insertQuarter() {
        System.out.println("you inserted a quarter");
        gumballMachine.setState(gumballMachine.getHasQuarterState());
    }

    /**
     * 没有25分不能退回25分
     */
    @Override
    public void ejectQuarter() {
        System.out.println("you haven't inserted a quarter");
    }

    /**
     * 没有25分摇动转轴没用
     */
    @Override
    public void turnCrank() {
        System.out.println("you turned, but there's no quarter");
    }

    /**
     * 没有25分当然不分配糖果
     */
    @Override
    public void dispense() {
        System.out.println("you need to pay first");
    }
}
