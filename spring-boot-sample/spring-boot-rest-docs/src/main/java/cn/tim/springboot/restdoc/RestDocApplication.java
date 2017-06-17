package cn.tim.springboot.restdoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User: luolibing
 * Date: 2017/5/12 16:05
 */
@RestController
@SpringBootApplication
public class RestDocApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestDocApplication.class, args);
    }

    @Autowired
    private PersonRepository repository;

    @GetMapping(value = "/")
    public String hello() {
        return "hello world!";
    }

    @GetMapping(value = "/{id}")
    public Person findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping(value = "/")
    public ResponseEntity add(@RequestBody Person person) {
        repository.add(person);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/")
    public ResponseEntity update(@RequestBody Person person) {
        repository.update(person);
        return ResponseEntity.ok().build();
    }
}
