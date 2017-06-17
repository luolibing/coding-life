package cn.tim.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Created by LuoLiBing on 15/11/25.
 * redis特点
 * 1 几乎所有数据都在内存
 * 2 所有数据都可以持久化
 * 3 支持自增操作
 * 4 支持事务
 * 5 支持订阅与发布
 * 6 单线程使用异步IO
 */
@ImportResource("classpath:/spring.xml")
@ComponentScan(value = {"cn.tim"})
public class Application {

    /**
     * 主从配置
     * spring.redis.sentinel.master
     * spring.redis.sentinel.nodes host:port list
     * @return
     */
    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master("mymaster")
                .sentinel("127.0.0.1", 26379).sentinel("127.0.0.1", 26380);
        return new JedisConnectionFactory(sentinelConfig);
    }
}
