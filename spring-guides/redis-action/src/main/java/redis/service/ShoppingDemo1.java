package redis.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * Created by LuoLiBing on 16/3/18.
 */
public class ShoppingDemo1 {

    public void loginCookies() {
        System.out.println("-------loginCookies--------");
        String token = UUID.randomUUID().toString();
        Jedis conn = getConn();
        updateToken(conn, token, "user");
    }

    private void updateToken(Jedis conn, String token, String user) {
        long timestamp = System.currentTimeMillis() / 1000;
        // hash记录登录信息
        conn.hset("login:", token, user);
        // zset记录登录信息
        conn.zadd("recent:", timestamp, token);
    }

    private Jedis getConn() {
        Jedis conn = new Jedis("localhost");
        JedisPool pool = new JedisPool();
        conn.select(15);
        return conn;
    }
}
