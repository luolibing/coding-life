package cn.tim.mockito.test;

import org.junit.Test;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/**
 * mockito框架：一个mock测试框架
 * http://site.mockito.org/
 * User: luolibing
 * Date: 2017/4/27 15:05
 * Email: 397911353@qq.com
 */
public class TutorTest {

    @Test
    public void helloWorld() {
        // 为什么不能add
        List<String> list = mock(List.class);
        list.add("aaa");
        // verify方法的作用
        List<String> verify = verify(list);
        verify.add("aaa");
    }

    /**
     * 打桩
     */
    @Test
    public void stub() {
        List<String> list = mock(List.class);
        when(list.get(0)).thenReturn("first");
        when(list.get(1)).thenReturn("second");
        // 当get(2)的时候抛出RuntimeException
        when(list.get(2)).thenThrow(new RuntimeException());
        Assert.isTrue(Objects.equals(list.get(0), "first"));
        Assert.isTrue(Objects.equals(list.get(1), "second"));
        try {
            list.get(2);
        } catch (RuntimeException e) {

        }
        // 没有打桩
//        Assert.isNull(list.get(2));

        // anyInt()匹配任何输入条件
        when(list.get(anyInt())).thenReturn("element");
        Assert.notNull(list.get(999));

        // 自定义参数匹配返回值
        when(list.contains(argThat((arg) -> {
            if(Objects.isNull(arg)) {
                return false;
            }
            return arg instanceof String;
        }))).thenReturn(true);

        Assert.isTrue(list.contains("aaa"));
        Assert.isTrue(!list.contains(1));

        List mockList = mock(List.class);
        // 验证是否正确
        when(mockList.get(anyInt())).thenReturn("element");
        // anyInt()并不是一个随机数, 这个地方验证会报错
        // verify(mockList).get(anyInt());
        verify(mockList).add(argThat(s -> s != null && s.toString().length() > 5));
        verify(mockList).add("aaaaaa");
    }

    @Test
    public void argMatch() {
        LinkedList<String> mock = mock(LinkedList.class);
        verify(mock).add(argThat(s -> s.length() > 5));
        mock.add("aaaaaa");
    }
}
