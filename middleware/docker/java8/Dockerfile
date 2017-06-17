FROM centos:centos6

MAINTAINER luolibing "397911353@qq.com"

# Set correct environment variables.
ENV	HOME /root
ENV	LANG en_US.UTF-8
ENV	LC_ALL en_US.UTF-8

RUN yum install -y curl; yum upgrade -y; yum update -y;  yum clean all

ENV JDK_VERSION 8u11
ENV JDK_BUILD_VERSION b12
RUN curl -LO "http://download.oracle.com/otn-pub/java/jdk/$JDK_VERSION-$JDK_BUILD_VERSION/jdk-$JDK_VERSION-linux-x64.rpm" \
    -H 'Cookie: oraclelicense=accept-securebackup-cookie' && \
    rpm -i jdk-$JDK_VERSION-linux-x64.rpm; \
    rm -f jdk-$JDK_VERSION-linux-x64.rpm; \
    yum clean all
ENV JAVA_HOME /usr/java/default

RUN yum remove curl;  yum clean all