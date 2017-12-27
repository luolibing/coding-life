package cn.tim.restful;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: luolibing
 * Date: 2017/12/27 11:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RestfulApplicationTest {

    @Test
    public void test() {
        System.out.println("aaa");
    }
}
