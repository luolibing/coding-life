package cn.tim.springsrc.ioc.replace;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by luolibing on 2017/4/12.
 */
public class WorkerReplacer implements MethodReplacer {
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("替换业务");
        int[] arr = (int[]) args[0];
        Arrays.sort(arr);
        System.out.println("新的业务处理方式" + Arrays.toString(arr));
        return null;
    }
}
