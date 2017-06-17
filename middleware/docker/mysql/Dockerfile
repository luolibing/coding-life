# 自己的centos
FROM centos:centos6
MAINTAINER luolibing "397911353@qq.com"


RUN yum -y update; yum clean all
RUN yum -y install epel-release; yum clean all
RUN yum -y install mysql-server mysql pwgen supervisor bash-completion psmisc net-tools; yum clean all

ADD ./start.sh /start.sh
ADD ./config_mysql.sh /config_mysql.sh
ADD ./supervisord.conf /etc/supervisord.conf

# RUN echo %sudo	ALL=NOPASSWD: ALL >> /etc/sudoers

RUN chmod 755 /start.sh
RUN chmod 755 /config_mysql.sh
RUN /config_mysql.sh

EXPOSE 3306

## CMD在启动的时候使用指定的命令映射到docker上的指令
## docker run -i -p 3307:3306 tim/mysql /bin/bash /start.sh
CMD ["/bin/bash", "/start.sh"]

## 用service mysqld start
# CMD ["service mysqld start"]