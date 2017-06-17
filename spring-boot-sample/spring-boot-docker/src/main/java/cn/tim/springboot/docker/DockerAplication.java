package cn.tim.springboot.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在Dockerfile中配置docker配置，修改其中的参数
 * 然后在mvn package docker:build -DpushImage （如果不想push到docker.io中，也可以不加）
 * 然后就可以通过docker run -p 9999:8080 -t spring-boot-docker (9999为本机的端口，8080为docker中的端口)
 *
 * # 自定义docker镜像
 * docker build -t mydocker .
 * https://github.com/spring-guides/gs-spring-boot-docker.git
 * User: luolibing
 * Date: 2017/5/12 10:46
 */
@RestController
@SpringBootApplication
public class DockerAplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerAplication.class, args);
    }

    @GetMapping(value = "/")
    public String hello() {
        return "hello world, docker";
    }
}
