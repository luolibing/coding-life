package cn.tim.velocity.entity;

import java.util.Random;

/**
 * Created by luolibing on 2017/4/6.
 */
public enum EventType {
    ADD("创建"), UPDATE("更新"), DELETE("删除"), SELECT("查询");
    public final String eventDesc;
    private final static Random rand = new Random(47);

    EventType(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public static EventType randEventType() {
        return values()[rand.nextInt(values().length)];
    }
}
