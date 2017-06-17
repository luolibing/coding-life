package cn.design.patterns.states.simple2;

import java.util.Random;

/**
 * Created by LuoLiBing on 16/6/20.
 */
class HasQuarterState implements State {

    private GumballMachine gumballMachine;

    Random randomWinner;

    public HasQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
        randomWinner = new Random(System.currentTimeMillis());
    }

    /**
     * 投入25分
     */
    @Override
    public void insertQuarter() {
        System.out.println("you can't insert another quarter");
    }

    /**
     * 投入了25分,可以退钱,退完之后变成没有25分
     */
    @Override
    public void ejectQuarter() {
        System.out.println("quarter returned");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }

    /**
     * 投入了25分,可以转动转轴,转动之后变成售出状态
     */
    @Override
    public void turnCrank() {
        System.out.println("you turned");
        // 有1/10的概率中奖,但是前提是还有多余1的糖果,这样才可能发出2个糖果
        int winner = randomWinner.nextInt(2);
        if((winner == 0) && (gumballMachine.getCount() > 1)) {
            gumballMachine.setState(gumballMachine.getWinnerState());
        } else {
            gumballMachine.setState(gumballMachine.getSoldState());
        }
    }

    /**
     * 不是售出状态,不能出糖果
     */
    @Override
    public void dispense() {
        System.out.println("no gumball dispensed");
    }
}
