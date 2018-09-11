## Dubbo
### 一 常用配置
1 启动时检查  
check = false

2 集群容错  
失败容错策略，有重新调用，有并行调用等等策略，根据不同的场景使用不同的策略

3 负载均衡  
支持负载均衡配置，策略有按权重随机，轮询调用，最慢的机器收到最少的请求，一致性hash等，也可以自行实现LoadBalance接口进行扩展

4 线程模型  
获取请求IO模型和处理请求线程池

5 点对点直连  
对应测试或者其他场景需要绕过注册中心直连

6 只订阅，不注册  
测试场景

7 只注册

8 静态服务  
将服务的上下线改成手动

9 多协议交互  
多种传输交互协议

10 多注册中心  
多注册中心还是相当的灵活，支持同一服务注册不同注册中心，或者不同服务注册到不同的注册中心等等。

11 服务分组  
一个接口多种实现，用group分组来进行区分

12 多版本  
当出现多个版本不兼容的时候，可以使用版本号来区分调用，不同版本之间不进行相互调用。 可以逐步升级的方式进行平滑的升级

13 结果合并merger 
将结果进行合并，可以实现dubbo的Merger接口进行扩展

14 自定义验证  
JSR303 

15 结果的缓存  
原生支持lRU,threadLocal,jcache,以及实现了jsr107缓存协议的缓存实现。 TODO

16 泛化调用  
所有返回参数都用map来表示，在集成某些测试框架时更通用，例如mock接口  TODO

17 回声测试
开启合并同时开启回声测试，会抛出空指针异常

18 上下文信息  
RpcContext使用一个ThreadLocal来保存调用者相关的一些信息，例如IP地址，端口方法等等，调用会被解析到一个  
dubbo://192.168.1.101:20880/com.tim.dubbo.sample.WelcomeService?anyhost=true&application=first-dubbo-consumer&cache=lru&check=false&dubbo=2.6.2&generic=false&group=group2&interface=com.tim.dubbo.sample.WelcomeService&loadbalance=myLoadBalance&merger=myMerger&methods=welcome,addPerson,updatePerson&pid=1364&register.ip=192.168.1.101&remote.timestamp=1536588965368&retries=2&side=consumer&timeout=2000&timestamp=1536588991610&validation=true  
类似于一个http协议一样。 RpcContext.getContext().get()；这个方法给人第一感觉以为是返回上下文所有键值对，但是却是空的，查看文档得知now we don't use the 'values' map to hold these objects  
每次调用都会reinit设置值，RpcContext.getContext().setAttachment("index", "1"); 隐式传参

18 异步调用
当调用接口过程比较长时，可以开启异步调用，先调用，然后继续执行结果，当调用完成时，会通知RpcContext同时set到future上  
！！！但是如果连着调用多个会怎么样

19 本地调用  
injvm，当provider和consumer在同一个Jvm内时，默认优先调用injvm，如果想执行远程调用需要配置<dubbo:reference ... scope="remote" />

20 