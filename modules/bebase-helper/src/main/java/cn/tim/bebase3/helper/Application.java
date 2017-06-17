package cn.tim.bebase3.helper;

import cn.tim.bebase3.helper.web.DownLoads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by LuoLiBing on 16/12/16.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String basePath = "/Users/tim/Downloads/avatars";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public List<String> getAvatars(long from, long count) {
        return jdbcTemplate.queryForList("select figure_url from USER limit ?, ?", new Object[]{from, count}, String.class);
    }


    @Override
    public void run(String... strings) {
        System.out.println("start");
        List<String> avatars = getAvatars(200 * 1000, 1000);
        int count = 0;
        for(String url : avatars) {
            if(!StringUtils.isEmpty(url)) {
                try {
                    DownLoads.download(url, basePath);
                    System.out.println("count = " + count++);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("end");
    }
}
