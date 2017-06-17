package cn.tim.listener.publisher.entity;

import java.util.Random;

/**
 * Created by luolibing on 2017/4/12.
 */
public enum SendChannel {
    WECHAT("微信"), TIMLINE("timLine"), MAN("Man端"), DONGDONG("咚咚");

    public final String desc;

    private static Random rand = new Random(47);

    SendChannel(String desc) {
        this.desc = desc;
    }

    public static SendChannel rand() {
        return values()[rand.nextInt(values().length)];
    }
}
