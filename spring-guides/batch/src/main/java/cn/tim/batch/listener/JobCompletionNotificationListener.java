package cn.tim.batch.listener;

import cn.tim.batch.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by LuoLiBing on 15/10/9.
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final static Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("任务完成");
            List<Person> persons = jdbcTemplate.query("SELECT first_name, last_name from people", new RowMapper<Person>() {
                @Override
                public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new Person(rs.getString(1), rs.getString(2));
                }
            });

            for(Person person: persons) {
                log.info(String.format("person firstname:%s, lastname:%s", person.getFirstName(), person.getLastName()));
            }
        }
    }
}
