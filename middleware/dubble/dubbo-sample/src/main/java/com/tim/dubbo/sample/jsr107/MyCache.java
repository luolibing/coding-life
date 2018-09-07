package com.tim.dubbo.sample.jsr107;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by luolibing on 2018/9/7.
 */
public class MyCache<K, V> implements Cache {
    @Override
    public Object get(Object o) {
        return null;
    }

    @Override
    public Map getAll(Set set) {
        return null;
    }

    @Override
    public boolean containsKey(Object o) {
        return false;
    }

    @Override
    public void loadAll(Set set, boolean b, CompletionListener completionListener) {

    }

    @Override
    public void put(Object o, Object o2) {

    }

    @Override
    public Object getAndPut(Object o, Object o2) {
        return null;
    }

    @Override
    public void putAll(Map map) {

    }

    @Override
    public boolean putIfAbsent(Object o, Object o2) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o, Object o2) {
        return false;
    }

    @Override
    public Object getAndRemove(Object o) {
        return null;
    }

    @Override
    public boolean replace(Object o, Object o2, Object v1) {
        return false;
    }

    @Override
    public boolean replace(Object o, Object o2) {
        return false;
    }

    @Override
    public Object getAndReplace(Object o, Object o2) {
        return null;
    }

    @Override
    public void removeAll(Set set) {

    }

    @Override
    public void removeAll() {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public CacheManager getCacheManager() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void registerCacheEntryListener(CacheEntryListenerConfiguration cacheEntryListenerConfiguration) {

    }

    @Override
    public void deregisterCacheEntryListener(CacheEntryListenerConfiguration cacheEntryListenerConfiguration) {

    }

    @Override
    public Iterator<Entry> iterator() {
        return null;
    }

    @Override
    public Object unwrap(Class aClass) {
        return null;
    }

    @Override
    public Map invokeAll(Set set, EntryProcessor entryProcessor, Object... objects) {
        return null;
    }

    @Override
    public Object invoke(Object o, EntryProcessor entryProcessor, Object... objects) throws EntryProcessorException {
        return null;
    }

    @Override
    public Configuration getConfiguration(Class aClass) {
        return null;
    }
}
