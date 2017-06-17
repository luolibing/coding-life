package cn.tim.groovy.actor

import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor
import org.junit.Test

import static groovyx.gpars.actor.Actors.actor

/**
 * Created by LuoLiBing on 15/12/30.
 */
class GparsTest {

    @Test
    void receiveMessage() {
        def console = actor {
            loop {
                react {
                    println it
                }
            }
        }

        console << "hello world!"
    }

    class CustomActor extends DefaultActor {
        @Override
        protected void act() {
            loop {
                react {
                    println it
                    reply("good !!")
                }
            }
        }


    }

    @Test
    void customTest() {
        def console = new CustomActor()
        console.start()

//        console.send "hello World!"
//        console "hello"
//        console.sendAndWait "World!"
        console.send "hello world!", { println "i received reply $it"}
    }

    /**
     * 异步处理
     */
    @Test
    void asynchronous() {
        final def decryptor = actor {
            loop {
                react { String message ->
                    reply message.reverse()
                }
            }
        }

        final def console = actor {
            decryptor.send 'HelloWorld!'
            react {
                println "Message: $it"
            }
        }

        console.join()
    }


    @Test
    void timeOut() {
        def friend = actor {
            react {
                // 接收响应
                println it
                // 做出响应
                sleep 3000
                reply "i have received! see you"
                react {
                    println it
                }
            }
        }

        def me = actor {
            friend.send "How are you"

            // 添加超时事件监听  callDynamic("onTimeout", EMPTY_ARGUMENTS);
            /*delegate.metaClass.onTimeout = {
                friend.send "are you busy"
                stop()
            }*/

            // 设置超时时间
            react(2000) { msg ->
                // 判断是否超时
                if(msg == Actor.TIMEOUT) {
                    friend.send "are you busy"
                    sleep 2000
                    stop()
                } else {
                    println "thanks for $msg"
                }
            }
        }
        me.join()
    }

    class MyCounterActor extends DefaultActor {
        private Integer counter = 0

        @Override
        protected void act() {
            loop {
                react {
                    counter ++
                }
            }
        }

        public Integer getCount() {
            return counter
        }

    }

    @Test
    void testCounter() {

        def actor = new MyCounterActor()
        for(i in 0..1000) {
            actor.send i
        }
        println actor.count
    }

}
