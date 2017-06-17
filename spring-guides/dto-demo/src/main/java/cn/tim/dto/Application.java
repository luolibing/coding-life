package cn.tim.dto;

import org.jdto.DTOBinder;
import org.jdto.spring.SpringDTOBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by LuoLiBing on 16/3/17.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DTOBinder dtoBinder() {
        return new SpringDTOBinder();
    }
}
