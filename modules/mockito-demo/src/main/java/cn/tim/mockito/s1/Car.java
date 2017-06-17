package cn.tim.mockito.s1;

import java.util.concurrent.atomic.AtomicLong;

/**
 * User: luolibing
 * Date: 2017/4/28 10:45
 * Email: 397911353@qq.com
 */
public class Car {

    private Long id;

    private String name;

    private CarStatusEnum status;

    private Integer size;

    private static AtomicLong counter = new AtomicLong();

    public static Car createCar() {
        Car car = new Car();
        car.setId(counter.incrementAndGet());
        car.setName("测试");
        car.turnBaseStatus();
        car.setSize(100);
        return car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void turnBaseStatus() {
        if(status != null) {
            throw new RuntimeException("非法的状态切换");
        }
        status = CarStatusEnum.BASE;
    }

    public void turnColorStatus() {
        if(status != CarStatusEnum.MODEL) {
            throw new RuntimeException("非法的状态切换");
        }
        status = CarStatusEnum.COLOR;
    }

    public void turnModelStatus() {
        if(status != CarStatusEnum.BASE) {
            throw new RuntimeException("非法的状态切换");
        }
        status = CarStatusEnum.MODEL;
    }

    public CarStatusEnum getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
