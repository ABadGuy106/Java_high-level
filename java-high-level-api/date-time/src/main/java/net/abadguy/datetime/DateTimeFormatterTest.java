package net.abadguy.datetime;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeFormatter:格式化或解析日期、时间
 */
public class DateTimeFormatterTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SS");
        String format = dateTimeFormatter.format(now);
        System.out.println(format);
    }
}
