package cn.tim.springboot.transactionmq.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by luolibing on 2017/6/18.
 */
@Data
@Entity
@Table
public class TimOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderCode;

    private Long productId;

    private String productName;

    private Integer count;

    private Long price;

    private Long amount;

    private Date created;
}
