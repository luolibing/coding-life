package cn.tim.autoconfiguration.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by luolibing on 2017/4/5.
 */
@Configuration
@ConfigurationProperties(prefix = "tim.counting")
@ConditionalOnProperty(prefix = "tim.counting", name = "enabled", havingValue = "true") //matchIfMissing = true
public class CountingProperties {

    private long initValue;

    private long maxValue;

    private long step;

    private boolean enable;

    public long getInitValue() {
        return initValue;
    }

    public void setInitValue(long initValue) {
        this.initValue = initValue;
    }

    public long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
    }

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        this.step = step;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
