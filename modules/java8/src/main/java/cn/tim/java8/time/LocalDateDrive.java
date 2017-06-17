package cn.tim.java8.time;

import org.junit.Test;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by LuoLiBing on 16/7/12.
 * 所有java.time对象都是不可变的
 * 一个瞬时对象Instant是时间线上的一个点与Date类似
 * 在java事件中,每天都是86400秒(即没有闰秒)
 * 持续事件(Duration)是两个瞬间之间的时间
 * LocalDateTime没有时区信息
 * TemporalAdjuster的方法可以处理常用的日历计算
 * ZonedDateTime是指定时区中某一个时间点类似于Gregorian Calendar
 * 当处理带时区的时间时,请使用时段Period,而非Duration,以便将夏令时考虑在内
 * 使用DateTimeFormatter来格式化和解析日期和时间
 *
 */
public class LocalDateDrive {

    /**
     * 时间线
     * 1 每天都有86400秒
     * 2 每天正午与官方时间准确匹配
     * 3 其他时间也要以一种精确定义的方式与其紧密匹配
     *
     * 一个Instant对象表示时间轴上的一个点,原点被规定为1970年1月1日午夜
     * Instant和Duration类都是不可变的,他们所有的方法都返回一个新的实例new Instant(seconds, nanosSeconds)
     */
    @Test
    public void instant() throws InterruptedException {
        // 秒
        System.out.println(Instant.now().getEpochSecond());
        // 毫秒
        System.out.println(Instant.now().toEpochMilli());
        // long溢出
        //System.out.println(Instant.MAX.toEpochMilli());

        Instant instant1 = Instant.now();
        Thread.sleep(1000);
        Instant instant2 = Instant.now();
        // 比较两个时间点使用equals和compareTo
//        assert instant1.equals(instant2);
//        assert instant1.compareTo(instant2) == 0;
        // 获取两个时间点的时间距离毫秒 duration.toNanos,toMillis,toSeconds,toMinutes,toHours,toDays
        // Duration对象在内部存储上需要多个long值,秒由一个long值保存,而纳秒的值由另一个int保存toNanos纳秒
        final Duration duration = Duration.between(instant1, instant2);
        long millis = duration.toMillis();
        System.out.println(millis);

        // 判断是否为0或者负数
        duration.isZero(); duration.isNegative();
        // duration.toNanos * 10 <duration1
        duration.multipliedBy(10).minus(duration).isNegative();
    }

    /**
     * 人类时间包括本地日期和时间和带时区的时间
     */
    @Test
    public void localDate() {
        // LocalDate\LocalDateTime不带时区信息,下面这种方式是一个错误的方式,需要使用另外一种方式
        // LocalDate.from(new Date().toInstant());
        // LocalDateTime.from(new Date().toInstant());
        // LocalDate.from(Instant.now()).atStartOfDay(ZoneId.systemDefault());
        // 本地时间需要携带时区信息,使用默认的时区
        LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());


        // LocalDate是一个带有年份\月份\当月天数的日期, 月份天数,可以和实际生活中一样从1开始 LocalDate.of() LocalDate.now()
        LocalDate.now();
        LocalDate start1 = LocalDate.of(2016, 6, 14);
        LocalDate start2 = LocalDate.of(2015, Month.APRIL, 14);
        // 获取两个日期之间的until
        Period until = start2.until(start1);
        System.out.println(until.getDays());

        // isBefore   isAfter 判断一个日期的前后
        start1.isBefore(start2);

        // isLeapYear 判断是闰年还是平年
        start1.isLeapYear();
    }

    /**
     * 日期运算
     */
    @Test
    public void plus() {
        // 日期计算
        final LocalDate localDate = LocalDate.of(2015, 1, 1).plusDays(200);
        System.out.println(localDate);

        // Duration是时间的距离,Period是日期的距离时段,表示一段逝去的年月日
        LocalDate nextBirthday = localDate.plus(Period.ofYears(1));
        System.out.println(nextBirthday);

        // 计算两个日期之间的差距
        final long until = LocalDate.of(2015, 1, 1).until(LocalDate.of(2016, 1, 1), ChronoUnit.DAYS);
        System.out.println(until);

        // 并不会直接对应到2月31 会返回这个月的最后一个天
        final LocalDate localDate1 = LocalDate.of(2015, 1, 31).plusMonths(1);
        System.out.println(localDate1);

        // 返回今天是星期几,分别从1 到 7 第一天为星期一
        System.out.println(LocalDate.now().getDayOfWeek().getValue());
    }

    /**
     * 日期校正器
     */
    @Test
    public void temporalAdjusters() {
        // 获取当前日期的下一个周五,如果今天是周五就返回今天
        System.out.println(LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY)));
        // 前一个周一
        System.out.println(LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY)));
        // 当前月第一个周五
        System.out.println(LocalDate.now().with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.MONDAY)));
        // 当前月最后一个周五
        System.out.println(LocalDate.now().with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY)));
        // 返回下一个工作日
        TemporalAdjuster NEXT_WORKDAY = w -> {
            LocalDate result = (LocalDate) w;
            do {
                result = result.plusDays(1);
            } while(result.getDayOfWeek().getValue() >= 6);
            return result;
        };
        System.out.println(LocalDate.now().with(NEXT_WORKDAY));
        // ofDateAdjuster(UnaryOperator<LocalDate>)
        TemporalAdjusters.ofDateAdjuster( w ->{
            // 使用一元操作符,可以避免强转
            return w;
        });
    }

    /**
     * 本地时间
     */
    @Test
    public void localTime() {
        // 获取本地时间
        System.out.println(LocalTime.now());
        System.out.println(LocalTime.of(22, 50));
        // 时间想加
        LocalTime wakeup = LocalTime.now().plusHours(8);
        System.out.println(wakeup);
        // 本地日期时间
        LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }

    /**
     * 带时区的人类时间
     */
    @Test
    public void zoneId() {
        // 获取所有可用时区列表
        ZoneId.getAvailableZoneIds().forEach(System.out::println);

        // 获取一个时区对象,并且获取当前对应时区的时间
        ZoneId asia = ZoneId.of("America/Los_Angeles");
        ZonedDateTime asiaTime = Instant.now().atZone(asia);
        System.out.println(asiaTime);

        // 将一个LocalDateTime转换为带时区的ZonedDateTime
        final ZonedDateTime zonedDateTime = LocalDateTime.now().atZone(asia);
        System.out.println(zonedDateTime);

        // 创建一个带时区的时间
        final ZonedDateTime of = ZonedDateTime.of(2016, 7, 16, 8, 20, 0, 0, ZoneId.of("Australia/Lindeman"));
        System.out.println(of);
        // 获取时间点
        of.toInstant();
        // 获取当前时间的UTC时间, UTC表示协调世界时,是格林威治皇家天文台标准时间
        ZonedDateTime utc = Instant.now().atZone(ZoneId.of("UTC"));
        System.out.println(utc);

        // 获取时区时间与UTC时间之间的时差
        zonedDateTime.getOffset().getTotalSeconds();
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        System.out.println(localZonedDateTime.getOffset());

        // 带时区时间返回本地时间
        System.out.println(localZonedDateTime.toLocalDate() + "" + localZonedDateTime.toLocalDateTime());

        // 进入夏令时,时钟拨快一小时,这个时候返回的是3:30
        final ZonedDateTime amb = ZonedDateTime.of(
                LocalDate.of(2013, 3, 31),
                LocalTime.of(2, 30),
                ZoneId.of("Europe/Berlin"));
        System.out.println(amb);

        // 夏令时结束,时钟拨慢一小时,这个时候返回的还是2,30,一个小时后,还是2:30,有两个一样的时间点
        final ZonedDateTime amb1 = ZonedDateTime.of(
                LocalDate.of(2013, 10, 27),
                LocalTime.of(2, 30),
                ZoneId.of("Europe/Berlin"));
        System.out.println(amb1);
        // 加一个小时后,时间并没有变,只是时间偏移量改变了
        System.out.println(amb1.plusHours(2));

        // 跨越夏令时,不能使用Duration时间距离,而应该使用日期距离
        System.out.println(amb.plus(Duration.ofDays(250)));
        System.out.println(amb.plus(Period.ofDays(250)));
    }

    /**
     * 日期时间转换
     */
    @Test
    public void dateTimeFormatter() {
        // DateTimeFormatter提供了许多常量DateTimeFormatter
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(localZonedDateTime));

        // 带语言环境的日期转换FormatStyle.SHORT:16-7-13,MEDIUM:2016-7-13,LONG:2016年6月13日,FULL:2016年7月13日 星期三
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        System.out.println(LocalDateTime.now().format(dateTimeFormatter));

        // 如果要改变其他的语言环境,可以使用withLocal()即可
        System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.CANADA).format(LocalDateTime.now()));

        // 将DateTimeFormatter转换为DateFormat
        final Format format = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).toFormat();
        System.out.println(format);

        DateTimeFormatter simpleDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateTimeFormatter.format(LocalDateTime.now()));

        // LocalDate可以使用静态方法parse可以使用ISO_LOCAL_DATE解析
        LocalDate localDate = LocalDate.parse("2016-06-13", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(localDate);

        // ZonedDateTime也可以使用静态方法parse转换
        ZonedDateTime apollolllauch = ZonedDateTime.parse("2016-09-09 03:30:00-0400", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxx"));
        System.out.println(apollolllauch);
    }

    @Test
    public void date() {
        // Instant和Date对应,在Date中间添加了new Date().toInstant()方法, Date.from(Instant)
        new Date().toInstant(); Date.from(Instant.now());
        // ZonedDateTime类似于GregorianCalendar类,同样有toZonedDateTime()方法和静态方法from(ZonedDateTime)
        new GregorianCalendar().toZonedDateTime(); GregorianCalendar.from(ZonedDateTime.now());
    }

    @Test
    public void between() {
        LocalDateTime start = LocalDateTime.of(2016,6,1,1, 1, 1);
        LocalDateTime to = LocalDateTime.of(2016,6,1,1, 1, 1);
        System.out.println(start.isBefore(to));
    }

    @Test
    public void getStartWithWeek() {
        LocalDate now = LocalDate.now();
        int value = now.getDayOfWeek().getValue();
        LocalDate from = now.minusDays(value - 1);
        LocalDate to = now.plusDays(7 - value);
        System.out.println(from);
        System.out.println(to);
    }


    @Test
    public void week() {
        LocalDate.now();
        System.out.println();
    }

    @Test
    public void quart() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date first = getFirstDayOfQuarter(date);
        Date last = getLastDayOfQuarter(date);
        System.out.println(df.format(first));
        System.out.println(df.format(last));
    }

    private static Date getFirstDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)/3 * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    private static Date getLastDayOfQuarter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)/3 * 3 + 2);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
}
