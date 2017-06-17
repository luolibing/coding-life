package cn.tim.spring4.ioc722;

import java.util.Collections;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/5/19.
 */
public class TestDao {

    public Map findOne() {
        return Collections.singletonMap("name", "luolibing");
    }
}
