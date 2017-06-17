# it is derived from alpine:3.4
FROM tim/java8:latest

MAINTAINER luolibing <397911353@qq.com>

ENV ZK_VERSION 3.4.10
ENV ZK_DATA /var/opt/zookeeper/data
#ENV ZK_LOG /var/log/zookeeper

RUN yum install -y tar \
                   wget

RUN mkdir -p /opt/apache && \
    wget -q -O - http://apache.mirrors.pair.com/zookeeper/zookeeper-$ZK_VERSION/zookeeper-$ZK_VERSION.tar.gz | \
    tar -xzf - -C /opt/apache && \
    mv /opt/apache/zookeeper-$ZK_VERSION/ /opt/apache/zookeeper && \
    mv /opt/apache/zookeeper/conf/zoo_sample.cfg /opt/apache/zookeeper/conf/zoo.cfg && \
    sed -ri 's|(dataDir=).*$|\1'"$ZK_DATA"'|' /opt/apache/zookeeper/conf/zoo.cfg && \
    mkdir -p $ZK_DATA $ZK_LOG
#    useradd --system --shell /bin/bash --home /opt/apache/zookeeper zookeeper && \
#    chown -R zookeeper:zookeeper /opt/apache/zookeeper $ZK_DATA $ZK_LOG
#USER zookeeper

EXPOSE 2181 2888 3888

WORKDIR /opt/apache/zookeeper

VOLUME ["$ZK_DATA", "/opt/apache/zookeeper/conf"]

ENTRYPOINT ["/opt/apache/zookeeper/bin/zkServer.sh"]
CMD ["start-foreground"]