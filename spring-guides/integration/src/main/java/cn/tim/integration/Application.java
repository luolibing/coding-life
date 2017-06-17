package cn.tim.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by LuoLiBing on 16/1/21.
 * 系统集成:通过文件进行传输通信
 * 一  实现大多数企业整合方案
 * Endpoint: 端点
 * Channel:  通道(点对点,订阅与发布)
 * Aggregator:聚合
 * Filter:   过滤器
 * Transformer:转换器
 * Control Bus:控制总线
 *
 * 二  整合其他扩展系统
 * Rest/HTTP
 * FTP/SFTP
 * Twitter
 * WebService
 * JMS
 * RabbitMQ
 * EMAIL
 */
@SpringBootApplication
@ImportResource("integration-context.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
