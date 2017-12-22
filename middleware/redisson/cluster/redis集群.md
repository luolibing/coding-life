## redis.conf配置
将daemonize设置为yes，后台启动
开启集群模式，cluster-enabled yes
同一台机器的多个端口，修改port
修改进程文件pidfile /var/run/redis_6377.pid

## 集群创建
cluster meet ip port 绑定集群

## 绑定slots
cluster addslots 0 1
一般借助于脚本进行添加

## cluster上线
当所有的slots都绑定了node时，才会进入到上线状态。

## 集群set get
如果是单机模式，set a b的时候会报错
(error) MOVED 3300 127.0.0.1:6375
redis-cli -c -h 127.0.0.1 6377 集群模式
Redirected to slot [3300] located at 127.0.0.1:6375
OK

重定向的实现
先对key进行计算所属的slots
cluster keyslot key1 
(integer) 15495

根据slots查看所属的node，是否是当前的node客户端，如果是则直接执行，否则进行redirected重定向到指定node，并且执行



