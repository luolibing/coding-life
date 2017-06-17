package cn.tim.mybatis.base.entity;

import lombok.Data;

/**
 * Created by luolibing on 2017/4/1.
 */
@Data
public class CityForm {
    private Long id;
    private String name;
    private String countryCode;
    private String district;

    public CityForm id(Long id) {
        this.id = id;
        return this;
    }

    public CityForm name(String name) {
        this.name = name;
        return this;
    }

    public CityForm countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public CityForm district(String district) {
        this.district = district;
        return this;
    }
}
