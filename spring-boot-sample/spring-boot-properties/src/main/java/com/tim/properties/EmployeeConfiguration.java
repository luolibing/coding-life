package com.tim.properties;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kroos.luo
 * @since 2020-02-16 22:34
 */
@Configuration
public class EmployeeConfiguration {

    @Bean
    @ConfigurationPropertiesBinding
    public EmployeeConverter employeeConverter() {
        return new EmployeeConverter();
    }
}
