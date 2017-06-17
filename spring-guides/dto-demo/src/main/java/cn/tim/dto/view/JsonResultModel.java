package cn.tim.dto.view;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 视图返回对象
 * Created by liuzhihao on 16/3/14.
 */
public class JsonResultModel {

    public static JsonResultModel newJsonResultModel(Object data) {
        return new JsonResultModel(data);
    }

    private Object data;

    private int returnCode = 200;

    private String returnMsg = "success";
 
    private JsonResultModel(Object data) {
      this.data = data;
    }

    public JsonResultModel() {
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public <T> T getData(Class<T> clazz) {
        // 不是Map不转换
        if(data == null) {
            return null;
        }
        return new ObjectMapper().convertValue(data, clazz);
    }

    public static void main(String[] args) {
        final OrderView orderView = new OrderView();
        orderView.setCode(100322L);
        orderView.setPayTime("2015-0432432");
        orderView.setStatus("121");
        OrderLogView orderLogView = new OrderLogView();
        orderLogView.setCode(4434343L);
        orderLogView.setStatus("设置");
        orderView.setOrderLogs(new OrderLogView[]{orderLogView});

        OrderView data = new JsonResultModel(orderView).getData(OrderView.class);
        System.out.println(data);
    }
}
