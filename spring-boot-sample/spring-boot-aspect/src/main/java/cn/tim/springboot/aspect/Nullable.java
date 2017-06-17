package cn.tim.springboot.aspect;

/**
 * User: luolibing
 * Date: 2017/6/1 17:21
 */
public class Nullable implements Defaultable<Nullable> {

    public final static Nullable DEFAULT = new Nullable();

    @Override
    public Nullable defaultValue() {
        return DEFAULT;
    }
}
