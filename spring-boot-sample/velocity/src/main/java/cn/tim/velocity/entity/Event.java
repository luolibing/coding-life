package cn.tim.velocity.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * Created by luolibing on 2017/4/6.
 */
@Data
public class Event implements Serializable {
    private static Random rand = new Random(47);
    private static long counter = 0;
    private Long id = counter++;
    private String eventType = EventType.randEventType().eventDesc;
    private String title = "操作";
    private String content = rand.nextBoolean() ? "操作内容" : null;
    private Date createDate = new Date();

    public int size(String[] array) {
        return array.length;
    }

    public void execute() {
        System.out.println("execute " + toString());
    }

    public void setArray(String...args) {
        System.out.println("array = " + Arrays.toString(args));
    }
}
