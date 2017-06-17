package cn.tim;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by LuoLiBing on 15/7/15.
 */
@Component
public class StoreIntegration {

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "cn.tim.StoreIntegration", value = "500")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "101"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            }, fallbackMethod = "defaultStores")
    public Object getStores(Map<String, Object> parameters) {
        //do stuff that might fail
        int i=1/0;
        return "success";
    }

    public Object defaultStores(Map<String, Object> parameters) {
        /* something useful */;
        return "something useful";
    }

}
