package cn.tim.integration.producer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by LuoLiBing on 16/1/21.
 */
@Configuration
public class ProducerApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Files.createDirectories(Paths.get("target/input"));
        if(args.length > 0) {
            try(FileOutputStream out = new FileOutputStream(
                    "target/input/data" + System.currentTimeMillis() + ".txt")) {
                for(String arg: args) {
                    out.write(arg.getBytes());
                }
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
}
