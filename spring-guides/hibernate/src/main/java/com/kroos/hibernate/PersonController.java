package com.kroos.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2021-12-16 16:40
 */
@RequestMapping("/person")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public void save(@RequestBody Person person) {
        personService.save(person);
    }

    @PutMapping
    public void update(@RequestBody Person person) {
        personService.update(person.getId(), person.getName());
    }
}
