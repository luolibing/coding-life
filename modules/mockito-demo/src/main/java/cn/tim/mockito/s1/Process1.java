package cn.tim.mockito.s1;

import java.util.concurrent.atomic.AtomicLong;

/**
 * User: luolibing
 * Date: 2017/4/28 10:45
 * Email: 397911353@qq.com
 */
public class Process1 {

    private static AtomicLong counter = new AtomicLong();

    public Car createBaseCar() {
        Car car = new Car();
        car.setId(counter.incrementAndGet());
        car.setName("汽车" + car.getId());
        car.turnBaseStatus();
        return car;
    }

    public Car setSize(Car car, Integer i) {
        car.setSize(i);
        return car;
    }

    public void reset() {

    }
}
