package cn.tim.dto.view;

import cn.tim.dto.merger.SimpleEnumMerger;
import org.jdto.annotation.DTOCascade;
import org.jdto.annotation.Source;
import org.jdto.mergers.DateFormatMerger;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by LuoLiBing on 16/3/18.
 */
public class OrderView implements Serializable {

    private Long userID;

    private Long code;

    private BigDecimal totalPrice;

    @Source(value = "payTime", merger = DateFormatMerger.class, mergerParam = "yyyy年MM月dd日 HH:mm:ss")
    private String payTime;

    @Source(value = "status", merger = SimpleEnumMerger.class, mergerParam = "orderStatus")
    private String status;

    /*@Source(value = "status")
    private Integer statusCode;*/

    private String remarkDesc;

    /**
     * @Source(value = "orderLogs")
     * 得使用数组来接收,使用SET集合接收不到
     */
    @Source(value = "orderLogs")
    @DTOCascade
    private OrderLogView[] orderLogs;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarkDesc() {
        return remarkDesc;
    }

    public void setRemarkDesc(String remarkDesc) {
        this.remarkDesc = remarkDesc;
    }

    public OrderLogView[] getOrderLogs() {
        return orderLogs;
    }

    public void setOrderLogs(OrderLogView[] orderLogs) {
        this.orderLogs = orderLogs;
    }

    /*public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }*/
}
