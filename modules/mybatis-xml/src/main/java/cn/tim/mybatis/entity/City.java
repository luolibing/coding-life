package cn.tim.mybatis.entity;

import cn.tim.mybatis.scan.Tim;
import lombok.Data;

/**
 * Created by LuoLiBing on 17/3/22.
 */
@Tim
@Data
public class City {

    private Long id;

    private String name;

    private String state;

    private String country;
}
