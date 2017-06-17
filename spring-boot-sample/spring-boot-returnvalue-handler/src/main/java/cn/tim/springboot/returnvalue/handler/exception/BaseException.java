package cn.tim.springboot.returnvalue.handler.exception;

/**
 * User: luolibing
 * Date: 2017/5/4 13:31
 */
public class BaseException extends RuntimeException {

    protected int errorCode;

    protected String errorMessage;

    public BaseException(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, errorMessage);
    }

    public BaseException(int errorCode, String errorMessage, String exceptionDesc) {
        super(exceptionDesc);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
