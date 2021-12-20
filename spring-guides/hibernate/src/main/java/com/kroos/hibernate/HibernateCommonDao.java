package com.kroos.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

/**
 * desc: TODO
 *
 * @author Kroos.luo
 * @since 2021-12-16 16:28
 */
@Service
public class HibernateCommonDao extends HibernateDaoSupport implements CommonDao {

    @Override
    public <T> T load(Class<T> clazz, Long id) {
        return this.getHibernateTemplate().get(clazz, id);
    }

    @Override
    public <T> void save(T t) {
        this.getHibernateTemplate().save(t);
    }

    @Autowired
    public void autowired(EntityManagerFactory entityManagerFactory) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        setSessionFactory(sessionFactory);
    }
}
