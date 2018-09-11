package com.tim.dubbo.sample.future;

/**
 * Created by luolibing on 2018/9/10.
 */
public class FuturableProvider implements Futurable {

    @Override
    public String getData() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "luolibing" + System.currentTimeMillis();
    }

    @Override
    public void callback(String key, CallbackListener callbackListener) {
        System.out.println("receive key = " + key);
        callbackListener.callback("luolibing");
    }
}
