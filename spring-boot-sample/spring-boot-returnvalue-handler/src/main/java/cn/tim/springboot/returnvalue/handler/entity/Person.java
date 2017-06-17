package cn.tim.springboot.returnvalue.handler.entity;

import lombok.Data;

/**
 * User: luolibing
 * Date: 2017/5/4 12:39
 * Email: 397911353@qq.com
 */
@Data
public class Person {

    private static long counter = 0;

    private long id = counter++;

    private String name;

    private int age = 18;
}
