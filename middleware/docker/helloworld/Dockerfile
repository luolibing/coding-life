FROM tim/centos

MAINTAINER luolibing 397911353@qq.com

#ENTRYPOINT ["echo", "hello world, welcome to docker"]

# CMD三种用法
# 1 CMD ["executable", "param1", "param2] 作为一个可执行指令
# CMD ["/bin/echo", "hello word"]

# 2 为ENTRYPOINT提供默认参数
CMD ["param1", "param2"]

# 3

# ENTRYPOINT的几种用法
# 1 进入的时候调用/bin/echo，只等待传递参数即可
# ENTRYPOINT ["/bin/echo"] # 这个地方参数直接使用了CMD中的

# 2 手动指定参数例如/bin/cat
ENTRYPOINT ["/bin/cat"]


# docker run -d -p 4000:80 hello, -d作为后台程序执行，-p指定端口