
FROM centos:centos6
MAINTAINER luolibing <397911353@qq.com>

RUN yum -y update; yum clean all
RUN yum -y install openssh-server passwd; yum clean all
ADD ./start.sh /start.sh
RUN mkdir /var/run/sshd

RUN ssh-keygen -t rsa -f /etc/ssh/ssh_host_rsa_key -N ''

RUN chmod 755 /start.sh
EXPOSE 22
RUN ./start.sh

## 开启Container自启动的命令，ENTRYPOINT, CMD是指定映射的指令Run的时候带上
# CMD ["/usr/sbin/sshd", "-D"]

CMD ["/usr/sbin/sshd", "-D"]


# -d 后台启动 -P 端口
# 启动容器 docker run -d -P --name test_sshd tim_sshd
# 启动容器 docker run -d -p 80:80 test_sshd service nginx start

# docker port test_sshd 22 绑定22端口为随机

# ssh -p 32773 root@127.0.0.1
