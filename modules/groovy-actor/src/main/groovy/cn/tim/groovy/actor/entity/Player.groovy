package cn.tim.groovy.actor.entity

import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor

/**
 * Created by LuoLiBing on 15/12/28.
 * 玩家actor
 */
class Player extends DefaultActor {

    String name
    Actor server
    int myNum

    @Override
    protected void act() {
        loop {
            myNum = new Random().nextInt(10000)
            // 发送num
            server.send myNum
            // 接收响应
            react {
                switch (it) {
                    case 'too large': println "$name: $myNum was too large"; break
                    case 'too small': println "$name: $myNum was too small"; break
                    case 'you win': println "$name: I won $myNum"; terminate(); break
                }
            }
        }
    }
}
