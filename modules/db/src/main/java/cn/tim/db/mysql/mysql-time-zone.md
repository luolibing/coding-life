### 如何理解时区的问题
#### 在一个服务当中我们需要注意的几个时区的问题
> 
1、客户端用户所在时区，也就是本地时区  
2、应用服务器的时区，也就是机器的时区  
3、mysql服务器的时区，可能和服务器时区一样，也可能不一样  
4、mysql连接connector的时区  
5、程序的时区，可以根据需要自动去设置时区  

数据库时间有2种格式，datetime和timestamp
datetime就是yyyy-MM-dd HH:mm:ss这种，是固定的，而时间戳会随着数据库时区的变化或者连接的变化而变化