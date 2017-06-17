package cn.tim.groovy.actor.entity

import groovyx.gpars.actor.DefaultActor

/**
 * Created by LuoLiBing on 15/12/26.
 * 服务器actor
 */
class MastActor extends DefaultActor {

    int secretNum

    void afterStart() {
        secretNum = new Random().nextInt(10000)
    }

    /**
     * 超时设置
     */
    public void onTimeout() {
        // 超时
    }

    @Override
    protected void act() {
        loop {
            // 服务器接收响应,并且回复
            react { int num ->
                if(num > secretNum)
                    reply "too large"
                else if (num < secretNum)
                    reply "too small"
                else {
                    reply "you win"
                    terminate()
                }

            }
        }
    }


}
