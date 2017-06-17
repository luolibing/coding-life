package cn.tim.phonenumber.simple;

import org.junit.Test;

import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Created by LuoLiBing on 16/8/26.
 */
public class PhoneNumberTest {

    @Test
    public void test1() {
        Map<Integer, Integer> counter =
                new PhoneNumberUniquer(Paths.get("/share/phonenumber/99.txt"), new Semaphore(1))
                        .counting()
                        .getTop50()
                        .getCounter();
        counter.forEach((key, val) -> {
            System.out.println(key + ":" + val);
        });
    }

    @Test
    public void test2() {
        PhoneNumberHandler phoneNumberHandler = new PhoneNumberHandler();
        phoneNumberHandler.handle();
        phoneNumberHandler.getPhoneNumberUniquerList().forEach( phoneNumberUniquer ->
                System.out.println(phoneNumberUniquer.getCounter())
        );
    }
}
