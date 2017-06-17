package cn.tim.restful.controller
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.RestTemplate

import java.util.concurrent.Executors
/**
 * Created by LuoLiBing on 15/10/27.
 */
/**
 * Created by LuoLiBing on 15/9/23.
 * 所有controller异常处理
 */
@ControllerAdvice
class ExceptionHandlerControllerAdvice {

    /*
     * 捕获的异常，默认情况下都是ajax请求
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Map<String, Object> allCatchException(Exception e) {
        e.printStackTrace();
        Map<String, Object> result = new HashMap<>()
        result.put("success", "N");
        result.put("message", e.getMessage());
        return result;
    }

    public static void main(String[] args) {
        def rest = new RestTemplate();
        def pool = Executors.newCachedThreadPool();
        for(int i = 0; i < 10000; i++) {
            pool.execute(new Runnable() {
                @Override
                void run() {
                    rest.getForEntity("http://localhost:8010/page", Object.class)
                }
            })
        }
    }
}
