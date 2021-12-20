package com.kroos.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2021-12-16 16:20
 */
@Service
public class PersonService {

    @Autowired
    private HibernateCommonDao hibernateCommonDao;

    @Transactional
    public void save(Person person) {
        hibernateCommonDao.save(person);
    }

    public void update(Long id, String name) {
        Person person = hibernateCommonDao.load(Person.class, id);
        person.setName(name);
    }
}
