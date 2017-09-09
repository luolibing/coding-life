package cn.tim.mybatis.entity.enums;

/**
 * User: luolibing
 * Date: 2017/9/9 11:32
 */
public enum CityStatus {
    CREATED(1), FINISHED(2), UNKNOW(3);

    public final int value;

    CityStatus(int value) {
        this.value = value;
    }

    public static CityStatus fromValue(Integer value) {
        if(value == null) {
            return UNKNOW;
        }

        for(CityStatus status : values()) {
            if(status.value == value) {
                return status;
            }
        }
        return UNKNOW;
    }
}
