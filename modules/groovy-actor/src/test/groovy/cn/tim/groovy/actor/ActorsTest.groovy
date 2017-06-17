package cn.tim.groovy.actor
import cn.tim.groovy.actor.entity.MastActor
import cn.tim.groovy.actor.entity.Player
import groovy.time.TimeCategory
import groovy.util.logging.Slf4j
import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.Actors
import groovyx.gpars.actor.DefaultActor
import org.junit.Test

import java.util.concurrent.TimeUnit


/**
 * Created by LuoLiBing on 15/12/28.
 */
@Slf4j
class ActorsTest {

    /************向一个actor发送消息**************/
    @Test
    void sendTest1() {
        def passiveActor = Actors.actor {
            loop {
                react { msg -> log.info "Received: $msg" }
            }
        }
        passiveActor.send "Hello"
        passiveActor << "World"
        passiveActor "Hello World!"
    }

    @Test
    void sendTest2() {

        def replyingActor = Actors.actor {
            loop {
                react { msg ->
                    println "Received $msg"
                    sleep 5000
                    reply "I've got $msg"
                }
            }
        }
        replyingActor.sendAndWait('Hello')
        replyingActor.sendAndWait("World", 3, TimeUnit.SECONDS)
        use(TimeCategory) {
            replyingActor.sendAndWait("HelloWorld", 10.seconds)
        }

    }

    @Test
    void sendTest3() {
        def friend = Actors.actor {
            loop {
                react { msg ->
                    println "Received $msg"
                    //sleep 5000
                    reply "100"
                }
            }
        }
        friend.sendAndContinue "I need money!", { money -> println money}
        sleep(2000)
        println "can continue"
    }

    @Test
    void receiveTest() {
        Actors.actor {
            loop {
                println 'Waiting for a gift'
                react { gift ->
                    if (gift == "rose") reply 'Thank you!'
                    else {
                        reply 'Try again, please'
                        react {anotherGift ->
                            if (anotherGift == "car") reply 'Thank you!'
                        }
                        println 'Never reached'
                    }
                }
                println 'Never reached'
            }
            println 'Never reached'
        }
    }
    /****************发送******************/

    @Test
    void sendReplies1() {

        def decryptor = Actors.actor {
            react { message ->
                reply message.reverse()

            }
        }

        def console = Actors.actor {
            react {
                println 'Decrypted message: ' + it
            }
        }
        decryptor.send 'hello world!', console
        console.join()
    }


    @Test
    void sendReplies2() {

        def decryptor = Actors.actor {
            loop {
                react { String message ->
                    if ('stopService' == message) {
                        println "Stopping decryptor"
                        stop()
                    } else {
                        reply message.reverse()
                    }
                }
            }
        }

        Actors.actor {
            decryptor.send 'Hello World!'
            react {
                println "Decrypted message: $it"
                decryptor.send 'stopService'
            }
        }.join()
        
    }

    @Test
    void sendReplies3() {

        def friend = Actors.actor {
            // 接收第一次请求
            react {
                println it
                sleep 3000
                // 第二次请求
                react { println it }
            }
        }
        def me = Actors.actor {
            friend.send "Hi"
            // 超时时间
            react(1000) { message ->
                if (message == Actor.TIMEOUT) {
                    friend.send "I see, busy as usual. Never mind"
                    stop()
                } else {
                    println "Thanks for $message"
                }
            }
        }
        me.join()
    }

    /**
     * 无法投递的消息
     */
    @Test
    void sendReplies4() {
        final DefaultActor me
        // 无法处理消息的时候可以使用onDeliveryError方法进行处理
        me = Actors.actor {
            def message = 1
            // 当消息无法投递时,调用的方法
            message.metaClass.onDeliveryError = { ->
                me << "could not deliver $delegate"
            }

            /*me.metaClass.onDeliveryError = {msg ->
                //callback on actor inaccessibility
                println "Could not deliver message $msg"
            }*/

            def actor = Actors.actor {
                // 接收一次方法就停止掉,第二次message无法投递
                react {
                    sleep(2000)
                    stop()
                }
            }

            actor << message
            // 第二次消息无法投递
            actor << message

            // 做出反应
            react {
                println it
            }
        }

        me.join()

    }

    /**
     * master和player连接执行
     */
    @Test
    void sendReplies5() {
        // masterActor
        def master = new MastActor().start()
        // 指定服务
        def player = new Player(name: 'Plater', server: master).start()
        // join
        [master, player]*.join()
    }

    /**
     * 条件式和循环统计
     */
    @Test
    void conditionalAndLoop1() {

        final def actor = Actors.actor {
            def candidates = []
            // 执行的方法
            def printResult = { -> println "the best offer is ${candidates.max()}"}

            // 循环三次之后,然后执行printResult
            loop(3, printResult) {
                // 每次执行之后,将结果保存到结果集当中
                react {
                    candidates << it
                }
            }
        }
        actor 10
        actor 30
        actor 20
        actor.join()
    }

    /**
     * 达到某个条件再执行的actor
     */
    @Test
    void conditionalAndLoop2() {

        def actor = Actors.actor {
            def candidates = []
            final Closure printResult = { -> println "Reached best offer - ${candidates.max()}" }
            // 达到条件之后才执行
            loop({ -> candidates.max() < 30 }, printResult) {
                react {
                    candidates << it
                }
            }

            // 前一个loop中止之后,无法再启动另一个 loop会报一个java.lang.AssertionError错误,但是可以使用react{}方法
            /*loop {
                println it
            }*/

            react {
                println it
            }
        }

        actor 10
        actor 31
        actor 50
        actor 20

        actor.join()
    }

    public static void main(String[] args)
    {
        def test = new ActorsTest()
        //test.sendTest1()
        //test.sendTest2()
        //test.sendTest3()
        //test.sendReplies1()
    }
}
