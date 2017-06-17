package cn.tim.metric.redis;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/2/23.
 */
@Controller
@Description("A controller for handle request")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, String> test() {
        return Collections.singletonMap("message", this.testService.getHelloMessage());
    }

    protected static class Message {

        @NotBlank(message = "message val can't be empty")
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
