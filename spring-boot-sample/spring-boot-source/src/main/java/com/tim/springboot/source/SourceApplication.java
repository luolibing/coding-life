package com.tim.springboot.source;

import com.tim.springboot.source.config.B;
import com.tim.springboot.source.config.C;
import com.tim.springboot.source.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User: luolibing
 * Date: 2017/9/1 10:44
 */
@SpringBootApplication
public class SourceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SourceApplication.class, args);
    }

    @Autowired
    private B b;

    @Autowired
    private DataService dataService;

    @Autowired
    private C c;

    public void run(String... strings) throws Exception {
        b.say();
        c.say();
        dataService.dataConfig();
    }
}
