package com.tim.dubbo.sample.plugin;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.LoadBalance;

import java.util.List;

/**
 * 实现LoadBalance接口
 * 然后在META-INF/dubbo/com.alibaba.dubbo.rpc.cluster.LoadBalance配置
 * xxx=com.xxx.XxxLoadBalance
 * xxx是啥?  xxx只是一个约定，在setLoadBalance设置对应的Key能对应起来就可以
 * Created by luolibing on 2018/9/4.
 *
 */
public class MyLoadBalance implements LoadBalance {
    @Override
    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        System.out.println("load balance");
        return invokers.get(0);
    }
}
