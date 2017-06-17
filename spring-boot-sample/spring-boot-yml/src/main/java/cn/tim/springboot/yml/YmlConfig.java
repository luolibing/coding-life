package cn.tim.springboot.yml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 如果是yml文件，在引入001这种字符串时，会自动将其作为数字字符串，忽略掉前面的00
 * 解决方法：
 * 1 换成properties文件，则不存在这样的问题
 * 2 加上双引号，也不存在这样的问题。
 * User: luolibing
 * Date: 2017/5/9 9:56
 */
@Configuration
@ConfigurationProperties(prefix = "yml")
public class YmlConfig {

    private String appCode;

    private Long businessId;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @Override
    public String toString() {
        return "YmlConfig{" +
                "appCode='" + appCode + '\'' +
                ", businessId=" + businessId +
                '}';
    }
}
