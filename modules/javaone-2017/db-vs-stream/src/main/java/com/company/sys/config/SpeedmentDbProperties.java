package com.company.sys.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * User: luolibing
 * Date: 2017/11/21 17:10
 */
@Data
@ConfigurationProperties(prefix = "speedment.mysql")
public class SpeedmentDbProperties {
    private String username;

    private String password;

    private String schema;
}
