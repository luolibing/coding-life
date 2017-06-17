package cn.tim.mail.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by LuoLiBing on 16/2/29.
 */
@Entity
@Table(name = "work_order")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String status;

    @ManyToOne(targetEntity = Service.class)
    @JoinColumn(name = "service_id")
    @JsonBackReference
    private Service service;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
