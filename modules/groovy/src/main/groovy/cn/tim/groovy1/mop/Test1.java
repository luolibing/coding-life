package cn.tim.groovy1.mop;

import org.junit.Test;

/**
 * Created by LuoLiBing on 15/10/12.
 */
public class Test1 {
    @Test
    public void userGroovyClass() {
        UseTest useTest = new UseTest();
        useTest.useClouse(new Object() {
            public String call() {
                System.out.println("exec clouse!");
                return "exec clouse";
            }
        });
    }
}
