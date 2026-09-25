package cn.topicstudy.jutil.basic.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {
    /**
     * 所有日期、时间都用该格式
     */
    public final static String DEFAULT_MASK_DATE = "yyyy-MM-dd";
    public final static String DEFAULT_MASK_TIME = "HH:mm:ss";
    public final static String DEFAULT_MASK_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public final static SimpleDateFormat dateDefaultFormat = new SimpleDateFormat(DEFAULT_MASK_DATE);
    public final static SimpleDateFormat timeDefaultFormat = new SimpleDateFormat(DEFAULT_MASK_TIME);
    public final static SimpleDateFormat dateDefaultTimeFormat = new SimpleDateFormat(DEFAULT_MASK_DATE_TIME);


    /**
     * Please use  LocalDateTime parse = LocalDateTime.parse(s, formatter);
     * LocalDate parse1 = LocalDate.parse(s, formatter);
     * LocalTime parse2 = LocalTime.parse(s, formatter);
     * 字符串转日期
     * 不报错，但是转换结果不正确
     * new SimpleDateFormat("yyyyMMdd").parse("2021-02-25");
     * <p>
     * 报错：java.text.ParseException
     * new SimpleDateFormat("yyyy-MM-dd").parse("20210225");
     * <p>
     * 报错
     * new SimpleDateFormat("yyyyMMdd").parse("2021#02#25");
     */
    @Deprecated
    public static Date stringToDate(String s, String mask) {
        try {
            if (mask.contains("-") ^ s.contains("-")) {
                throw new RuntimeException("mask and date string are not in the same format.");
            }
            SimpleDateFormat sdf = new SimpleDateFormat(mask);
            return sdf.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Please use DateTimeFormatter formatter = DateTimeFormatter.ofPattern(mask);
     * String s = LocalDateTime.now().format(formatter);
     * String s1 = LocalDate.now().format(formatter);
     * String s2 = LocalTime.now().format(formatter);
     *
     * @param d
     * @param mask
     * @return
     */
    @Deprecated
    public static String dateToString(Date d, String mask) {
        SimpleDateFormat sdf = new SimpleDateFormat(mask);
        return sdf.format(d);
    }

    /**
     * 给date加上seconds秒
     * TODO fix:超过int范围
     */
    @Deprecated
    public static Date addSeconds(Date date, long seconds) {
        return new Date(date.getTime() + seconds * 1000);
    }

    /**
     * 获取日期中的年
     */
    @Deprecated
    public static Integer getYear(Date d) {
        return d.getYear() + 1900;
    }

    /**
     * 获取日期中的月
     */
    @Deprecated
    public static Integer getMonth(Date d) {
        return d.getMonth() + 1;
    }

    /**
     * 获取日期中的日
     */
    @Deprecated
    public static Integer getDay(Date d) {
        return d.getDate();
    }

    /**
     * 当前时间
     */
    @Deprecated
    public static Date nowDate() {
        return new Date();
    }

    /**
     * 当前时间
     *
     * @param mask
     * @return String
     */
    @Deprecated
    public static String nowDateString(String mask) {
        return dateToString(nowDate(), mask);
    }


    public static String calculateTimeDifference(LocalDateTime start, LocalDateTime end) {
        Period period = Period.between(start.toLocalDate(), end.toLocalDate());

        long days = ChronoUnit.DAYS.between(start.toLocalDate().plus(period), end.toLocalDate());
        start = start.plus(period).plusDays(days);

        long hours = ChronoUnit.HOURS.between(start, end);
        start = start.plusHours(hours);

        long minutes = ChronoUnit.MINUTES.between(start, end);
        start = start.plusMinutes(minutes);

        long seconds = ChronoUnit.SECONDS.between(start, end);
        start = start.plusSeconds(seconds);

        long millis = ChronoUnit.MILLIS.between(start, end);

        return String.format("%d年%d个月%d天%d小时%d分%d秒%d毫秒",
                period.getYears(), period.getMonths(), days, hours, minutes, seconds, millis);
    }

    public static void main(String[] args) {
        LocalDateTime startTime = LocalDateTime.of(2020, 5, 17, 0, 30, 20, 500);
        LocalDateTime endTime = LocalDateTime.now();

        System.out.println(calculateTimeDifference(startTime, endTime));
    }
}
