package cn.design.patterns.proxy.simple2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by LuoLiBing on 16/6/23.
 *
 */
public class GumballMonitorTestDrive {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String location = "rmi://127.0.0.1/gumballMachine";
        GumballMachineRemote gumballMachineRemote = (GumballMachineRemote) Naming.lookup(location);
        GumballMonitor gumballMonitor = new GumballMonitor(gumballMachineRemote);
        gumballMonitor.report();
    }
}
