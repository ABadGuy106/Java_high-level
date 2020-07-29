package net.abadguy.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JDK8DateTimeTest {

    public static void main(String[] args) {
        //now():获取当前的日期、时间、日期+时间
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDate);
        System.out.println(localTime);
        System.out.println(localDateTime);

        //of(): 设置指定的年、月、日、时、分、秒
        LocalDateTime of = LocalDateTime.of(2020, 7, 9, 23, 12, 34);
        System.out.println(of);

        //getXxx()
        int dayOfMonth = localDateTime.getDayOfMonth();
        System.out.println(dayOfMonth);
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        System.out.println(dayOfWeek);
        int dayOfYear = localDateTime.getDayOfYear();
        System.out.println(dayOfYear);
        int monthValue = localDateTime.getMonthValue();
        System.out.println(monthValue);
        int minute = localDateTime.getMinute();
        System.out.println(minute);

        //withXxx()
        LocalDate localDate1 = localDate.withDayOfMonth(28);
        System.out.println(localDate);
        System.out.println(localDate1);


        LocalDate localDate2 = localDate.plusDays(22);
        System.out.println(localDate);
        System.out.println(localDate2);
        LocalDate localDate3 = localDate.plusMonths(8);
        System.out.println(localDate);
        System.out.println(localDate3);

        LocalDate localDate4 = localDate.minusDays(3);
        System.out.println(localDate);
        System.out.println(localDate4);

    }
}
