package cn.tim.springboot.restdoc;

import org.springframework.stereotype.Component;

/**
 * User: luolibing
 * Date: 2017/5/12 16:30
 */
@Component
public class PersonRepository {

    public Person findById(Long id) {
        Person person = new Person();
        person.setId(id);
        person.setName("luolibing");
        return person;
    }

    public void update(Person person) {

    }

    public void add(Person person) {

    }
}
