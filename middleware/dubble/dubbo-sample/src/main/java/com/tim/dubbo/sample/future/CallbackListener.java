package com.tim.dubbo.sample.future;

import java.io.Serializable;

/**
 * Created by luolibing on 2018/9/11.
 */
public interface CallbackListener extends Serializable {

    void callback(String name);
}
