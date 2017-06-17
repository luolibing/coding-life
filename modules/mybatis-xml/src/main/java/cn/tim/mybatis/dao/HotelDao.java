package cn.tim.mybatis.dao;

import cn.tim.mybatis.entity.Hotel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 17/3/22.
 */
@Component
public class HotelDao {

    private final SqlSession session;

    public HotelDao(SqlSession session) {
        this.session = session;
    }

    public Hotel selectByCityId(long cityId) {
        return session.selectOne("selectByCityId", cityId);
    }

    public void save(Hotel hotel) {

    }
}
