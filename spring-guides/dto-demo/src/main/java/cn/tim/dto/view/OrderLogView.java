package cn.tim.dto.view;

import cn.tim.dto.merger.SimpleEnumMerger;
import org.jdto.annotation.Source;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by LuoLiBing on 16/3/18.
 */
public class OrderLogView implements Serializable {

    private Long userID;

    private Long code;

    private BigDecimal totalPrice;

    @Source(value = "status", merger = SimpleEnumMerger.class, mergerParam = "orderStatus")
    private String status;

    /*@Source(value = "status")
    private Integer statusCode;*/

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }*/
}
