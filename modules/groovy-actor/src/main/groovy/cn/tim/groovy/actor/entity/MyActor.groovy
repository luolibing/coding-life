package cn.tim.groovy.actor.entity

import groovyx.gpars.actor.Actors

/**
 * Created by LuoLiBing on 15/12/26.
 */
//Actors.defaultActorPGroup.resize 1
//
//def actor = Actors.actor {
//    println receive()
//}.start()
//
//actor.send 'hello'


def start() {
    def actor = Actors.actor({
        receive  {
            println it
        }
    })
    actor.react {}
    println "start"
    actor.start()
    actor.send "Hello World"
}