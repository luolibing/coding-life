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

import javax.sql.DataSource;

/**
 * Created by LuoLiBing on 15/10/9.
 */
@SpringBootApplication
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
        String[] persons = {"luolibing", "liuxiaoling", "luominghao", "luoaiyun", null};
        BookingService bookingService = ctx.getBean(BookingService.class);
        bookingService.batchInsert(persons);
    }

}
