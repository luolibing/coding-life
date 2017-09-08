package cn.tim.mybatis.entity;

import cn.tim.mybatis.scan.Tim;
import lombok.Data;

/**
 * Created by LuoLiBing on 17/3/22.
 */
@Tim
@Data
public class Hotel {

    private Long city;

    private String name;

    private String address;

    private String zip;
}
