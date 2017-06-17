package cn.tim.dto.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoLiBing on 16/3/18.
 */
@Entity
@Table(name = "`order`")
public class Order extends PersistentObject {

    @Column(name = "user_id")
    private Long userID;

    @Column(name = "`code`")
    private Long code;

    @Column(name = "total_price", precision = 8, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "pay_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payTime;

    @Column(name = "`status`")
    private Integer status;

    @Column(name = "remark_description")
    private String remarkDesc;

    @OneToMany(targetEntity = OrderLog.class, fetch = FetchType.EAGER, mappedBy = "order",
            cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<OrderLog> orderLogs;

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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarkDesc() {
        return remarkDesc;
    }

    public void setRemarkDesc(String remarkDesc) {
        this.remarkDesc = remarkDesc;
    }

    public List<OrderLog> getOrderLogs() {
        return orderLogs;
    }

    public void setOrderLogs(List<OrderLog> orderLogs) {
        this.orderLogs = orderLogs;
    }
}
