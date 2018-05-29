package cn.tim.mail.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by LuoLiBing on 16/2/29.
 */
@Entity
@Table
@Cacheable
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "sku_name")
    private String productSkuName;

    @OneToMany(targetEntity = WorkOrder.class)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "service_id")
    // 解决循环调用问题
    @JsonManagedReference
    private Set<WorkOrder> workOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
    }

    public Set<WorkOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(Set<WorkOrder> workOrders) {
        this.workOrders = workOrders;
    }

    public void addWorkOrder(WorkOrder workOrder) {
        if(workOrders == null) {
            workOrders = new HashSet<>();
        }
        workOrders.add(workOrder);
        workOrder.setService(this);
    }
}
