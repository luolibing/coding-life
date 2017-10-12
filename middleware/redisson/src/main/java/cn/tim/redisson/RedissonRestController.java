package cn.tim.redisson;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * User: luolibing
 * Date: 2017/8/30 14:08
 */
@RestController
@RequestMapping("/redisson")
public class RedissonRestController {

    @Autowired
    private LockService lockService;

    @GetMapping("/nextId/{key}")
    public Object nextId(@PathVariable String key) {
        return Collections.singletonMap("nextId", lockService.nextId(key));
    }

    @GetMapping("/gLock/{key}")
    public Object gLock(@PathVariable String key) throws InterruptedException {
        lockService.gLockExecute(key);
        return ResponseEntity.ok().build();
    }

    @Test
    public void t() {
        System.out.println(7 & 3);
    }
}
