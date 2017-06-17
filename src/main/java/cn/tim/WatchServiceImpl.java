package cn.tim;

import java.io.IOException;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

/**
 * Created by LuoLiBing on 15/8/1.
 */
public class WatchServiceImpl implements WatchService {
    public void close() throws IOException {

    }

    public WatchKey poll() {
        return null;
    }

    public WatchKey poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    public WatchKey take() throws InterruptedException {
        return null;
    }
}
