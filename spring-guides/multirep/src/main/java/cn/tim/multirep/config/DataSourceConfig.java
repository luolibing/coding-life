package cn.tim.multirep.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by LuoLiBing on 15/10/29.
 */
@Configuration
public class DataSourceConfig {

    /**
     * spring.datasource.url=jdbc:mysql://127.0.0.1:3306/crm?useUnicode=true&characterEncoding=utf8
       spring.datasource.username=root
       spring.datasource.password=root
       spring.datasource.driverClassName=com.mysql.jdbc.Driver
       spring.datasource.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
     * @return
     */
    @Bean(name = "firstDB")
    @Qualifier("firstDB")
    @Primary
    @ConfigurationProperties(prefix = "tim.first")
    public DataSource localDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * spring.datasource.url=jdbc:mysql://192.168.0.100:3306/crm?useUnicode=true&characterEncoding=utf8
     spring.datasource.username=root
     spring.datasource.password=root
     spring.datasource.driverClassName=com.mysql.jdbc.Driver
     spring.datasource.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
     * @return
     */
    @Bean(name = "secondDB")
    @Qualifier("secondDB")
    //@Primary
    @ConfigurationProperties(prefix = "tim.second")
    public DataSource productDataSource() {
        return DataSourceBuilder.create().build();
    }
}
