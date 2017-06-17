package cn.design.patterns.states.simple3;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class GumballMachine {

    State soldOutState;

    State noQuarterState;

    State hasQuarterState;

    State soldState;

    State winnerState;

    // 当前状态,默认为售罄状态,糖果机就在这几个状态之间进行切换,而引起状态切换需要进行一些动作
    private State state = soldOutState;

    private int count = 0;

    public GumballMachine(int count) {
        this.count = count;
        this.soldOutState = new SoldOutState(this);
        this.noQuarterState = new NoQuarterState(this);
        this.hasQuarterState = new HasQuarterState(this);
        this.soldState = new SoldState(this);
        this.winnerState = new WinnerState(this);
        if(count > 0) {
            state = noQuarterState;
        }
    }

    void setState(State state) {
        this.state = state;
    }

    public void insertQuarter() {
        state.insertQuarter();
    }

    public void ejectQuarter() {
        state.ejectQuarter();
    }

    public void turnCrank() {
        state.turnCrank();
        state.dispense();
    }

    void releaseBall() {
        System.out.println("A gumball comes rolling out the slot...");
        if(count != 0) {
            count = count - 1;
        }
    }

    void refill(int count) {
        if(count <= 0) {
            throw new GumballException();
        }
        this.count = this.count + count;
        this.state = noQuarterState;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getWinnerState() {
        return winnerState;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "GumballMachine{" +
                "state=" + state +
                ", count=" + count +
                '}';
    }
}
