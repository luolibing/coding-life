package cn.tim.phonenumber.simple;

import cn.tim.phonenumber.generate.NumberBookReader;
import cn.tim.phonenumber.generate.SortMapUtils;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * Created by LuoLiBing on 16/8/26.
 */
public class PhoneNumberUniquer implements Runnable {

    private Path path;

    private Map<Integer, Integer> counter = new HashMap<>();

    private final Semaphore semaphore;

    public PhoneNumberUniquer(Path path, Semaphore semaphore) {
        this.path = path;
        this.semaphore = semaphore;
    }

    public PhoneNumberUniquer counting() {
        NumberBookReader.readData(path,
                i -> counter.merge(i, 1, (oldValue, newValue) -> oldValue + 1));
        return this;
    }

    public PhoneNumberUniquer getTop50() {
        counter = SortMapUtils.sortByValue(counter,
                (v1, v2) -> {
                    if(v1 > v2) {
                        return -1;
                    } else if(Objects.equals(v1, v2)) {
                        return 0;
                    } else {
                        return 1;
                    }
                }, 50);
        return this;
    }

    public Map<Integer, Integer> getCounter() {
        return counter;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            counting().getTop50();
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public PhoneNumberUniquer start() {
        new Thread(this).start();
        return this;
    }
}
