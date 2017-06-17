package cn.tim.springsrc.context.convert;

import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Date;

/**
 * Created by luolibing on 2017/6/7.
 */
public class Test {

    @org.junit.Test
    public void testStringTo() {
        // 在spring中可以配置ConversionService来控制对象的转换，添加converter即可
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new String2DateConverter());

        String date = "2017-06-07";
        Date convert = conversionService.convert(date, Date.class);
        System.out.println(convert);
    }

}
