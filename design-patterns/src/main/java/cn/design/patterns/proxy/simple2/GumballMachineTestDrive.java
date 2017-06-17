package cn.design.patterns.proxy.simple2;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by LuoLiBing on 16/6/23.
 */
public class GumballMachineTestDrive {

    public static void main(String[] args) throws RemoteException {
        GumballMachine gumballMachine = new GumballMachine("五道口", 10);
        try {
            Naming.rebind("//127.0.0.1/gumballMachine", gumballMachine);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
