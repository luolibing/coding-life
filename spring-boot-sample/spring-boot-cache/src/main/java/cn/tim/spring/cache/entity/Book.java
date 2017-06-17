package cn.tim.spring.cache.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * User: luolibing
 * Date: 2017/4/21 11:01
 * Email: 397911353@qq.com
 */
@Data
public class Book implements Serializable {

    private static long counter = 0;

    private Long id = counter++;

    private String name;

    private Category category;

    private List<Tag> tags;
}
