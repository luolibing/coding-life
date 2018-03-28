package cn.tim.transaction;

import cn.tim.transaction.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by LuoLiBing on 15/10/9.
 */
@SpringBootApplication
// 开启cglib的事务管理器, 在spring boot当中，自动注入proxyTargetClass=false的配置，如果需要修改，可以配置application.yml  spring.aop.proxy-target-class=true
@EnableTransactionManagement(proxyTargetClass = true)
public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        process(ctx);
    }


    @Bean
    BookingService bookingService() {
        return new BookingService();
    }


    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private static void process(ApplicationContext ctx) {
        BookingService bookingService = ctx.getBean(BookingService.class);
        try {
            String[] persons = {"luolibing", "liuxiaoling", "luominghao", "luoaiyun", null};
            bookingService.batchInsert(persons);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> books = bookingService.queryAllBookings();
        System.out.println(books);
    }

}
