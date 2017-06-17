package cn.tim.thinking.clazz14;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/9/27.
 */
public class CastClassTest {

    class Building {}
    class House extends Building {}

    /**
     * 可以使用Class对象的cast方法进行强转, target = class.cast(sourceObj)
     *
     */
    @Test
    public void test1() {
        Building b = new House();
        Class<House> houseType = House.class;
        // 获取子类的类型
        Class<? extends Building> aClass = houseType.asSubclass(Building.class);
        // if (obj != null && !isInstance(obj))
        House h = houseType.cast(b);
        System.out.println(h == b);
        h = (House) b;
    }

    @Test
    public void test2() throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> beanMap = new HashMap<>();
        beanMap.put("id", 2L);
        beanMap.put("name", "luolibing");
        Employee target = new Employee();
        BeanUtils.copyProperties(new Employee(), beanMap);
        System.out.println(target);
    }
}
