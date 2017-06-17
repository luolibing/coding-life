package cn.design.patterns.states.simple3;

import org.springframework.util.StringUtils;

/**
 * Created by LuoLiBing on 16/6/22.
 */
public class GumballException extends RuntimeException {

    private String message = "401,un support operation";

    public GumballException() {
        super();
    }

    public GumballException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        if(StringUtils.isEmpty(super.getMessage())) {
            return message;
        }
        return super.getMessage();
    }
}
