package cn.tim.mybatis;

import cn.tim.mybatis.dao.HotelDao;
import cn.tim.mybatis.entity.City;
import cn.tim.mybatis.entity.Hotel;
import cn.tim.mybatis.mapper.CityMapper;
import cn.tim.mybatis.mapper.HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * mybatis需要创建出SqlSessionFactory，默认可以使用SqlSessionFactoryBuilder来进行build
 * 使用spring-mybatis，原理则是，spring使用SqlSessionFactoryBean来buildSqlSessionFactory()
 * 使用spring-boot-mybatis，原理是autoconfig根据约定以及配置来创建出SqlSessionFactoryBean
 * 所以研究mybatis重点是研究sqlSessionFactory
 * Created by LuoLiBing on 17/3/22.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private HotelDao hotelDao;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private CityMapper cityMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        City city = cityMapper.selectCityById(1L);
        System.out.println(city);

        /**
         * 当查询结果为空时，mybatis返回emptyList而不是null
         */
        List<City> citys = cityMapper.selectCityByAddress("afdsfs");
        System.out.println(citys);

        Hotel hotel = hotelDao.selectByCityId(1);
        System.out.println(hotel);

        hotel = hotelMapper.findByZip("4001");
        System.out.println(hotel);
    }
}
