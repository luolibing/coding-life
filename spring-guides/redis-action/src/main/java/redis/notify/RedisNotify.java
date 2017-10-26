package redis.notify;

/**
 *
 *
 * User: luolibing
 * Date: 2017/10/16 11:00
 */
public class RedisNotify {

    /**
     * 开启通知： notify-keyspace-events "AKE"
     * 键空间通知：针对键为主体进行监听
     * subscribe __keyspace@0__:message (__keyspace@<dbid>__:<key>)
     *
     * 键事件通知：针对命令为主体进行监听
     * subscribe __keyevent@0__:del
     *
     * 执行del message的时候会发送两个publish通知，等同于：
     * PUBLISH __keyspace@0__:message del
     * PUBLISH __keyevent@0__:del message
     */
    public void keyspace() {

    }

    /**
     * save 900 1
     * save 300 10
     * save 60 100000
     * save 秒数 修改次数
     * 自动bgsave的条件，满足一个即可，900秒内至少一次更改，300秒内10次，
     */
    public void bgsave() {

    }
}
