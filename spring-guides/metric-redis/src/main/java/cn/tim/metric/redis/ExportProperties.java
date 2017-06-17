package cn.tim.metric.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Created by LuoLiBing on 16/2/23.
 */
@ConfigurationProperties("redis.metrics.export")
public class ExportProperties {

    private String prefix = "spring.metrics";
    private String key = "keys.spring.metrics";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAggregatePrefix() {
        final String[] tokens = StringUtils.delimitedListToStringArray(this.prefix, ".");
        if (tokens.length > 1) {
            if (StringUtils.hasText(tokens[1])) {
                // If the prefix has 2 or more non-trivial parts, use the first 1
                return tokens[0];
            }
        }
        return this.prefix;
    }
}
