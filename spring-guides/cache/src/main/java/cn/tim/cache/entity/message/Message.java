package cn.tim.cache.entity.message;

/**
 * Created by LuoLiBing on 16/1/29.
 */
public class Message<T> {
    String name;
    T data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
