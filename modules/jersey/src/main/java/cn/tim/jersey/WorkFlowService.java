package cn.tim.jersey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 17/3/15.
 */
@Component
public class WorkFlowService {

    @Value("${message}")
    private String message;

    public String message() {
        return "Hello " +  message;
    }
}
