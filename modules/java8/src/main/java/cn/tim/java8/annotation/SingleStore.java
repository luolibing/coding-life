package cn.tim.java8.annotation;

/**
 * User: luolibing
 * Date: 2017/5/15 9:49
 */
public class SingleStore {

    @Singles(
            {
                    @Single("aaaa"),
                    @Single("bbbb")
            }
    )
    public String result() {
        return null;
    }
}
