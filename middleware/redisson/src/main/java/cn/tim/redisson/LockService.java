package cn.tim.redisson;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

/**
 * User: luolibing
 * Date: 2017/8/30 13:57
 */
@Service
public class LockService {

    private RedissonClient redisson;

    public LockService() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setDatabase(1);
        redisson = Redisson.create(config);
    }


    /**
     * 序列
     * @param name
     * @return
     */
    public Long nextId(String name) {
        RAtomicLong atomicId = redisson.getAtomicLong(name);
        return atomicId.incrementAndGet();
    }

    public void gLockExecute(String key) throws InterruptedException {
        RLock lock = redisson.getLock(key);

        try {
            lock.lock();
            System.out.println("execute " + Thread.currentThread().getId());
            Thread.sleep(5000);
            System.out.println("execute " + Thread.currentThread().getId() + " finished");
        } finally {
            lock.unlock();
        }
    }
}
