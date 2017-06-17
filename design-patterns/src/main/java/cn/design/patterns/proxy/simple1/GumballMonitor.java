package cn.design.patterns.proxy.simple1;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class GumballMonitor {

    private GumballMachine gumballMachine;

    public GumballMonitor(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void report() {
        System.out.println("Gumball Machine: " + gumballMachine.getLocation());
        System.out.println("Current inventory: " + gumballMachine.getCount() + " gumballs");
        System.out.println("Current state: " + gumballMachine.getState());
    }
}
