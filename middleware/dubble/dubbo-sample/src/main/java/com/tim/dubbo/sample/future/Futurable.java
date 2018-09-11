package com.tim.dubbo.sample.future;

/**
 * Created by luolibing on 2018/9/10.
 */
public interface Futurable {
    String getData();

    void callback(String key, CallbackListener callbackListener);
}
