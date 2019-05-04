package cn.hotspot.classloader;

public interface Function<T, R> {
    R apply(T t);
}
