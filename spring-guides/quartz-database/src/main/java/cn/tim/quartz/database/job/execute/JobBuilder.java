package cn.tim.quartz.database.job.execute;

import org.quartz.SchedulerException;

/**
 * Created by LuoLiBing on 16/6/23.
 *
 */
public interface JobBuilder {
    void build() throws SchedulerException;
}
