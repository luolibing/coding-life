package cn.tim.ehcache;

import lombok.Data;

import java.io.Serializable;

/**
 * User: luolibing
 * Date: 2018/3/13 19:32
 */
@Data // lombok generates the Getter and Setters
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

}
