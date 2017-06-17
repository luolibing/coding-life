package cn.tim.springboot.returnvalue.handler.exception;

/**
 * User: luolibing
 * Date: 2017/5/4 13:06
 * Email: 397911353@qq.com
 */
public class NestException extends BaseException {

    public NestException() {
        this("重复数据");
    }

    public NestException(String exceptionDesc) {
        super(100002, "重复数据", exceptionDesc);
    }
}
