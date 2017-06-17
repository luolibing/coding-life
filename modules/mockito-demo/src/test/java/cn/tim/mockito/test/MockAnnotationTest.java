package cn.tim.mockito.test;

import cn.tim.mockito.s1.Car;
import cn.tim.mockito.s1.Process1;
import cn.tim.mockito.s1.Process2;
import cn.tim.mockito.s1.Process3;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * User: luolibing
 * Date: 2017/4/28 11:39
 * Email: 397911353@qq.com
 */
@RunWith(MockitoJUnitRunner.class)
public class MockAnnotationTest {

    @Mock
    private Process1 process1;

    @Mock
    private Process3 process3;

    private cn.tim.mockito.s1.Process2 process2 = new Process2();

    @Before
    public void mockInit() {
        when(process1.createBaseCar()).thenReturn(Car.createCar());
        when(process1.setSize(null, -1))
                .thenThrow(new RuntimeException("为空，或者小于0"))
                .thenAnswer(invocation -> {
                    Car car = (Car) invocation.getArguments()[0];
                    Integer size = (Integer) invocation.getArguments()[1];
                    car.setSize(size);
                    return car;
                });
        // 返回默认值
        when(process3.colour(null))
                .thenReturn(new Car());
        // DSL风格，do ... when ..。 doReturn doThrow(class|obj) doAnswer, doNothing() doCallRealMethod
        doThrow(new RuntimeException("")).when(process1).reset();

        // 调用真正的方法，而不是mock方法，当***时候
        doCallRealMethod().when(process3).colour(null);
    }

    @Test
    public void test1() {
        Car car = process1.createBaseCar();
        process2.modules(car);
        System.out.println(car);
        process3.colour(car);
    }

    @Test
    public void test2() {
        Car car = process1.createBaseCar();
        process1.setSize(car, 100);
        process2.modules(car);
        process3.colour(car);
    }

    @Test
    public void test3() {
        Car car = process3.colour(null);
        System.out.println(car);
    }

    @Test
    public void test4() {
        Car colour = process3.colour(null);
        System.out.println(colour);
    }

    @Test
    public void test5() {
        new HashSet<>().addAll(Collections.emptyList());
        System.out.println(null instanceof Integer);
    }
}
