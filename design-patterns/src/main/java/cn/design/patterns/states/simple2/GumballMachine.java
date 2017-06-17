package cn.design.patterns.states.simple2;

/**
 * Created by LuoLiBing on 16/6/20.
 * 糖果机2,相比第一个版本我们做到了
 * 1 将每个状态的行为局部化到他自己的类中
 * 2 将容易产生问题的if语句删除,变成多态方法,方便日后维护
 * 3 将每一个状态对修改关闭,让糖果机对扩展开放,因为可以加入新的状态类
 * 4 创建一个新的代码基和类结构
 *
 * 定义状态模式
 * 允许对象砸内部状态改变时改变它的行为,对象看起来好像修改了它的类
 *
 *
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
