package cn.tim.mybatis.dao;

import cn.tim.mybatis.entity.City;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 17/3/22.
 */
@Component
public class CityDao {

    private final SqlSession session;

    public CityDao(SqlSession session) {
        this.session = session;
    }

    public City selectCityById(long id) {
        return session.selectOne("selectCityById", id);
    }
}
