package cn.tim.reback.resource;

import cn.tim.reback.resource.entity.Resource;
import cn.tim.reback.resource.entity.jpa.ResourceJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by LuoLiBing on 16/3/14.
 */
@SpringBootApplication
@RestController
public class Application {

    private final static int pageSize = 100;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    ResourceJpaRepository resourceJpaRepository;

    @RequestMapping(value = "/transfer/{type}")
    public Object transfer(@PathVariable String type) {
        final List<Resource> resources = resourceJpaRepository.findByType(type);
        for(Resource resource:resources) {
            try {
                resource.rename();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("finish!!!!");
        return Collections.singletonMap("success", "Y");
    }
}
