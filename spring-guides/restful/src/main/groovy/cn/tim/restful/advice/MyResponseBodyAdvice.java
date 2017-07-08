package cn.tim.restful.advice;

import cn.tim.restful.annoction.ResponseWrapper;
import cn.tim.restful.annoction.WrapperEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by luolibing on 2017/7/8.
 */
@RestControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class converterType) {
        return methodParameter.hasMethodAnnotation(ResponseWrapper.class)
                || (methodParameter.getDeclaringClass().getAnnotation(ResponseWrapper.class) != null);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        WrapperEntity result = new WrapperEntity();
        result.setReturnCode(200);
        result.setMessage("OK");
        result.setData(body);
        return result;
    }
}
