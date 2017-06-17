package cn.tim.mybatis.base;

import cn.tim.mybatis.base.entity.City;
import cn.tim.mybatis.base.entity.CityForm;
import cn.tim.mybatis.base.entity.User;
import cn.tim.mybatis.base.mapper.CityMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.List;

/**
 * Created by luolibing on 2017/3/31.
 */
public class MyBatisSample1 {

    @Test
    public void sample1() {
        execute(((session, selectName, arg) -> {
            User user = session.selectOne(selectName, arg);
            System.out.println(user);
        }), "mybatis","selectUserById", 100L);
    }

    /**
     * 单条件查询
     */
    @Test
    public void sample2() {
        execute(((session, selectName, arg) -> {
            City city = session.selectOne(selectName, arg);
            System.out.println(city);
        }),"mybatis-world.xml", "selectCityById", 100L);
    }

    /**
     * 多条件查询
     */
    @Test
    public void sample3() {
        CityForm form = new CityForm()
                .countryCode("NLD")
                .district("Zuid-Holland");
        execute(((session, selectName, arg) -> {
            List<City> cities = session.selectList(selectName, arg);
            System.out.println(cities);
        }), "mybatis-world.xml", "selectByCondition", form);
    }

    /**
     * 插入数据
     */
    @Test
    public void sample4() {
        City city = new City();
        city.setName("luolibing");
        city.setCountryCode("LLB");
        city.setDistrict("LuoLiBing");
        city.setPopulation(100);
        execute(((session, selectName, arg) -> {
            CityMapper mapper = session.getMapper(CityMapper.class);
            if(arg instanceof City) {
                mapper.insert((City) arg);
            }
        }), "mybatis-world.xml", "insert", city);
    }

    /**
     * 删除
     */
    @Test
    public void delete() {
        execute((SqlSession::delete), "mybatis-world.xml", "delete", 100L);
    }

    /**
     * 修改
     */
    @Test
    public void update() {
        City city = new City();
        city.setName("luolibing");
        city.setCountryCode("LLB");
        city.setDistrict("LuoLiBing");
        city.setId(101L);
        city.setPopulation(100);
        execute(((session, selectName, arg) -> {
            int result = session.update(selectName, city);
            System.out.println(result);
        }), "mybatis-world.xml", "update" ,100L);
    }

    public void execute(Handler handler, String batisXml, String selectName, Object args) {
        Resource resource = new ClassPathResource(batisXml);
        try(InputStream in = resource.getInputStream()) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
            try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
                handler.handle(sqlSession, selectName, args);
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface Handler {
        void handle(SqlSession session, String selectName, Object arg);
    }

    @Test
    public void instanceOf() {
        Object city = null;
        // 不会抛异常
        System.out.println(City.class.isInstance(city));
        System.out.println(city instanceof City);
    }
}
