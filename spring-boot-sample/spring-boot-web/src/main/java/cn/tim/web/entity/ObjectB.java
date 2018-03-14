package cn.tim.web.entity;

/**
 * User: luolibing
 * Date: 2018/3/14 12:55
 */
public class ObjectB {

    private ObjectA objectA;

    public ObjectB(ObjectA objectA) {
        this.objectA = objectA;
    }

    public ObjectA getObjectA() {
        return objectA;
    }

    public void setObjectA(ObjectA objectA) {
        this.objectA = objectA;
    }
}
