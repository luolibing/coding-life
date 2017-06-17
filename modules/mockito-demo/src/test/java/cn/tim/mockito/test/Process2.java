package cn.tim.mockito.test;

import cn.tim.mockito.s1.Car;
import cn.tim.mockito.s1.CarStatusEnum;
import cn.tim.mockito.s1.Process1;
import cn.tim.mockito.s1.Process3;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.*;

/**
 * User: luolibing
 * Date: 2017/4/28 10:56
 * Email: 397911353@qq.com
 */
public class Process2 {

    private cn.tim.mockito.s1.Process2 process2 = new cn.tim.mockito.s1.Process2();

    @Test
    public void modulesTest() {
        // mock第一个工序的产出Process1
        Process1 process1 = mock(Process1.class);
        when(process1.createBaseCar()).thenReturn(Car.createCar());
        Car car = process1.createBaseCar();
        System.out.println(car);

        // 要测试的主体，使用mock测试上下游系统未写好的接口
        process2.modules(car);

        Process3 process3 = mock(Process3.class);
        when(process3.colour(argThat(c -> c != null && c.getStatus() == CarStatusEnum.MODEL)))
                .thenAnswer(invocation -> {
                    Car c = (Car)invocation.getArguments()[0];
                    c.turnColorStatus();
                    return c;
                });
        when(process3.colour(argThat(c -> c.getStatus() != CarStatusEnum.MODEL)))
                .thenThrow(new RuntimeException("错误的状态"));
        process3.colour(car);
        System.out.println(car);
    }

    private AtomicLong counter = new AtomicLong();

    @Test
    public void argMatch2() {
        List<String> mockList = mock(List.class);
        // add()调用3次
        verify(mockList, times(3)).add("aaa");
        System.out.println(mockList);
    }

    @Test
    public void exception() {
        List<String> mockList = mock(List.class);
        // 当调用某方法时抛出异常
        doThrow(new RuntimeException()).when(mockList).clear();
        mockList.clear();
    }
}
