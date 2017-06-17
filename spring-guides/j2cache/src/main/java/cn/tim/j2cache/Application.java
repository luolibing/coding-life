package cn.tim.j2cache;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;

/**
 * Created by LuoLiBing on 15/12/11.
 */
public class Application {

    public static void main(String[] args) {
        CacheChannel cacheChannel = J2Cache.getChannel();
        cacheChannel.set("cache1", "key", "luolibing");
        System.out.println(cacheChannel.get("cache1", "key"));
    }

}
