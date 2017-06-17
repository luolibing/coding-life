package cn.tim.springsrc.context.i18;

import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by luolibing on 2017/6/5.
 */
public class LocaleTest {

    /**
     * 国际化也称为"本地化信息"，一般需要两个条件才能确定一个本地化信息，分别是"语言类型"和"国家/地区类型".
     */
    @Test
    public void locate() throws ParseException {
        Locale l1 = new Locale("zh", "CN");
        Locale l2 = new Locale("zh");
        // 等同于zh/CN
        Locale l3 = Locale.CHINA;
        // 等同于zh
        Locale l4 = Locale.CHINESE;
        // 本地系统默认
        Locale l5 = Locale.getDefault();

        String number = NumberFormat.getIntegerInstance(Locale.CANADA).format(10.00d);
        System.out.println("number = " + number);

        new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2017-08-08");


        // 格式化字符串
        String pattern1 = "{0},您好！你于{1}在工商银行存入{2}元。";
        String pattern2 = "At {1,time,short} on{1,date,long},{0} paid {2,number,currency}.";

        // 用于替换的参数
        Object params = new Object[]{"John", new GregorianCalendar().getTime(), 1.0E3};

        // 本地默认的格式化
        String msg1 = MessageFormat.format(pattern1, params);

        // 指定本地化信息的格式化
        MessageFormat mf = new MessageFormat(pattern2, Locale.US);
        String msg2 = mf.format(params);
        System.out.println(msg1);
        System.out.println(msg2);
    }

    @Test
    public void i18() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context/i18.xml");
        Object[] params = new Object[]{"John", new GregorianCalendar().getTime()};
        //String msg1 = context.getMessage("test", params, Locale.US);
        String msg2 = context.getMessage("test", params, Locale.CHINA);
        //System.out.println(msg1);
        System.out.println(msg2);

        MessageSource bean = context.getBean(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
        System.out.println(bean);
    }
}
