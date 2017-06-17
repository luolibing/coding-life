package cn.tim.autoconfiguration.beans;

/**
 * Created by luolibing on 2017/4/5.
 * 上下文，
 */
public interface Counting {
    long increment();
    void reset();
    long currentVal();
}
