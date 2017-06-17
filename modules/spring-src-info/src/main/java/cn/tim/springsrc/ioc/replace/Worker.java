package cn.tim.springsrc.ioc.replace;

import java.util.Arrays;

/**
 * Created by luolibing on 2017/4/12.
 */
public class Worker {

    public void execute(int[] arr) {
        System.out.println("MethodChange execute()方式" + Arrays.toString(arr));
    }

}
