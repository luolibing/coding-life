package cn.tim.web.cycle;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: luolibing
 * Date: 2018/3/12 10:10
 */
@Data
public class AnyObject {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Map<String, Object> data;

    public AnyObject() {
        this(new LinkedHashMap<>());
    }

    public AnyObject(Map<String, Object> sourceData) {
        this.data = new LinkedHashMap<>(sourceData);
    }

    @JsonAnySetter
    public void setData(String name, Object value) {
        this.data.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(this.data);
    }

}