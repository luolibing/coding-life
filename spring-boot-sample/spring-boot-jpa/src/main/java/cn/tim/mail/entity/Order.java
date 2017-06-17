package cn.tim.mail.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LuoLiBing on 16/3/2.
 */
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long code;

    // orphanRemoval=true解决递归删除
    @OneToMany(targetEntity = OrderProduct.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    // 解决循环调用问题
    @JsonManagedReference
    private Set<OrderProduct> orderProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        if(orderProducts == null) {
            orderProducts = new HashSet<>();
        }
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void removeOrderProduct(Long id) {
        if(orderProducts == null) {
            return;
        }
        for(OrderProduct orderProduct: orderProducts) {
            if(orderProduct.getId().equals(id)) {
                orderProducts.remove(orderProduct);
                return;
            }
        }
    }
}
