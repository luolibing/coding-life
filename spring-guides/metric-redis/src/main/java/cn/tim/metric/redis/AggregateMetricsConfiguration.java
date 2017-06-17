package cn.tim.metric.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.aggregate.AggregateMetricReader;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.boot.actuate.metrics.repository.redis.RedisMetricRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Created by LuoLiBing on 16/2/23.
 */
@Configuration
public class AggregateMetricsConfiguration {

    @Autowired
    private ExportProperties exportProperties;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public PublicMetrics metricsAggregate() {
        return new MetricReaderPublicMetrics(aggregatesMetricReader());
    }

    private MetricReader globalMetricsForAggregation() {
        return new RedisMetricRepository(
                this.connectionFactory,
                this.exportProperties.getAggregatePrefix(),
                this.exportProperties.getKey());
    }

    private MetricReader aggregatesMetricReader() {
        return new AggregateMetricReader((globalMetricsForAggregation()));
    }
}
