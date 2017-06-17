package cn.design.patterns.states.simple1;

/**
 * Created by LuoLiBing on 16/6/20.
 */
public class GumballMachine {

    final static int SOLD_OUT = 0;

    final static int NO_QUARTER = 1;

    final static int HAS_QUAERER = 2;

    final static int SOLD = 3;

    private int state = SOLD_OUT;

    private int count;

    public GumballMachine(int count) {
        this.count = count;
        if(this.count > 0) {
            state = NO_QUARTER;
        }
    }

    /**
     * 投币
     */
    public void insertQuarter() {
        if(state == HAS_QUAERER) {
            System.out.println("You can't insert another quarter");
        } else if(state == SOLD_OUT) {
            System.out.println("you can't insert a quarter,the machine is sold out");
        } else if(state == SOLD) {
            System.out.println("please wait, we're already giving you a gumball;");
        } else if(state == NO_QUARTER) {
            state = HAS_QUAERER;
            System.out.println("you inserted a quarter");
        }
    }

    /**
     * 退钱
     */
    public void ejectQuarter() {
        if(state == HAS_QUAERER) {
            System.out.println("Quarter returned");
            state = NO_QUARTER;
        } else if(state == SOLD_OUT) {
            System.out.println("you can't insert a quarter,the machine is sold out");
        } else if(state == SOLD) {
            System.out.println("please wait, we're already giving you a gumball;");
        } else if(state == NO_QUARTER) {
            System.out.println("you can't eject,you haven't inserted a quarter yet");
        }
    }

    /**
     * 摇动滚轴
     */
    public void turnCrank() {
        if(state == HAS_QUAERER) {
            System.out.println("you turned...");
            state = SOLD;
            dispense();
        } else if(state == SOLD_OUT) {
            System.out.println("you can't insert a quarter,the machine is sold out");
        } else if(state == SOLD) {
            System.out.println("turning twice doesn't get you another gumball");
        } else if(state == NO_QUARTER) {
            System.out.println("you can't eject,you haven't inserted a quarter yet");
        }
    }

    /**
     * 出货
     */
    public void dispense() {
        if(state == SOLD) {
            System.out.println("A gumball comes rolling out the slot");
            count--;
            if(count == 0) {
                System.out.println("oops, out of gumballs!!");
                state = SOLD_OUT;
            } else {
                state = NO_QUARTER;
            }
        } else if(state == NO_QUARTER) {
            System.out.println("you need to pay first");
        } else if(state == SOLD_OUT) {
            System.out.println("No gumball dispensed");
        } else if(state == HAS_QUAERER) {
            System.out.println("NO qumball dispensed");
        }
    }

    @Override
    public String toString() {
        return "GumballMachine{" +
                "state=" + state +
                ", count=" + count +
                '}';
    }
}
