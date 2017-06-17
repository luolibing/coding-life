package cn.design.patterns.states.simple2;

/**
 * Created by LuoLiBing on 16/6/20.
 */
public class WinnerState implements State {

    private GumballMachine gumballMachine;

    public WinnerState(GumballMachine gumballMachine) {
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

    @Override
    public void dispense() {
        System.out.println("you're a 大赢家! you get two gumballs for your quarter");
        // 先释放出一个糖果
        gumballMachine.releaseBall();
        // 中奖了,但是没有多余的糖果了 ..
        if(gumballMachine.getCount() == 0) {
            gumballMachine.setState(gumballMachine.getSoldOutState());
        } else {
            // 释放中奖的糖果
            gumballMachine.releaseBall();
            if(gumballMachine.getCount() > 0) {
                gumballMachine.setState(gumballMachine.getNoQuarterState());
            } else {
                System.out.println("Oops, out of gumballs!");
                gumballMachine.setState(gumballMachine.getSoldOutState());
            }
        }
    }
}
