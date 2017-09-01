package com.tim.springboot.source.service;

import com.tim.springboot.source.config.DataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * User: luolibing
 * Date: 2017/9/1 16:24
 */
@Service
// 将DataConfig配置导入到spring管理，配合ConfigurationProperties使用。标准的使用配置的方式
@EnableConfigurationProperties(DataConfig.class)
public class DataService {

    @Autowired
    private DataConfig dataConfig;

    public void dataConfig() {
        System.out.println("dataConfig= " + dataConfig);
    }
}
