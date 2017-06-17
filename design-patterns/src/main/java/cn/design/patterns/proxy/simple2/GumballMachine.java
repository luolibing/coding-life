package cn.design.patterns.proxy.simple2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by LuoLiBing on 16/6/23.
 */
public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {

    private String location;

    private int count;

    private String state;

    protected GumballMachine() throws RemoteException {
        super();
    }

    public GumballMachine(String location, int count) throws RemoteException {
        super();
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
