package com.tim.dubbo.sample.notify;

/**
 * Created by luolibing on 2018/9/13.
 */
public class NotifyImpl implements Notify {
    @Override
    public void onreturn(String message) {
        System.out.println("notify on return, message = " + message);
    }

    @Override
    public void onthrow(Throwable ex) {
        ex.printStackTrace();
    }
}
