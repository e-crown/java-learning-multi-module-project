package com.kv.wc.common.utils;

import java.text.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

/**
 * 日期工具类
 *
 */
public class DateUtil extends DateUtils {
    /**
     * 常用变量
     */
    public static final String YMDHMSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";
    public static final String YMDHMSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_dd = "yyyy-MM-dd";
    public static final String HMS = "HH:mm:ss";
    public static final String HM = "HH:mm";
    public static final String YMDHM = "yyyy-MM-dd HH:mm";
    public static final String YMDHMS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final long ONE_DAY_MILLS = 3600000 * 24;
    public static final int WEEK_DAYS = 7;
    private static final int dateLength = YMDHM.length();

    /**
     * SimpleDateFormat 线程安全考虑
     */
    private static final ThreadLocal<DateFormat> sdf_ymdhmss_z =
        ThreadLocal.withInitial(() -> new SimpleDateFormat(YMDHMSS_Z));
    private static final ThreadLocal<DateFormat> sdf_ymdhmssz =
        ThreadLocal.withInitial(() -> new SimpleDateFormat(YMDHMSSZ));
    private static final ThreadLocal<DateFormat> sdf_yyyyMMdd =
        ThreadLocal.withInitial(() -> new SimpleDateFormat(YYYYMMDD));
    private static final ThreadLocal<DateFormat> sdf_yyyyMM =
        ThreadLocal.withInitial(() -> new SimpleDateFormat(YYYYMM));
    private static final ThreadLocal<DateFormat> sdf_default =
        ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT));
    private static final ThreadLocal<DateFormat> sdf_yyyy_mm_dd =
        ThreadLocal.withInitial(() -> new SimpleDateFormat(YYYY_MM_dd));
    private static final ThreadLocal<DateFormat> sdf_ymdhms =
        ThreadLocal.withInitial(() -> new SimpleDateFormat(YMDHMS));

    /**
     * 指定日期格式
     *
     * @param formatStr
     * @return
     */
    public static DateFormat getDateFormat(String formatStr) {
        if (formatStr.equalsIgnoreCase(DEFAULT)) {
            return sdf_default.get();
        } else if (formatStr.equalsIgnoreCase(YMDHMSS_Z)) {
            return sdf_ymdhmss_z.get();
        } else if (formatStr.equalsIgnoreCase(YMDHMSSZ)) {
            return sdf_ymdhmssz.get();
        } else if (formatStr.equalsIgnoreCase(YYYYMMDD)) {
            return sdf_yyyyMMdd.get();
        } else if (formatStr.equalsIgnoreCase(YYYY_MM_dd)) {
            return sdf_yyyy_mm_dd.get();
        } else if (formatStr.equalsIgnoreCase(YYYYMM)) {
            return sdf_yyyyMM.get();
        } else if (formatStr.equalsIgnoreCase(YMDHMS)) {
            return sdf_ymdhms.get();
        } else {
            return new SimpleDateFormat(formatStr);
        }
    }

    /**
     * 获取当前日期的UTC时间 不包含时分秒
     *
     * @return
     */
    public static Long getCurrentDateUtc() {
        java.time.LocalDate localDate = java.time.LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        java.time.Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant).getTime();
    }

    /**
     * 获取当前日期 不包含时分秒
     *
     * @return
     */
    public static Date getCurrentDate() {
        java.time.LocalDate localDate = java.time.LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        java.time.Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 将时间格式化成 yyyy-MM-dd HH:mm:ss 字符串
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateTime2String(Date date) {
        return formatDateToString(date, DEFAULT);
    }

    /**
     * 将时间格式化成 yyyyMMddHHmmss 字符串
     *
     * @param date
     * @return
     */
    public static String date2yMdHmsString(Date date) {
        return formatDateToString(date, YMDHMS);
    }

    /**
     * 将时间格式化成 yyyyMMddHHmm 字符串
     *
     * @param date
     * @return
     */
    public static String date2YYYYMMDDHHMMString(Date date) {
        return formatDateToString(date, YYYYMMDDHHMM);
    }

    /**
     * 将yyyyMMddHHmmss字符串类型时间格式化成Date类型
     *
     * @param date
     * @return
     */
    public static Date yMdHmsString2Date(String date) {
        return formatStringToDate(date, YMDHMS);
    }

    public static Date yMdString2Date(String date) {
        return formatStringToDate(date, YYYY_MM_dd);
    }

    public static Date string2Date(String date) {
        return formatStringToDate(date, DEFAULT);
    }

    /**
     * 字符串转换为制定格式日期 (注意：当你输入的日期是2014-12-21 12:12，format对应的应为yyyy-MM-dd HH:mm 否则异常抛出)
     *
     * @param date
     * @param format
     * @return @
     */
    public static Date formatStringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.toString());
        }
    }

    /**
     * 日期转换为制定格式字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String formatDateToString(Date time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    /**
     * 描述：获取当前日期 修改日期：2014-07-02上午10:23:26
     *
     * @param dateFormat
     *            日期格式
     * @return 返回当前日期
     */
    public static String currentDate(String dateFormat) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
            String currentDate = sf.format(new Date());
            return currentDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断一个日期是否属于两个时段内
     *
     * @param time
     * @param timeRange
     * @return
     */
    public static boolean isTimeInRange(Date time, Date[] timeRange) {
        return (!time.before(timeRange[0]) && !time.after(timeRange[1]));
    }

    /**
     * 从完整的时间截取精确到分的时间
     *
     * @param fullDateStr
     * @return
     */
    public static String getDateToMinute(String fullDateStr) {
        return fullDateStr == null ? null
            : (fullDateStr.length() >= dateLength ? fullDateStr.substring(0, dateLength) : fullDateStr);
    }

    /**
     * 返回指定年度的所有周。List中包含的是String[2]对象 string[0]本周的开始日期,string[1]是本周的结束日期。 日期的格式为YYYY-MM-DD 每年的第一个周，必须包含星期一且是完整的七天。
     * 例如：2009年的第一个周开始日期为2009-01-05，结束日期为2009-01-11。 星期一在哪一年，那么包含这个星期的周就是哪一年的周。
     * 例如：2008-12-29是星期一，2009-01-04是星期日，哪么这个周就是2008年度的最后一个周。
     *
     * @param year
     *            格式 YYYY ，必须大于1900年度 小于9999年
     * @return @
     */
    public static List<String[]> getWeeksByYear(final int year) {
        int weeks = getWeekNumOfYear(year);
        List<String[]> result = new ArrayList<String[]>(weeks);
        int start = 1;
        int end = 7;
        for (int i = 1; i <= weeks; i++) {
            String[] tempWeek = new String[2];
            tempWeek[0] = getDateForDayOfWeek(year, i, start);
            tempWeek[1] = getDateForDayOfWeek(year, i, end);
            result.add(tempWeek);
        }
        return result;
    }

    /**
     * 计算指定年、周的上一年、周
     *
     * @param year
     * @param week
     * @return @
     */
    public static int[] getLastYearWeek(int year, int week) {
        if (week <= 0) {
            throw new IllegalArgumentException("周序号不能小于1！！");
        }
        int[] result = {week, year};
        if (week == 1) {
            // 上一年
            result[1] -= 1;
            // 最后一周
            result[0] = getWeekNumOfYear(result[1]);
        } else {
            result[0] -= 1;
        }
        return result;
    }

    /**
     * 下一个[周，年]
     *
     * @param year
     * @param week
     * @return @
     */
    public static int[] getNextYearWeek(int year, int week) {
        if (week <= 0) {
            throw new IllegalArgumentException("周序号不能小于1！！");
        }
        int[] result = {week, year};
        int weeks = getWeekNumOfYear(year);
        if (week == weeks) {
            // 下一年
            result[1] += 1;
            // 第一周
            result[0] = 1;
        } else {
            result[0] += 1;
        }
        return result;
    }

    /**
     * 计算指定年度共有多少个周。(从周一开始)
     *
     * @param year
     * @return @
     */
    public static int getWeekNumOfYear(final int year) {
        return getWeekNumOfYear(year, Calendar.MONDAY);
    }

    /**
     * 计算指定年度共有多少个周。
     *
     * @param year
     *            yyyy
     * @return @
     */
    public static int getWeekNumOfYear(final int year, int firstDayOfWeek) {
        // 每年至少有52个周 ，最多有53个周。
        int minWeeks = 52;
        int maxWeeks = 53;
        int result = minWeeks;
        int sIndex = 4;
        String date = getDateForDayOfWeek(year, maxWeeks, firstDayOfWeek);
        // 判断年度是否相符，如果相符说明有53个周。
        if (date.substring(0, sIndex).equals(year)) {
            result = maxWeeks;
        }
        return result;
    }

    public static int getWeeksOfWeekYear(final int year) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setMinimalDaysInFirstWeek(WEEK_DAYS);
        cal.set(Calendar.YEAR, year);
        return cal.getWeeksInWeekYear();
    }

    /**
     * 获取指定年份的第几周的第几天对应的日期yyyy-MM-dd(从周一开始)
     *
     * @param year
     * @param weekOfYear
     * @param dayOfWeek
     * @return yyyy-MM-dd 格式的日期 @
     */
    public static String getDateForDayOfWeek(int year, int weekOfYear, int dayOfWeek) {
        return getDateForDayOfWeek(year, weekOfYear, dayOfWeek, Calendar.MONDAY);
    }

    /**
     * 获取指定年份的第几周的第几天对应的日期yyyy-MM-dd，指定周几算一周的第一天（firstDayOfWeek）
     *
     * @param year
     * @param weekOfYear
     * @param dayOfWeek
     * @param firstDayOfWeek
     *            指定周几算一周的第一天
     * @return yyyy-MM-dd 格式的日期
     */
    public static String getDateForDayOfWeek(int year, int weekOfYear, int dayOfWeek, int firstDayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(firstDayOfWeek);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        cal.setMinimalDaysInFirstWeek(WEEK_DAYS);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        return formatDateToString(cal.getTime(), YYYY_MM_dd);
    }

    /**
     * 获取指定日期星期几
     *
     * @param datetime @
     */
    public static int getWeekOfDate(String datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setMinimalDaysInFirstWeek(WEEK_DAYS);
        Date date = formatStringToDate(datetime, YYYY_MM_dd);
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);

    }

    /**
     * 计算某年某周内的所有日期(从周一开始 为每周的第一天)
     *
     * @param yearNum
     * @param weekNum
     * @return @
     */
    public static List<String> getWeekDays(int yearNum, int weekNum) {
        return getWeekDays(yearNum, weekNum, Calendar.MONDAY);
    }

    /**
     * 计算某年某周内的所有日期(七天)
     *
     * @return yyyy-MM-dd 格式的日期列表
     */
    public static List<String> getWeekDays(int year, int weekOfYear, int firstDayOfWeek) {
        List<String> dates = new ArrayList<String>();
        int dayOfWeek = firstDayOfWeek;
        for (int i = 0; i < WEEK_DAYS; i++) {
            dates.add(getDateForDayOfWeek(year, weekOfYear, dayOfWeek++, firstDayOfWeek));
        }
        return dates;
    }

    /**
     * 获取目标日期的上周、或本周、或下周的年、周信息
     *
     * @param queryDate
     *            传入的时间
     * @param weekOffset
     *            -1:上周 0:本周 1:下周
     * @param firstDayOfWeek
     *            每周以第几天为首日
     * @return
     */
    public static int[] getWeekAndYear(String queryDate, int weekOffset, int firstDayOfWeek) {

        Date date = formatStringToDate(queryDate, YYYY_MM_dd);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(firstDayOfWeek);
        calendar.setMinimalDaysInFirstWeek(WEEK_DAYS);
        int year = calendar.getWeekYear();
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int[] result = {week, year};
        switch (weekOffset) {
            case 1:
                result = getNextYearWeek(year, week);
                break;
            case -1:
                result = getLastYearWeek(year, week);
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 计算个两日期的天数
     *
     * @param startDate
     *            开始日期字串
     * @param endDate
     *            结束日期字串
     * @return
     */
    public static int getDaysBetween(String startDate, String endDate) {
        int dayGap = 0;
        if (startDate != null && startDate.length() > 0 && endDate != null && endDate.length() > 0) {
            Date end = formatStringToDate(endDate, YYYY_MM_dd);
            Date start = formatStringToDate(startDate, YYYY_MM_dd);
            dayGap = getDaysBetween(start, end);
        }
        return dayGap;
    }

    private static int getDaysBetween(Date startDate, Date endDate) {
        return (int)((endDate.getTime() - startDate.getTime()) / ONE_DAY_MILLS);
    }

    /**
     * 计算两个日期之间的天数差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDaysGapOfDates(Date startDate, Date endDate) {
        int date = 0;
        if (startDate != null && endDate != null) {
            date = getDaysBetween(startDate, endDate);
        }
        return date;
    }

    /**
     * 计算两个日期之间的年份差距
     *
     * @param firstDate
     * @param secondDate
     * @return
     */

    public static int getYearGapOfDates(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return 0;
        }
        Calendar helpCalendar = Calendar.getInstance();
        helpCalendar.setTime(firstDate);
        int firstYear = helpCalendar.get(Calendar.YEAR);
        helpCalendar.setTime(secondDate);
        int secondYear = helpCalendar.get(Calendar.YEAR);
        return secondYear - firstYear;
    }

    /**
     * 计算两个日期之间的月份差距
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static int getMonthGapOfDates(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return 0;
        }

        return (int)((secondDate.getTime() - firstDate.getTime()) / ONE_DAY_MILLS / 30);

    }

    /**
     * 计算是否包含当前日期
     *
     * @return
     */
    public static boolean isContainCurrent(List<String> dates) {
        boolean flag = false;
        SimpleDateFormat fmt = new SimpleDateFormat(YYYY_MM_dd);
        Date date = new Date();
        String dateStr = fmt.format(date);
        for (int i = 0; i < dates.size(); i++) {
            if (dateStr.equals(dates.get(i))) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 从date开始计算time天后的日期
     *
     * @param time
     * @return
     */
    public static String getCalculateDateToString(String startDate, int time) {
        String resultDate = null;
        if (startDate != null && startDate.length() > 0) {
            Date date = formatStringToDate(startDate, YYYY_MM_dd);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.DAY_OF_YEAR, time);
            date = c.getTime();
            resultDate = formatDateToString(date, YYYY_MM_dd);
        }
        return resultDate;
    }

    /**
     * 获取从某日期开始计算，指定的日期所在该年的第几周
     *
     * @param date
     * @param admitDate
     * @return
     */
    public static int[] getYearAndWeeks(String date, String admitDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(formatStringToDate(admitDate, YYYY_MM_dd));
        int time = c.get(Calendar.DAY_OF_WEEK);
        return getWeekAndYear(date, 0, time);
    }

    /**
     * 获取指定日期refDate，前或后一周的所有日期
     *
     * @param refDate
     *            参考日期
     * @param weekOffset
     *            -1:上周 0:本周 1:下周
     * @param startDate
     *            哪天算一周的第一天
     * @return yyyy-MM-dd 格式的日期
     */
    public static List<String> getWeekDaysAroundDate(String refDate, int weekOffset, String startDate) {
        // 以startDate为一周的第一天
        Calendar c = Calendar.getInstance();
        c.setTime(formatStringToDate(startDate, YYYY_MM_dd));
        int firstDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        // 获取相应周
        int[] weekAndYear = getWeekAndYear(refDate, weekOffset, firstDayOfWeek);
        // 获取相应周的所有日期
        return getWeekDays(weekAndYear[1], weekAndYear[0], firstDayOfWeek);
    }

    /**
     * 根据时间点获取时间区间
     *
     * @param hours
     * @return
     */
    public static List<String[]> getTimePointsByHour(int[] hours) {
        List<String[]> hourPoints = new ArrayList<String[]>();
        String sbStart = ":00:00";
        String sbEnd = ":59:59";
        for (int i = 0; i < hours.length; i++) {
            String[] times = new String[2];
            times[0] = hours[i] + sbStart;
            times[1] = (hours[(i + 1 + hours.length) % hours.length] - 1) + sbEnd;
            hourPoints.add(times);
        }
        return hourPoints;
    }

    /**
     * 根据指定的日期，增加或者减少天数
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    public static Date addSecends(Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 根据指定的日期，类型，增加或减少数量
     *
     * @param date
     * @param calendarField
     * @param amount
     * @return
     */
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 获取当前日期的最大日期 时间2014-12-21 23:59:59
     *
     * @return
     */
    public static Date getCurDateWithMaxTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取当前日期的最小日期时间 2014-12-21 00:00:00
     *
     * @return
     */
    public static Date getCurDateWithMinTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String Date2StringFormat_yyyyMMdd(Date date) {
        if (date != null) {
            return getDateFormat(YYYYMMDD).format(date);
        } else {
            throw new NullPointerException("DateUtil formatDefaultDate input null date");
        }
    }

    public static String Date2StringFormat_yyyy_MM_dd(Date date) {
        if (date != null) {
            return getDateFormat(YYYY_MM_dd).format(date);
        } else {
            throw new NullPointerException("DateUtil formatDefaultDate input null date");
        }
    }

    public static String formatDefaultDateTime(Date date) {
        if (date != null) {
            return getDateFormat(DEFAULT).format(date);
        } else {
            throw new NullPointerException("DateUtil getFormattedDateTimeStr input null date");
        }
    }

    public static String formatDateTimeForDynamoDB(Date date) {
        if (date != null) {
            return getDateFormat(YMDHMSS_Z).format(date);
        } else {
            throw new NullPointerException("DateUtil getFormattedDateTimeStr input null date");
        }
    }

    public static Date parseDateTimeStr(String dateTimeStr) {
        if (StringUtils.isBlank(dateTimeStr)) {
            return null;
        }
        ParsePosition pos = new ParsePosition(0);
        Date date = getDateFormat(dateTimeStr).parse(dateTimeStr, pos);
        return date;
    }

    /**
     * time 为(yyyy-MM-dd'T'HH:mm:ss.SSSZZ)格式的字符串时间 例如 "2012-05-25T14:59:38.237-07:00" "2012-06-19T01:07:52.000Z"
     *
     * @return java.util.Date 当前Locale的Date
     * @throws UnsupportedOperationException
     *             if parsing is not supported
     * @throws IllegalArgumentException
     *             if the time to parse is invalid
     */
    public static Date parseISOFormatToDate(String time) {
        DateTimeFormatter parser2 = ISODateTimeFormat.dateTime();
        return parser2.parseDateTime(time).toDate();
    }
    /*public static Date getUTCTime(Long time){
    	//1、取得本地时间：
    	final Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(time);
    
    	//2、取得时间偏移量：
    	final int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
    
    	//3、取得夏令时差：
    	final int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
    
    	//4、从本地时间里扣除这些差量，即可以取得UTC时间：
    	cal.add(Calendar.MILLISECOND, - (zoneOffset + dstOffset));
    	return cal.getTime();
    }*/

    /**
     * eg: 20140101
     *
     * @param date
     * @return
     */
    public static String getYearQuarter(Date date, boolean next) {
        Date useDate = date != null ? date : new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(useDate);

        int year = cal.get(Calendar.YEAR);
        int quarter = getQuarter(cal);
        if (next) {
            if (quarter == 4) {
                year += year;
                quarter = 1;
            } else {
                quarter += 1;
            }
        }
        String str = String.format("%s_%s", year, quarter);
        return str;
    }

    /**
     * 得到一天是一年的第几个季度
     *
     * @return
     */
    private static int getQuarter(Calendar cal) {
        int month = cal.get(Calendar.MONTH) + 1;
        int quarter;
        switch (month) {
            case 1:
            case 2:
            case 3:
                quarter = 1;
                break;
            case 4:
            case 5:
            case 6:
                quarter = 2;
                break;
            case 7:
            case 8:
            case 9:
                quarter = 3;
                break;
            case 10:
            case 11:
            case 12:
                quarter = 4;
                break;
            default:
                quarter = 0;
                break;
        }
        return quarter;
    }

    /**
     * utc时间转DateTime 毫秒
     *
     * @param utcTime
     *            utc时间
     * @return
     */
    public static DateTime utc2DateTime(Long utcTime) {
        DateTime dateTime = new DateTime(utcTime, DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+8")));
        return dateTime;
    }

    /**
     * utc时间转DateTime 秒
     *
     * @param utcTime
     *            utc时间
     * @return
     */
    public static DateTime utcSec2DateTime(Long utcTime) {
        return utc2DateTime(utcTime * 1000);
    }

    /**
     * @param utcTime
     * @return
     */
    public static String utc2DateTimeStr(String utcTime) {
        if (StringUtils.isBlank(utcTime)) {
            return "";
        }
        DateTime dateTime = utcSec2DateTime(Long.valueOf(utcTime));
        return dateTime2String(dateTime.toDate());
    }

    // public static void UTCtoCST(String utc){
    // //CST可视为美国、澳大利亚、古巴或中国的标准时间,北京时间与utc时间相差8小时
    // ZonedDateTime zdt = ZonedDateTime.parse(utc);
    // LocalDateTime localDateTime = zdt.toLocalDateTime();
    // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM HH:mm:ss");
    // System.out.println("北京时间："+formatter.format(localDateTime.plusHours(8)));
    // }

    /**
     * 获取plusDays前后的日期 格式为YYYY-MM-DD
     *
     * @param plusDays
     * @return
     */
    public static DateTime getDateTime(Integer plusDays) {
        DateTime dateTime = new DateTime();
        // plusDays前的日期
        dateTime = dateTime.plusDays(plusDays);
        return dateTime.dayOfWeek().roundFloorCopy();
    }

    /**
     * 获取plusDays前的日期utc时间 若plusDays=0 则获取当天日期的utc时间(注 不包括时分秒)
     *
     * @param plusDays
     * @return
     */
    public static Long getDateUTCTime(Integer plusDays) {
        return getDateTime(plusDays).getMillis();
    }

    /**
     * 获取当前的小时数
     *
     * @return
     */
    public static int getHourOfDay() {
        DateTime dateTime = new DateTime();
        return dateTime.getHourOfDay();
    }

    /**
     * 获取当前的小时数
     *
     * @return
     */
    public static int getMinuteOfHour() {
        DateTime dateTime = new DateTime();
        return dateTime.getMinuteOfHour();
    }

    /**
     * 获取2个时间间隔的天数
     *
     * @param endTime
     *            结束时间
     * @param starTime
     *            开始时间
     * @return
     */
    public static int getDaysBetweenV2(Date endTime, Date starTime) {
        DateTime start = new DateTime(starTime);
        DateTime end = new DateTime(endTime);
        return Math.abs(Days.daysBetween(end, start).getDays());
    }

    /**
     * 获取2个时间之间的小时数
     *
     * @param starTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return
     */
    public static int getHoursBetweenDays(Date endTime, Date starTime) {
        DateTime start = new DateTime(starTime);
        DateTime end = new DateTime(endTime);
        return Math.abs(Hours.hoursBetween(end, start).getHours());
    }

    /**
     * 获取2个时间之间的小时数
     *
     * @param endTime
     *            结束时间
     * @param starTime
     *            开始时间
     * @return
     */
    public static int getMinutesBetweenDays(Date endTime, Date starTime) {
        DateTime start = new DateTime(starTime);
        DateTime end = new DateTime(endTime);
        return Math.abs(Minutes.minutesBetween(end, start).getMinutes());
    }

    public static int getHoursBetweenDays2(Date starTime, Date endTime) {
        Long t = endTime.getTime() - starTime.getTime();
        return (int)(t / (60 * 60 * 1000));
    }

    /**
     * 获取指定年月第一天的utc时间
     *
     * @param year
     *            年
     * @param month
     *            月
     * @return
     */
    public static long getMonthFirstDay(Integer year, Integer month) {
        DateTime dateTime;
        if (null == year || null == month) {
            dateTime = new DateTime();
        } else {
            dateTime = new DateTime(year, month, 1, 0, 0);
        }
        return dateTime.monthOfYear().roundFloorCopy().getMillis();
    }

    public static String yestodayDate() {
        LocalDate localDate = LocalDate.now().minusDays(1);
        return Date2StringFormat_yyyy_MM_dd(localDate.toDate());
    }

    /*public static Map<String, String> getDetailDate(LocalDate localDate) {
        Map<String, String> map = Maps.newHashMap();
        String date = Date2StringFormat_yyyy_MM_dd(localDate.toDate());
        map.put("date",date);
        String year = String.valueOf(localDate.getYear());
        map.put("year",year);
        String month = String.valueOf(localDate.monthOfYear().get());
        map.put("month",month);
        return map;
    }*/
    // 上周同一天 往前推7天
    public static String lastWeekDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        localDate = localDate.minusDays(7);
        return Date2StringFormat_yyyy_MM_dd(localDate.toDate());
    }

    // 上个月同一天 如果本月天数大于上个月的天数 则多天返回null
    public static String lastMonthDate(String date) {
        LocalDate lastMonth = lastMonthLocalDate(date);
        if (null == lastMonth) {
            return null;
        }
        return Date2StringFormat_yyyy_MM_dd(lastMonth.toDate());
    }

    public static LocalDate lastMonthLocalDate(String date) {
        // 31天的为大月 小于31天的月份为小月
        Integer[] days31Month = {1, 3, 5, 7, 8, 10, 12};// 大月月份数组
        LocalDate localDate = LocalDate.parse(date);
        LocalDate lastMonth = localDate.minusMonths(1);
        int lastMonthOfYear = lastMonth.getMonthOfYear();
        // 判断月份是否为大月
        if (!Arrays.stream(days31Month).anyMatch(p -> p == lastMonthOfYear)) {
            int dayOfMonth = localDate.getDayOfMonth();
            if (2 == lastMonthOfYear) {
                java.time.LocalDate localDate1 =
                    java.time.LocalDate.ofYearDay(lastMonth.getYear(), lastMonth.dayOfYear().get());
                // 判断是否为闰年
                if (localDate1.isLeapYear()) {
                    if (dayOfMonth > 29) {
                        return null;
                    }
                } else {
                    if (dayOfMonth > 28) {
                        return null;
                    }
                }
            } else {
                if (dayOfMonth > 30) {
                    return null;
                }
            }

        }
        return lastMonth;
    }

    // （T-1）天往前推7天
    public static List<String> getBefore7Dates(LocalDate date) {
        List<String> indexDates = new ArrayList<>();
        date = date.minusDays(1);// T-1
        indexDates.add(Date2StringFormat_yyyy_MM_dd(date.toDate()));
        for (int i = 1; i < 7; i++) {
            indexDates.add(Date2StringFormat_yyyy_MM_dd(date.minusDays(i).toDate()));
        }
        return indexDates;
    }

    public static List<String> lastWeekDates(LocalDate date) {
        List<String> indexDates = new ArrayList<>();
        date = date.minusDays(1);// T-1
        indexDates.add(Date2StringFormat_yyyy_MM_dd(date.minusDays(7).toDate()));
        for (int i = 1; i < 7; i++) {
            indexDates.add(Date2StringFormat_yyyy_MM_dd(date.minusDays(i + 7).toDate()));
        }
        return indexDates;
    }

    public static List<String> lastMonthDates(LocalDate date) {
        List<String> indexDates = new ArrayList<>();
        List<String> before = getBefore7Dates(date);
        String lastMonthDate;
        for (String p : before) {
            lastMonthDate = lastMonthDate(p);
            if (null == lastMonthDate) {
                continue;
            }
            indexDates.add(lastMonthDate);
        }
        return indexDates;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds
     *            精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        long lt = new Long(seconds);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str
     *            字符串日期
     * @param format
     *            如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当天0点的时间戳 offset 可设置偏移量 昨天为-1
     *
     * @param offset
     * @return
     */
    public static String zeroTimeStamp(int offset) {
        long current = System.currentTimeMillis();
        long zero = (current / (1000 * 3600 * 24) + offset) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        return String.valueOf(zero);
    }

    /**
     * 获取相对偏移量年份
     *
     * @param offset
     * @return
     */
    public static String getYear(int offset) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, offset);
        Date y = c.getTime();
        String time = format.format(y);
        return time;
    }

    /**
     * 获取相对偏移量年月
     *
     * @param offset
     * @param pattern
     * @return
     */
    public static String getYearMonth(int offset, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, offset);
        Date y = c.getTime();
        String time = format.format(y);
        return time;
    }

    /**
     * 获取相对偏移量年月日
     *
     * @param offset
     * @param pattern
     * @return
     */
    public static String getDay(int offset, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, offset);
        Date y = c.getTime();
        String time = format.format(y);
        return time;
    }

    /**
     * 获取当前日期
     *
     * @param pattern
     * @return
     */
    public static String getCurrentTime(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date y = c.getTime();
        String time = format.format(y);
        return time;
    }

    /**
     * 获取当前日期
     *
     * @param pattern
     * @return
     */
    public static Date getCurrentTimeDate(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Date y = c.getTime();
        return y;
    }

    /**
     * 获取当前时间所属季度
     *
     * @param offset
     *            偏移量
     * @return
     */
    public static int getQuarter(int offset) {
        int month = Integer.parseInt(getYearMonth(0, "MM"));
        int quarter;
        switch (month) {
            case 1:
            case 2:
            case 3:
                quarter = 1;
                break;
            case 4:
            case 5:
            case 6:
                quarter = 2;
                break;
            case 7:
            case 8:
            case 9:
                quarter = 3;
                break;
            case 10:
            case 11:
            case 12:
                quarter = 4;
                break;
            default:
                quarter = 0;
                break;
        }
        return quarter + offset;
    }

    /**
     * 获取当前季度偏移offset后所属季度的最后一个月时间
     *
     * @param offset
     * @return
     */
    public static String getYearMonthByQuarter(int offset) {
        String yearMonth = "";
        String year = DateUtil.getYear(0);
        int quarter = getQuarter(offset);
        if (quarter == 0) {
            year = String.valueOf(Integer.parseInt(year) - 1);
            yearMonth = year + "12";
        }
        switch (quarter) {
            case 1:
                yearMonth = year + "03";
                break;
            case 2:
                yearMonth = year + "06";
                break;
            case 3:
                yearMonth = year + "09";
                break;
            case 4:
                yearMonth = year + "12";
                break;
        }
        return yearMonth;
    }

    /**
     * 获取本周的第一天
     *
     * @return String
     **/
    public static String getWeekStart() {
        Calendar cal = Calendar.getInstance();
        // cal.add(Calendar.WEEK_OF_MONTH, 0);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 1);
        // cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    /**
     * 获取本周的最后一天
     *
     * @return String
     **/
    public static String getWeekEnd() {
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 7);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    /**
     * 获取今天
     *
     * @return String
     */
    public static String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static void main(String[] arg) {
        long utc = Long.valueOf("1580175120");
        DateTime dateTime = utc2DateTime(utc * 1000);
        System.out.println(dateTime2String(dateTime.toDate()));
    }

    public static String localDateTimeToString(java.time.LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return "";
        }
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(DEFAULT);
        return localDateTime.format(formatter);
    }

    public static String formatFullTime(java.time.LocalDateTime localDateTime) {
        return formatFullTime(localDateTime, YMDHMS);
    }

    public static String formatFullTime(java.time.LocalDateTime localDateTime, String pattern) {
        java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    private static String getDateFormat(Date date, String dateFormatType) {
        SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
        return simformat.format(date);
    }

    public static String formatCSTTime(String date, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        return DateUtil.getDateFormat(d, format);
    }
}
