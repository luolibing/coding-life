package cn.tim.springboot.returnvalue.handler.advice;

import cn.tim.springboot.returnvalue.handler.exception.BaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常统一处理
 * User: luolibing
 * Date: 2017/5/4 12:59
 * Email: 397911353@qq.com
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 自定义的异常，每个异常都绑定了一个状态码和状态信息
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public ResultModel resultModel(BaseException e) {
        return ResultModel.newResultModel(e.getErrorCode(), e.getErrorMessage(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultModel defaultExceptionHandle(Exception e) {
        return ResultModel.newResultModel(400, e.getMessage());
    }
}
