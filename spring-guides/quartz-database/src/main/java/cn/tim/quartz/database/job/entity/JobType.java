package cn.tim.quartz.database.job.entity;

/**
 * Created by LuoLiBing on 16/6/24.
 */
public enum JobType {

    LOCAL(0), RPC(1);

    private int type;

    JobType(int type) {
        this.type = type;
    }

    public int value() {
        return type;
    }

    public boolean match(int type) {
        return this.type == type;
    }
}
