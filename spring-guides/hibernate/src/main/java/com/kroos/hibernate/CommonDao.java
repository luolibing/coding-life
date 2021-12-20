package com.kroos.hibernate;

public interface CommonDao {

    <T> T load(Class<T> clazz, Long id);

    <T> void save(T t);
}
