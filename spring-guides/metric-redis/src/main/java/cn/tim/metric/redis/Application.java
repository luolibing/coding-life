package cn.tim.metric.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.repository.redis.RedisMetricRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jmx.export.MBeanExporter;

/**
 * Created by LuoLiBing on 16/2/23.
 */
@SpringBootApplication
@EnableConfigurationProperties(ExportProperties.class)
public class Application {

    @Autowired
    private ExportProperties exportProperties;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RedisMetricRepository redisMetricRepository(RedisConnectionFactory connectionFactory) {
        return new RedisMetricRepository(
                connectionFactory,
                this.exportProperties.getPrefix(),
                this.exportProperties.getKey());
    }

    @Bean
    public JmxMetricWriter jmxMetricWriter(@Qualifier("mbeanExporter")MBeanExporter exporter) {
        return new JmxMetricWriter(exporter);
    }
}
