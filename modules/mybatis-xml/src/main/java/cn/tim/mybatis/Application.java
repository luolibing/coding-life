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
