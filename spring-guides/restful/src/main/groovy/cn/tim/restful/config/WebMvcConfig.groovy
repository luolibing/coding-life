package cn.tim.restful.config

import cn.tim.restful.advice.ResuleControllerAdvice
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
/**
 * Created by LuoLiBing on 16/3/16.
 */
@Configuration
class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResuleControllerAdvice())
    }
}
