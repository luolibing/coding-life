package cn.tim.restful.controller

import cn.tim.restful.entity.Greeting
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.util.logging.Slf4j
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*

import java.util.concurrent.atomic.AtomicLong
/**
 * Created by LuoLiBing on 15/9/29.
 */
@RestController
@Slf4j
class GreetingController {


    private static final String template = "hello, %s!"
    private final static AtomicLong counter = new AtomicLong()

    /**
     * 将spring mvc中的参数纳入spring管理，可以定义各种Editor
     * @param binder
     */
    @InitBinder
    void initDataBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true) {
            @Override
            void setAsText(String text) {
                super.setAsText(text + "luolibing")
            }
        })
    }


    @RequestMapping("/greeting")
    @JsonIgnoreProperties(ignoreUnknown = true, value = "content")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "world") String name) {
        return new Greeting(12,
                String.format(template, name));
    }


    @RequestMapping(value = "/check/{email:.+}")
    public Object check(@PathVariable String email) {
        log.info email
        if(email.contains("qq.com")){
            return ["status": 'Y']
        }
        return ["status": 'N']
    }

    @RequestMapping(value = "/check1")
    public Object check1(String email) {
        log.info email
        if(email.contains("qq.com")){
            return ["status": 'Y']
        }
        return ["status": 'N']
    }

    @RequestMapping(value = "/genericException", method = RequestMethod.GET)
    public Greeting genericException(@RequestParam(value = "name", defaultValue = "world") String name) {
        def greeting = new Greeting(counter.incrementAndGet(),
                String.format(template, name))
        greeting.genericException()
        return greeting;
    }


    @RequestMapping(value = "/runtimeException", method = RequestMethod.GET)
    public Greeting runtimeException(@RequestParam(value = "name", defaultValue = "world") String name) {
        def greeting = new Greeting(counter.incrementAndGet(),
                String.format(template, name))
        greeting.runtimeException()
        return greeting;
    }

    /**
     * 默认情况下所有异常都由这里处理，所有的Exception子类
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exceptionHandle(Exception e) {
        Map resultMap = new HashMap()
        resultMap.put("message", e.getMessage())
        return resultMap
    }

    /**
     * 也可以自行配置对应的异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object runtimeExceptionHandle(Exception e) {
        Map resultMap = new HashMap()
        resultMap.put("message", e.getMessage())
        return resultMap
    }

}
