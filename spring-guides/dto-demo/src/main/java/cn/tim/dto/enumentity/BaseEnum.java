package cn.tim.dto.enumentity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/3/18.
 */
public class BaseEnum {

    protected Map<Integer, String> beanMap;

    public BaseEnum() {
        beanMap = new HashMap<>();
    }

    public String getName(Integer val) {
        return beanMap.get(val);
    }

    protected void put(Integer val, String name) {
        beanMap.put(val, name);
    }
}
