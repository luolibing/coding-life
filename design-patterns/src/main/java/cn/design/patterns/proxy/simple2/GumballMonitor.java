package cn.design.patterns.proxy.simple2;

import java.rmi.RemoteException;

/**
 * Created by LuoLiBing on 16/6/23.
 * gumballMonitor根本不需要例会调用的gumballMachine是本地对象还是远程对象,唯一要操心的就是要处理远程异常
 * 调用过程
 * 1 客户执行监视器,先取得远程糖果机的代理,然后调用每个代理的getState以及其他方法
 * 2 代理商的getState方法被调用,将参数进行序列化,转发到远程服务.skeleton接收到请求,然后转发给糖果机
 * 3 糖果机将状态返回给skeleton,skeleton将状态进行序列化,通过网络传回给代理,代理将其反序列化,把它当做一个对象返回给监视器
 *
 * 代理模式定义
 * 为另一个对象提供一个替身或占位符以控制对这个对象的访问.使用代理模式创建代表对象,让代表对象控制某对象的访问
 * 1 被代理的对象可以是远程对象
 * 2 创建开销大的对象
 * 3 需要安全控制的对象
 */
public class GumballMonitor {

    private GumballMachineRemote gumballMachine;

    public GumballMonitor(GumballMachineRemote gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    public void report() {
        try {
            System.out.println("Gumball Machine: " + gumballMachine.getLocation());
            System.out.println("Current inventory: " + gumballMachine.getCount() + " gumballs");
            System.out.println("Current state: " + gumballMachine.getState());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}