package cn.design.patterns.proxy.simple1;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class GumballMachineTestDrive {

    public static void main(String[] args) {
        GumballMachine gumballMachine = new GumballMachine("五道口", 10);
        GumballMonitor gumballMonitor = new GumballMonitor(gumballMachine);
        gumballMonitor.report();
    }
}
