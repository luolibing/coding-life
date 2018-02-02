package com.tim.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * User: luolibing
 * Date: 2018/2/2 15:06
 */
public class CacheSample {

    public LoadingCache<Long, Person> cache() {
        LoadingCache<Long, Person> caches = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(
                    new CacheLoader<Long, Person>() {
                        @Override
                        public Person load(Long key) throws Exception {
                            return createExpensivePerson(key);
                        }
                    });
        return caches;
    }

    private Person createExpensivePerson(Long key) {
        Person person = new Person();
        person.setId(key);
        person.setName("luolibing" + System.currentTimeMillis());
        person.setAge(30);
        return person;
    }

    @Test
    public void getCache() {
        LoadingCache<Long, Person> cache = cache();
        try {
            Person person = cache.get(100L);
            System.out.println(person);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
