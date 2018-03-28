package cn.tim.transaction.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by LuoLiBing on 15/10/9.
 */
public class BookingService {

    private final static Logger log = LoggerFactory.getLogger(BookingService.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 添加事物
     * @param persons
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(String... persons) {
        for(int i = 0; i < persons.length; i++) {
            String person = persons[i];
            log.info("insert person:" + person);
            jdbcTemplate.update("insert into bookings (first_name) values (?);", person);
            if(i ==3) {
                throw new IllegalArgumentException();
            }
        }
    }


    public List<String> queryAllBookings() {
        return jdbcTemplate.query("SELECT * FROM bookings", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("first_name");
            }
        });
    }
}
