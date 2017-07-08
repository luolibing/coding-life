package cn.tim.springboot.returnvalue.handler;

import cn.tim.springboot.returnvalue.handler.advice.MyWrapper;
import cn.tim.springboot.returnvalue.handler.advice.ResultModel;
import cn.tim.springboot.returnvalue.handler.annotation.JsonpResponseBody;
import cn.tim.springboot.returnvalue.handler.annotation.ResultWrapper;
import cn.tim.springboot.returnvalue.handler.entity.Person;
import cn.tim.springboot.returnvalue.handler.exception.NotExistsException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * User: luolibing
 * Date: 2017/5/4 11:24
 * Email: 397911353@qq.com
 */
@SpringBootApplication
@RestController
@ResultWrapper
@EnableWebMvc
public class ReturnValueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReturnValueApplication.class, args);
    }

    @JsonpResponseBody
    @GetMapping(value = "/{id}")
    public Person findOne(@PathVariable Long id, @MyWrapper(value = "name", ifEmpty = "empty") String name) {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        return person;
    }

    @PostMapping
    public ResultModel add() {
        return ResultModel.ok;
    }

    @PutMapping
    public ResultModel update() {
        return ResultModel.ok;
    }

    @DeleteMapping(value = "/{id}")
    public ResultModel delete(@PathVariable Long id) {
        throw new NotExistsException();
    }

    @PostMapping("/save")
    public Person findOne(@RequestBody @MyWrapper Person person) {
        System.out.println(person);
        return person;
    }
}
