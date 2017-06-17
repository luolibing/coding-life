package cn.tim.mybatis.base.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by luolibing on 2017/4/1.
 */
//@Data
@Getter
@Setter
public class City {

    private Long id;

    private String name;

    private String countryCode;

    private String district;

    private Integer population;
}
