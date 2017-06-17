package cn.tim.groovy1.mop1

/**
 * Created by LuoLiBing on 15/10/17.
 */
@Mixin([Teacher, Friend])
class Person {
    String firstName
    String lastName
    String getName() { "$firstName $lastName"}
}
