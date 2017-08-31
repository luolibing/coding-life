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


#安装maven
RUN mkdir /var/tmp/maven
RUN wget -P /var/tmp/maven  http://apache.fayea.com/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
RUN tar xzf /var/tmp/maven/apache-maven-3.3.9-bin.tar.gz -C /var/tmp/maven
RUN rm -rf /var/tmp/maven/apache-maven-3.3.9-bin.tar.gz
ADD ./apache-maven-3.3.9-bin.tar.gz /var/tmp/maven

#设置环境变量
ENV JAVA_HOME /var/tmp/jdk/jdk1.8.0_111
ENV CATALINA_HOME /var/tmp/tomcat/apache-tomcat-8.5.11
ENV M2_HOME /var/tmp/maven/apache-maven-3.3.9
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin:$M2_HOME/bin
ENV CLASSPATH .:$JAVA_HOME/lib:$JAVA_HOME/jre/lib\

