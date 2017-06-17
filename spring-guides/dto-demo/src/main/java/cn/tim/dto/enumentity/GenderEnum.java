package cn.tim.dto.enumentity;

import org.springframework.stereotype.Component;

/**
 * Created by LuoLiBing on 16/3/18.
 */
@Component
public class GenderEnum extends BaseEnum {

    private final static String name = "gender";

    public GenderEnum() {
        put(0, "男");
        put(1, "女");
        put(2, "未知");
        EnumProvider.addEnum(name, this);
    }

}
