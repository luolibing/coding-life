package cn.tim.springboot.returnvalue.handler.advice;

/**
 * User: luolibing
 * Date: 2017/5/4 12:31
 * Email: 397911353@qq.com
 */
public class ResultModel {

    public final static ResultModel ok = ok(null);

    public final int code;

    public final String message;

    public final Object data;

    public ResultModel(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultModel ok(Object data) {
        return new ResultModel(200, "OK", data);
    }

    public static ResultModel newResultModel(int code, String message) {
        return newResultModel(code, message, null);
    }

    public static ResultModel newResultModel(int code, String message, Object data) {
        return new ResultModel(code, message, data);
    }
}
