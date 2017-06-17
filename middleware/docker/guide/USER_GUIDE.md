### Docker网络
> docker网络设置，docker提供了两种网络驱动，一种是桥接网络bridge，覆盖网络overlay. 桥接网络仅限于运行docker引擎的单个主机,覆盖网络可以在多个主机之间使用，并且拥有更高的主体
> docker network ls，除非指定对应的网络，否则docker默认都是使用桥接网络bridge
> docker network inspect bridge查看所有桥接网络容器的情况，包括分配的ip地址
> docker network disconnect bridge networktest 可以将容器从桥接网络中移除出去.
> docker network create -d bridge my_bridge 创建自己的桥接网络 -d 是告诉docker创建新的网络时使用桥接网络.
> docker run -d --net=my_bridge --name my_fridenlyhello friendlyhello   启动容器使用指定的网络
> docker inspect my_fridenlyhello 使用docker inspect来检查容器的情况。 可以使用docker inspect --format='{{json .NetworkSettings.Networks}}' my_fridenlyhello 查看docker容器连接的网络情况.
> 使用不同的桥接网络启动的容器，因为处于不同的网段，可能不能互相连通，需要手动指定网络连接，使用指令 docker network connect my_bridge 23576939dd3a连接到指定网络, 这样就可以让一个docker连接多个网络

### Docker数据管理
> docker容器之间如何管理数据，两种管理方式： 1 数据卷  2 数据卷管理

### Docker run指令中的详细步骤
> docker run -t -i ubuntu /bin/bash

When you run this command, the following happens (assuming you are using the default registry configuration):
1 如果本地没有该image，则从你配置的registry上拉取（缺省配置是到docker hub上拉取）
If you do not have the ubuntu image locally, Docker pulls it from your configured registry, as though you had run docker pull ubuntu manually.

2 创建一个新的容器
Docker creates a new container, as though you had run a docker create command manually.

3 docker分配一块可读写硬盘空间，作为最终层
Docker allocates a read-write filesystem to the container, as its final layer. This allows a running container to create or modify files and directories in its local filesystem.

4 创建网络接口，连接到指定的网络（缺省网络连接到bridge）
Docker creates a network interface to connect the container to the default network, since you did not specify any networking options. This includes assigning an IP address to the container. By default, containers can connect to external networks using the host machine’s network connection.

5 启动容器，并且启动指令/bin/bash命令行
Docker starts the container and executes /bin/bash. Because the container is run interactively and attached to your terminal (due to the -i and -t) flags, you can provide input using your keyboard and output is logged to your terminal.

6 到这一步，容器就算是启动完成了
When you type exit to terminate the /bin/bash command, the container stops but is not removed. You can start it again or remove it.

## Docker namespace名称空间
> docker使用namespace来为container提供隔离的工作区，容器运行时创建一组名称空间。名称空间提供了一个隔离层，容器的各个方面都在一个单独的namespace中运行，而且仅限于该名称空间范围内访问。