package cn.design.patterns.proxy.simple2;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by LuoLiBing on 16/6/23.
 */
public interface GumballMachineRemote extends Remote {

    int getCount() throws RemoteException;

    String getLocation() throws RemoteException;

    String getState() throws RemoteException;
}
