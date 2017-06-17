package cn.tim.quartz.database.job.entity;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public enum JobPolicy {

    CRON(0), TIMER(1);

    private int type;

    JobPolicy(int type) {
        this.type = type;
    }

    public int value() {
        return type;
    }

    public boolean match(int type) {
        return this.type == type;
    }
}
