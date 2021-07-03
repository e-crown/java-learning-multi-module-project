package java8;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

/**
 * @Desc
 * 
 * @Date 2021/2/19 15:30
 * @Version 1.0
 */
public class TimeTest {

    /**
     *
     * @throws ParseException
     */
    @Test
    public void test5() throws ParseException {
        final String patterStr = "yyyy-MM-dd";
        final DateFormat dateFormat = new SimpleDateFormat(patterStr);

        final String birthdayStr = "1988-09-11";
        // 字符串 -> Date -> 字符串
        Date birthday = dateFormat.parse(birthdayStr);
        final long birthdayTimestamp = birthday.getTime();
        System.out.println("老王的生日是：" + birthday);
        System.out.println("老王的生日的时间戳是：" + birthdayTimestamp);

        System.out.println("==============程序经过一番周转，我的同时 方法入参传来了生日的时间戳=============");
        // 字符串 -> Date -> 时间戳 -> Date -> 字符串
        birthday = new Date(birthdayTimestamp);
        System.out.println("老王的生日是：" + birthday);
        System.out.println("老王的生日的时间戳是：" + dateFormat.format(birthday));
    }

    /**
     * Date时区无关性
     */
    @Test
    public void test6() {
        final String patterStr = "yyyy-MM-dd HH:mm:ss";
        final Date currDate = new Date(System.currentTimeMillis());

        // 北京时区
        final DateFormat bjDateFormat = new SimpleDateFormat(patterStr);
        bjDateFormat.setTimeZone(TimeZone.getDefault());
        // 纽约时区
        final DateFormat newYorkDateFormat = new SimpleDateFormat(patterStr);
        newYorkDateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        // 伦敦时区
        final DateFormat londonDateFormat = new SimpleDateFormat(patterStr);
        londonDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/London"));

        System.out.println("毫秒数:" + currDate.getTime() + ", 北京本地时间:" + bjDateFormat.format(currDate));
        System.out.println("毫秒数:" + currDate.getTime() + ", 纽约本地时间:" + newYorkDateFormat.format(currDate));
        System.out.println("毫秒数:" + currDate.getTime() + ", 伦敦本地时间:" + londonDateFormat.format(currDate));
    }

    @Test
    public void test9() throws ParseException {
        final String patternStr = "G GG GGGGG E EE EEEEE a aa aaaaa";
        final Date currDate = new Date();

        System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓中文地区模式↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
        System.out.println("====================Date->String====================");
        DateFormat dateFormat = new SimpleDateFormat(patternStr, Locale.CHINA);
        System.out.println(dateFormat.format(currDate));
        System.out.println();
        System.out.println("====================String->Date====================");
        String dateStrParam = "公元 公元 公元 星期六 星期六 星期六 下午 下午 下午";
        System.out.println(dateFormat.parse(dateStrParam));
        System.out.println();
        System.out.println("↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓英文地区模式↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
        System.out.println("====================Date->String====================");
        dateFormat = new SimpleDateFormat(patternStr, Locale.US);
        System.out.println(dateFormat.format(currDate));
        System.out.println();
        System.out.println("====================String->Date====================");
        dateStrParam = "AD ad bC Sat SatUrday sunDay PM PM Am";
        System.out.println(dateFormat.parse(dateStrParam));
    }
}
