package cn.tim;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by LuoLiBing on 16/6/23.
 */
public interface MyService extends Remote {

    String sayHello(String name) throws RemoteException;
}

