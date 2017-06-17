package cn.design.patterns.proxy.simple1;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class GumballMachine {

    private String location;

    private int count;

    private String state;

    public GumballMachine(String location, int count) {
        this.location = location;
        this.count = count;
        state = "sold out";
    }

    public int getCount() {
        return count;
    }

    public String getLocation() {
        return location;
    }

    public String getState() {
        return state;
    }
}
