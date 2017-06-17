package cn.tim.java8.time;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Predicate;

/**
 * Created by LuoLiBing on 16/7/13.
 */
public class LocalDateExercise {

    @Test
    public void test1() {
        // Period日期距离
        LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), 1, 1).plus(Period.ofDays(255));
        System.out.println(localDate);
    }

    @Test
    public void test2() {
        LocalDate startDate = LocalDate.of(2000, 2, 29);
        System.out.println(startDate.plusYears(1));
        System.out.println(startDate.plusYears(4));

        System.out.println(startDate.plusYears(1).plusYears(1).plusYears(1).plusYears(1));
    }

    @Test
    public void test3() {
        System.out.println(LocalDate.now().with(next(w -> w.getDayOfWeek().getValue() < 6)));
    }

    public TemporalAdjuster next(Predicate<LocalDate> predicate) {
//        return w -> {
//            LocalDate d = (LocalDate) w;
//            do {
//                d = d.plusDays(1);
//            } while (!predicate.test(d));
//            return d;
//        };
        return TemporalAdjusters.ofDateAdjuster((d) -> {
            do {
                d = d.plusDays(1);
            } while (!predicate.test(d));
            return d;
        });
    }

    @Test
    public void test4() {
        cal(2016, 7);
    }
    
    private void cal(int year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate preSunday = startOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        do {
            System.out.print(getDay(preSunday, month));
            preSunday = preSunday.plusDays(1);
            if(preSunday.getDayOfWeek() == DayOfWeek.SUNDAY) {
                System.out.println();
            }
        } while (preSunday.getMonth().getValue() != startOfMonth.plusMonths(1).getMonthValue());
    }
    
    private String getDay(LocalDate localDate, int month) {
        if(localDate.getMonthValue() != month) {
            return "    ";
        }
        return localDate.getDayOfMonth() < 10? localDate.getDayOfMonth() + "   " : localDate.getDayOfMonth() + "  ";
    }

    @Test
    public void test5() {
        System.out.println(LocalDate.of(1987, 8, 8).until(LocalDate.now(), ChronoUnit.DAYS));
    }

    @Test
    public void test6() {
        // 21世纪第一个周五
        LocalDate localDate = LocalDate.of(2000, 1, 1).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate endLocalDate = LocalDate.of(2100, 1, 1);
        while(localDate.isBefore(endLocalDate)) {
            System.out.println(localDate);
            localDate = localDate.plusWeeks(1);
        }
    }

    @Test
    public void test7() {
        TimeInterval first = new TimeInterval(LocalDateTime.of(2016, 1, 20, 10, 20, 0), LocalDateTime.of(2016, 1, 20, 10, 40, 0));
        TimeInterval second = new TimeInterval(LocalDateTime.of(2016, 1, 20, 10, 10, 0), LocalDateTime.of(2016, 1, 20, 10, 20, 0));
        System.out.println(first.overlap(second));
    }

    class TimeInterval {
        LocalDateTime startTime;
        LocalDateTime endTime;
        public TimeInterval(LocalDateTime startTime, LocalDateTime endTime) {
            if(startTime == null || endTime == null || startTime.isAfter(endTime) || startTime.equals(endTime)) {
                throw new RuntimeException();
            }
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public boolean overlap(TimeInterval timeInterval) {
            return (timeInterval.getStartTime().isAfter(startTime) && timeInterval.getStartTime().isBefore(endTime))
                    || (timeInterval.getEndTime().isAfter(startTime) && timeInterval.getEndTime().isBefore(endTime));
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }
    }

    @Test
    public void test8() {
        Instant instant = Instant.now();
        ZoneId.getAvailableZoneIds()
                .stream()
                .map(id -> instant.atZone(ZoneId.of(id)).getOffset())
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void test9() {
        Instant instant = Instant.now();
        ZoneId.getAvailableZoneIds()
                .stream()
                .map(id -> instant.atZone(ZoneId.of(id)).getOffset())
                .filter(offset -> offset.getTotalSeconds() % 3600 == 0)
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void test10() {
        ZonedDateTime newYork = ZonedDateTime.of(LocalDate.now(), LocalTime.of(3, 5), ZoneId.of("America/New_York"));
        newYork = newYork.plus(Duration.ofMinutes(10 * 60 + 50));
        ZonedDateTime europe = newYork.withZoneSameInstant(ZoneId.of("Europe/Zurich"));
        System.out.println(europe);
    }

    @Test
    public void test11() {
        // ZoneId.getAvailableZoneIds().forEach(System.out::println);
        Duration duration = getDuration(LocalTime.of(14, 5), LocalTime.of(16, 40), ZoneId.of("America/New_York"), ZoneId.of("Europe/Zurich"));
        System.out.println(duration.toHours());
    }

    public Duration getDuration(LocalTime startTime,
                                LocalTime endTime,
                                ZoneId startZoneId,
                                ZoneId endZoneId) {
        Instant startInstant = ZonedDateTime.of(
                LocalDate.now(),
                startTime,
                startZoneId
        ).toInstant();
        Instant endInstant = ZonedDateTime.of(
                LocalDate.now(),
                endTime,
                endZoneId
        ).toInstant();
        return Duration.between(startInstant, endInstant);
    }
}
