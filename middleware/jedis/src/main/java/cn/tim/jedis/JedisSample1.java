package cn.tim.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * User: luolibing
 * Date: 2018/3/14 10:36
 */
public class JedisSample1 {

    @Test
    public void jedis() {
        Jedis jedis = new Jedis("localhost", 10086);
        jedis.select(0);
        jedis.dbSize();
        ScanResult<String> scanResult = jedis.scan("0");
        List<String> keys = scanResult.getResult();
        System.out.println("keys: " + keys);
        for(String key : keys) {
            try {
                String json = jedis.get(key);
                System.out.println(key + ": " + json);
            } catch(Exception e) {

            }
        }
        jedis.close();
    }
}
