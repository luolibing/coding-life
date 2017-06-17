package cn.tim.spring.cache.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * User: luolibing
 * Date: 2017/4/21 11:02
 * Email: 397911353@qq.com
 */
@Data
public class Tag implements Serializable {

    private static long counter = 0;

    private Long id = counter++;

    private String name;
}
