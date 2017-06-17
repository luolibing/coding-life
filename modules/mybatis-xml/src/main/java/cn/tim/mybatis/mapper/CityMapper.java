package cn.tim.mybatis.mapper;

import cn.tim.mybatis.entity.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by LuoLiBing on 17/3/22.
 */
@Mapper
public interface CityMapper {

    /**
     * 如果Mapper.xml文件和@Mapper类中没有定义对应的select, 会抛出BindingException: Invalid bound statement (not found)异常.
     * 如果这里没有定义select则Mapper.xml中必须配置.
     */
    City selectCityById(Long id);

    List<City> selectCityByAddress(String country);
}
