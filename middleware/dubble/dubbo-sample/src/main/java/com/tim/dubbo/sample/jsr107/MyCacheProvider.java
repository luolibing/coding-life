package com.tim.dubbo.sample.jsr107;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

/**
 * Created by luolibing on 2018/9/7.
 */
public class MyCacheProvider implements CachingProvider {
    @Override
    public CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
        return null;
    }

    @Override
    public ClassLoader getDefaultClassLoader() {
        return null;
    }

    @Override
    public URI getDefaultURI() {
        return null;
    }

    @Override
    public Properties getDefaultProperties() {
        return null;
    }

    @Override
    public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
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
    public void close(ClassLoader classLoader) {

    }

    @Override
    public void close(URI uri, ClassLoader classLoader) {

    }

    @Override
    public boolean isSupported(OptionalFeature optionalFeature) {
        return false;
    }
}
