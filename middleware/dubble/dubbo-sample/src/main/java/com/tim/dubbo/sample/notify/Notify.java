package com.tim.dubbo.sample.notify;

/**
 * Created by luolibing on 2018/9/12.
 */
public interface Notify {

    void onreturn(String message);

    void onthrow(Throwable ex);
}
