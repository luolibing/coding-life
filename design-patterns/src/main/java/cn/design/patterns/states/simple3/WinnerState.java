package cn.design.patterns.states.simple3;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class WinnerState extends DefaultState {

    private GumballMachine gumballMachine;

    public WinnerState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
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
