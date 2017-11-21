package com.company.sys.config;

import com.company.sys.SysApplication;
import com.company.sys.SysApplicationBuilder;
import com.company.sys.sys.sys.film.FilmManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: luolibing
 * Date: 2017/11/21 17:09
 */
@Configuration
@EnableConfigurationProperties(SpeedmentDbProperties.class)
public class SpeedmentConfiguration {

    @Autowired
    private SpeedmentDbProperties speedmentDbProperties;

    @Bean
    public SysApplication sysApplication() {
        return new SysApplicationBuilder()
                .withUsername(speedmentDbProperties.getUsername())
                .withPassword(speedmentDbProperties.getPassword())
                .withSchema(speedmentDbProperties.getSchema())
                .withIpAddress("127.0.0.1")
                .build();
    }

    @Bean
    public FilmManager filmManager(SysApplication sysApplication) {
        return sysApplication.getOrThrow(FilmManager.class);
    }

}
