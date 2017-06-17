package cn.tim.springboot.returnvalue.handler.advice;

import cn.tim.springboot.returnvalue.handler.annotation.JsonpResponseBody;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * jsonp支持
 * User: luolibing
 * Date: 2017/5/4 11:29
 * Email: 397911353@qq.com
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RestControllerAdvice
public class JsonpRestControllerAdvice extends AbstractJsonpResponseBodyAdvice {

    protected JsonpRestControllerAdvice() {
        // 指定jsonp回调函数的key
        super("callback");
    }

    /**
     * 支持在方法或者类上加@JsonpResponseBody注解
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(JsonpResponseBody.class)
                || (returnType.getDeclaringClass().getAnnotation(JsonpResponseBody.class) != null);
    }
}
