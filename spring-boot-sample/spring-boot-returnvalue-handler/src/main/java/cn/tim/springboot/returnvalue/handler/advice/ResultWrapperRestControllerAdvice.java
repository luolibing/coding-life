package cn.tim.springboot.returnvalue.handler.advice;

import cn.tim.springboot.returnvalue.handler.annotation.ResultWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回结果wrapper封装
 * User: luolibing
 * Date: 2017/5/4 11:33
 * Email: 397911353@qq.com
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ResultWrapperRestControllerAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.hasMethodAnnotation(ResultWrapper.class)
                || (methodParameter.getDeclaringClass().getAnnotation(ResultWrapper.class) != null);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        return ResultModel.ok(o);
    }
}
