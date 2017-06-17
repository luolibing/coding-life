package cn.design.patterns.proxy.remote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by LuoLiBing on 16/6/23.
 * 客户端
 * 通过Naming.lookup("rmi://127.0.0.1/RemoteHello")查找对应的服务,并且调用
 *
 * 客户端工作方式:
 * 1 客户到RMI register中寻找对应的服务Naming.lookup("rmi://127.0.0.1/RemoteHello")
 * 2 RMI register返回Stub对象,客户端必须有stub类,否则stub就无法被反序列化
 * 3 客户端调用stub的方法,就想调用MyServiceImpl服务方法一样
 * 4 服务器端需要stub和skeleton,绑定的时候需要stub,客户端也需要stub,因为反序列化的时候需要stub
 * 5 stub就是服务堆的代理
 */
public class RemoteClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // 返回的是Stub对象,stub对象会被序列化传递过来然后进行反序列化
        MyService myService = (MyService) Naming.lookup("rmi://127.0.0.1/RemoteHello");
        System.out.println(myService.sayHello("luolibing"));
    }
}
