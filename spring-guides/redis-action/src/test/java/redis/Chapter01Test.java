package redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.service.Chapter01;

import java.util.Random;

/**
 * Created by LuoLiBing on 16/2/29.
 */
public class Chapter01Test {

    Chapter01 chapter01;

    @Before
    public void before() {
        chapter01 = new Chapter01();
    }

    @Test
    public void postArticle() {
        Jedis conn = new Jedis();
        conn.select(10);
        int random = new Random().nextInt(100);
        //chapter01.postArticle("admin" + random, "测试章节" + random, "http://www.baidu.com/1.html");
    }
}
