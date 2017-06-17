FROM centos:centos6

MAINTAINER luolibing, 397911353@qq.com

# yum 更新，以及安装epel-release，来及时更新yum安装源
RUN yum update -y && \
    yum install -y epel-release && \
    yum clean all

CMD ["bash"]