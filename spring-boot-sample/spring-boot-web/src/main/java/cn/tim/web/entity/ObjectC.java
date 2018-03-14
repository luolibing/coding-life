package cn.tim.web.entity;

/**
 * User: luolibing
 * Date: 2018/3/14 12:56
 */
public class ObjectC {

    private ObjectB objectB;

    public ObjectC(ObjectB objectB) {
        this.objectB = objectB;
    }

    public ObjectB getObjectB() {
        return objectB;
    }

    public void setObjectB(ObjectB objectB) {
        this.objectB = objectB;
    }
}
