package cn.tim.restful.annoction;

/**
 * Created by luolibing on 2017/7/8.
 */
public class WrapperEntity {

    private int returnCode;

    private Object data;

    private String message;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
