package cn.design.patterns.states.simple3;

import java.util.Random;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class HasQuarterState extends DefaultState {

    private GumballMachine gumballMachine;

    Random randomWinner;

    public HasQuarterState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
        randomWinner = new Random(System.currentTimeMillis());
    }

    @Override
    public void ejectQuarter() {
        System.out.println("quarter returned");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }

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
}
