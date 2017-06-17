package cn.tim.autoconfiguration.beans;

import cn.tim.autoconfiguration.config.CountingProperties;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by luolibing on 2017/4/5.
 */

public class AtomicCounting implements Counting {

    private AtomicLong atomicLong;

    private CountingProperties countingProperties;

    public AtomicCounting(CountingProperties countingProperties) {
        atomicLong = new AtomicLong(countingProperties.getInitValue());
        this.countingProperties = countingProperties;
    }

    @Override
    public long increment() {
        return atomicLong.getAndAdd(countingProperties.getStep());
    }

    @Override
    public void reset() {
        atomicLong.set(countingProperties.getInitValue());
    }

    @Override
    public long currentVal() {
        return atomicLong.get();
    }
}
