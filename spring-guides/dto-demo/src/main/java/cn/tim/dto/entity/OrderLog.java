package cn.tim.dto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by LuoLiBing on 16/3/18.
 */
@Entity
@Table(name = "order_log")
public class OrderLog extends PersistentObject implements Serializable {

    @ManyToOne(targetEntity = Order.class, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @Column(name = "user_id")
    private Long userID;

    @Column(name = "code")
    private Long code;

    @Column(name = "total_price", precision = 8, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "status")
    private Integer status;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
