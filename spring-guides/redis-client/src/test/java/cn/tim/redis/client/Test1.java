package cn.tim.redis.client;

import cn.tim.redis.Application;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by LuoLiBing on 15/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class Test1 {

    private Jedis jedis;

    @Before
    public void before() {
        jedis = new Jedis("localhost");
        System.out.println("连接成功！");
    }

    @Test
    public void ping() throws InterruptedException {
        for(int i=0; i<10; i++) {
            Thread.sleep(1000);
            System.out.println("server is running:" + jedis.ping());
        }
    }

    @Test
    public void stringTest() {
        String value = "luolibing";
        String key = "name";
        jedis.set(key, value);
        Assert.assertEquals(jedis.get(key), value);
    }

    @Test
    public void listTest() {
        String key = "list";
        jedis.lpush(key, "luolibing");
        jedis.lpush(key, "liuxiaoling");
        jedis.lpush(key, "luominghao");
        List<String> list = jedis.lrange(key, 0, 10);
        for(String val: list) {
            System.out.println(val);
        }
    }

    @Test
    public void keys() {
        Set<String> keys = jedis.keys("*");
        for(String key: keys) {
            System.out.println(key);
        }
    }

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOps;

    @Resource(name = "redisTemplate")
    private ListOperations<String, Map<String, String>> listHashMapOps;

    @Resource(name = "redisTemplate")
    private SetOperations<String, Map<String, String>> hashSetOps;

    @Test
    public void list() {
        listOps.leftPush("list", "luolibing");
        final List<String> list = listOps.range("list", 0, 10);
        for(String val: list ){
            System.out.println(val);
        }
    }

    @Test
    public void set() {
        setOps.add("set", "luolibing");
        final Set<String> set = setOps.members("set");
        for(String val: set) {
            System.out.println(val);
        }
    }

    @Test
    public void keyCheck() {
        //System.out.println(setOps.size());
    }

    @Test
    public void keySetAdds() {
        List<String> list = new ArrayList<>();
        list.add("luolibing");
        list.add("liuxiaoling");
        list.add("luominghao");
        list.add("ll");
    }

    @Test
    public void hashList() {
        Map<String, String> resultMap1 = new HashMap<>();
        resultMap1.put("luolibing", "cool");
        resultMap1.put("liuxiaoling", "nice");
        listHashMapOps.leftPush("base", resultMap1);
        resultMap1 = new HashMap<>();
        resultMap1.put("luolibing", "cool");
        resultMap1.put("liuxiaoling", "nice");
        listHashMapOps.leftPush("base", resultMap1);
    }

    @Test
    public void hashListGet() {
        List<Map<String, String>> base = listHashMapOps.range("base", 0, 100000);
        for(Map<String, String> beanMap: base) {
            System.out.println(beanMap.get("luolibing"));
            System.out.println(beanMap.get("liuxiaoling"));
        }
    }

    @Test
    public void hashListUpdate() {
    }

    @Test
    public void hashSet() {
        Map<String, String> resultMap1 = new HashMap<>();
        resultMap1.put("luolibing", "cool");
        resultMap1.put("liuxiaoling", "nice");
        hashSetOps.add("extend", resultMap1);
        resultMap1 = new HashMap<>();
        resultMap1.put("luolibing", "cool");
        resultMap1.put("liuxiaoling", "nice");
        hashSetOps.add("extend", resultMap1);
    }

    @Test
    public void hashSetGet() {
        final Set<Map<String, String>> extend = hashSetOps.members("extend");
        for(Map<String, String> beanMap: extend) {

        }
    }
}
