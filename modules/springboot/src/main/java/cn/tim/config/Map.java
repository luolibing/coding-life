package cn.tim.config;

/**
 * Created by LuoLiBing on 16/7/15.
 */
public class Map {
    String key;
    String value;

    public Map() {
    }

    public Map(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}