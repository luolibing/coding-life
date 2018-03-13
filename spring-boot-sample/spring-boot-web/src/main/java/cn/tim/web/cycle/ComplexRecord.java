package cn.tim.web.cycle;

import lombok.Data;

import java.time.Instant;

/**
 * User: luolibing
 * Date: 2018/3/12 10:09
 */
@Data
public class ComplexRecord {

    private String id;
    private AnyObject data;
    private String status;
    private Instant createdDateTime;

}
