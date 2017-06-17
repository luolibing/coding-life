package cn.tim.dto.enumentity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuoLiBing on 16/3/18.
 */
public class EnumProvider {

    private final static Map<String, BaseEnum> beanMap = new HashMap<>();

    private EnumProvider() {
    }

    public static void addEnum(String enumName, BaseEnum baseEnum) {
        beanMap.put(enumName, baseEnum);
    }

    public static BaseEnum getEnum(String enumName) {
        return beanMap.get(enumName);
    }

    /*public static void main(String[] args) {
        final GenderProvider genderProvider = new GenderProvider();
        final String json = new GenderProvider().toJson();
        System.out.println(json);
        final Map map = genderProvider.toMap(json);
        System.out.println(map);

    }*/
}
