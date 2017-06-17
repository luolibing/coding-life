FROM centos:centos6

MAINTAINER luolibing <397911353@qq.com>

RUN yum update -y && \
    yum install -y epel-release && \
    yum clean all

WORKDIR "/root"

COPY ./ .

CMD ["bash"]