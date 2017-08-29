## Zookeeper配置文件
> 
tickTime=2000           （心跳时间）
dataDir=/var/lib/zookeeper
clientPort=2181         
initLimit=5             （连接到leader的超时时间,已心跳时间为单位，5次心跳之后）
syncLimit=2             （条目到leader的最远距离，2次心跳）
server.1=zoo1:2888:3888 (第一个端口2888用于对等体连接，第二个端口3888用户leader选举)
server.2=zoo2:2888:3888
server.3=zoo3:2888:3888

## 多点模式
> 在多点模式下，至少要求3个节点，这样才有可能在挂掉一个之后，另外两个进行投票选举leader，而且最好有奇数个节点，利于选举。

## 错误处理
> 当实现zookeeper时必须处理那些可恢复的异常情况，实际上，很多方法都采用有序的短暂节点，在成功创建节点后，正常返回节点名称之前出现了错误。
这个时候客户端需要重连session还是有效的，但是创建的节点并没有删除，这个时候客户端很难知道节点是被创建了还是未被创建。

## Zookeeper内部
### 原子传播
zookeeper的核心是保证所有原子消息系统保持同步

### 保证
1 可靠的发送，只要有一个server接收到，所有的server都应该能收到
2 顺序执行，保证总顺序，因果顺序

假定在server之间建议一个FIFO的通道，虽然有可能存在消息丢失或者顺序错乱的情况，但是因为tcp特性的保障。
1 顺序发送数据包，只有当message b之前的所有数据包都成功发送才会发送b，换言之，如果b之前有数据包发送失败，b将被丢弃.
2 通道关闭后，不会再接收到任何数据

### Zookeeper协议名词
1 数据包： 通过FIFO channel顺序发送的数据包
2 提议：协议单位。通过多个zookeeper服务器交换数据来商定提议，大多提案都包含消息，NEW_LEADER除外。
3 消息：顺序广播给所有zookeeper服务器的数据。一个消息被放置在一个天中，被提交前必须都得到同意。
zookeeper保证了message的总顺序和提案的总顺序. zookeeper通过zxid来保持顺序，所有的提案都被打上了这个zxid。
提案被发送给所有的server，当达到规定的确认消息的时候进行提交。如果这个提案包含有消息，则当提案提交的时候，这个消息将被发送。ack代表着持久化存储。
规定的ack数量至少一对server单重至少有一个公共的server。我们假定达到n/2+1，即超过一半

zxid实现由64位数字组成，高32位为epoch和低32位是counter，epoch代表的是leadership的变化，每一个新的leader都拥有自己的epoch.

zookeeper消息分为2个阶段
1 领导激活阶段，领导建议一个正确的系统状态，并且准备开始提出提案
2 激活消息阶段，领导接收消息，提出并且协调消息传递

leader激活（包含了Leader选举）
选举算法：LeaderElection和FastLeaderElection（AuthFastLeaderElection是Fast的一种变种），zookeeper并不关心选举算法的实现，只要满足以下要求即可
1 leader已经看到所有追随者的最高zxid（必须）
2 leader拥有一个法定quorum数量的追随者（数量大于quorum即可，如果故障在选举的时候或者之后发生,quorum丢失，发起下一次选举）

leader选举之后，将有一个单一的server被指定为leader，等待其他的追随者来连接leader。leader将发送跟随者错过的所有提案来同步，如果有跟随者有大量的提案未同步，则发送整个状态给这个追随者。
如果有一个追随者的提案U还没来得及让leader看到，因为提案的顺序到达，所以U提案的zxid会比leader的还会更高。这个时候leader会告诉这个追随者，丢弃提案U
新leader在新协议中使用第一个zxid设置为(e+1,0)，e是最大zxid中epoch。在同步之后，leader会提出一个NEW_LEADER协议，一旦这个协议被提交，新的leader被激活就可以开始接收和发起提案了。

活动消息传递

