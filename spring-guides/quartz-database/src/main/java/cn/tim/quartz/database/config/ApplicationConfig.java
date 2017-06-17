package cn.tim.quartz.database.config;

import org.jdto.DTOBinder;
import org.jdto.spring.SpringDTOBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LuoLiBing on 16/6/24.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public DTOBinder dtoBinder() {
        return new SpringDTOBinder();
    }
}
