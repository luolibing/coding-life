package com.tim.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

                        @Override
                        public ListenableFuture<Person> reload(Long key, Person oldValue) throws Exception {
                            return super.reload(key, oldValue);
                        }
                    });
        return caches;
    }

    public LoadingCache<Long, Person> asyncCache() {

        LoadingCache<Long, Person> caches = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build(
                        CacheLoader.asyncReloading(new CacheLoader<Long, Person>() {
                            @Override
                            public Person load(Long key) throws Exception {
                                return createExpensivePerson(key);
                            }
                        }, Executors.newFixedThreadPool(100)));
        return caches;
    }

    private Person createExpensivePerson(Long key) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("load By ExpensivePerson");
        Person person = new Person();
        person.setId(key);
        person.setName("luolibing" + System.currentTimeMillis());
        person.setAge(30);
        return person;
    }

    @Test
    public void getCache() throws ExecutionException {
        LoadingCache<Long, Person> cache = asyncCache();
        Person person = cache.get(100L);
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            for(int i = 0; i< 1000; i++) {
                exec.submit(() -> {
                    cache.refresh(100L);
                });
            }
            exec.awaitTermination(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
