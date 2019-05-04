package cn.tim.db.sample1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParallUpdateService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public synchronized void increment() {
        Integer count = jdbcTemplate.queryForObject("select score from counter where id=1", Integer.class);
        System.out.println(count);
        count += 1;
        jdbcTemplate.update("update counter set score=? where id=1", new Object[] {count});
    }
}
