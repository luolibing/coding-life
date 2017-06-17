package cn.tim.mockito.s1;

/**
 * User: luolibing
 * Date: 2017/4/28 10:46
 * Email: 397911353@qq.com
 */
public enum CarStatusEnum {
    BASE(0), MODEL(1),COLOR(2);

    public final int status;

    CarStatusEnum(int status) {
        this.status = status;
    }
}
