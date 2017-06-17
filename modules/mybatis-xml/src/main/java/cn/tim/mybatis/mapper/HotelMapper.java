package cn.tim.mybatis.mapper;

import cn.tim.mybatis.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by LuoLiBing on 17/3/22.
 */
@Mapper
public interface HotelMapper {

    @Select("select * from Hotel where zip= #{zip}")
    Hotel findByZip(String zip);
}
