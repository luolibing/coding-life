package cn.tim.entity;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by LuoLiBing on 15/10/30.
 */
@Service
public class BookService {

    public void sayHello() {
        System.out.println("sayHello " + System.currentTimeMillis());
    }

    /**
     * spring.datasource.url=jdbc:mysql://127.0.0.1:3306/security?useUnicode=true&characterEncoding=utf8
     spring.datasource.username=root
     spring.datasource.password=root
     spring.datasource.driverClassName=com.mysql.jdbc.Driver

     spring.jpa.hibernate.ddl-auto=update

     * @return
     */
    @Bean
    public DataSource dataSource() {

        /*return DataSourceBuilder.create()
                .username("root")
                .password("root")
                .url("jdbc:mysql://127.0.0.1:3306/security?useUnicode=true&characterEncoding=utf8")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();*/
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/security?useUnicode=true&characterEncoding=utf8");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

}
