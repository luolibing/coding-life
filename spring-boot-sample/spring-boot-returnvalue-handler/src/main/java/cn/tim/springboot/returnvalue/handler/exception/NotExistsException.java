package cn.tim.springboot.returnvalue.handler.exception;

/**
 * User: luolibing
 * Date: 2017/5/4 13:05
 * Email: 397911353@qq.com
 */
public class NotExistsException extends BaseException {

    public NotExistsException() {
        this("不存在");
    }

    public NotExistsException(String exceptionDesc) {
        super(100001, "不存在", exceptionDesc);
    }
}
