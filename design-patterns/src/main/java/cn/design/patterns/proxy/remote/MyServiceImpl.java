package cn.design.patterns.proxy.remote;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by LuoLiBing on 16/6/22.
 * 使用UnicastRemoteObject来帮助实现底层的调用
 * 传递的参数与返回值都必须是原语类型或者是实现了序列化接口的类
 *
 * 制作远程服务的步骤
 * 1 制作远程接口,stub和实际的服务类都必须实现这个接口
 * 2 制作远程实现,就是最终的目标服务类
 * 3 利用rmic产生stub桩和skeleton骨架(新版本不需要)
 * 4 启动RMI registry
 * 5 注册远程服务Naming.rebind("RemoteHello", myService);
 *
 */
public class MyServiceImpl extends UnicastRemoteObject implements MyService {

    @Override
    public String sayHello(String name) throws RemoteException {
        System.out.println("hello " + name);
        return "hello " + name;
    }

    protected MyServiceImpl() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        try {
            System.out.println("register: ");
            MyService myService = new MyServiceImpl();
            Naming.rebind("RemoteHello", myService);
            System.out.println("register to RemoteHello myService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
