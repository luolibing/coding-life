package cn.design.patterns.proxy.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by LuoLiBing on 16/6/22.
 * 必须实现远程接口Remote,这个接口不做任何事情,只是一个标记
 */
public interface MyService extends Remote {

    String sayHello(String name) throws RemoteException;
}
