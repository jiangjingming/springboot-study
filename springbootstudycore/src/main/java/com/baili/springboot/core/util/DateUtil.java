package com.baili.springboot.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期操作
 */
public class DateUtil {

    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public final static String YYYY_MM_DD = "yyyy-MM-dd";

    private DateUtil() {

    }

    /**
     * java8 时间转化
     * @param localDateTime
     * @return
     */
    public static Date covert(LocalDateTime localDateTime){

        return localDateTime==null?null:Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * java8 时间转化
     * @param date
     * @return
     */
    public static LocalDateTime covert(Date date){
        return date==null?null:LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * @return java.util.Date
     */
    public static final Date getNow() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            return sdf.parse(getNowTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * @param pattern
     *            日期格式
     * @return java.util.Date
     */
    public final Date getNow(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(getNowTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * @return String
     */
    public static final String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(new GregorianCalendar().getTime());
    }

    /**
     * @description 得到指定格式的字符串日期
     */
    public static final String getNowTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new GregorianCalendar().getTime());
    }

    /**
     * String型转Date型
     *
     * @param date
     *            String型日期
     * @param pattern
     *            日期格式
     * @return Date
     * @throws ParseException
     */
    public static final Date stringToDate(String date, String pattern) throws ParseException {
        if (date == null || pattern == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    /**
     * @description 日期转字符串
     * @date 2016-3-31
     * @return String
     * @param date
     * @param pattern
     * @return
     */
    public static final String dateToString(Date date, String pattern) {
        if (date == null || pattern == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 比较时间大小
     * 
     * @date 2007-10-31
     * @param startDate
     * @param endDate
     * @return Long
     */

    public static final Long calculateDate(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) > 0) {
            return Long.valueOf(0);
        }
        Long temp = endDate.getTime() - startDate.getTime();
        // return (temp/1000/60/60/24); //大于的时间/毫秒/秒/小时/天/
        return (temp / 1000 / 60 / 60); // 大于的时间/毫秒/秒/小时/天/
    }

    /**
     * 比较时间大小
     * 
     * @date 2007-10-31
     * @param startDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @param negative
     *            是否允许输出负数,true 允许. false 不允许
     * @return Long
     */

    public final Long calculateDate(Date startDate, Date endDate, boolean negative) {
        if (!negative && startDate.compareTo(endDate) > 0) {
            return Long.valueOf(0);
        }
        Long temp = endDate.getTime() - startDate.getTime();
        // return (temp/1000/60/60/24); //大于的时间/毫秒/秒/小时/天/
        return (temp / 1000 / 60 / 60); // 大于的时间/毫秒/秒/小时/天/
    }

    /**
     * @description 比较两个字符串时间的大小(时间降序)
     * @date 2016-3-30
     * @return int
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date0(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * @description 比较两个字符串时间的大小(时间升序)
     * @date 2016-3-30
     * @return int
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date1(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 将Date型转为String型
     * 
     * @param date
     * @param pattern
     * @return String
     */
    public static final String convDateToString(Date date, String pattern) {
        if (date == null || pattern == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     * 
     * @param date
     *            String 想要格式化的日期
     * @param oldPattern
     *            String 想要格式化的日期的现有格式
     * @param newPattern
     *            String 想要格式化成什么格式
     * @return String
     */
    public static final String stringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern); // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern); // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(date); // 将给定的字符串中的日期提取出来
        } catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace(); // 打印异常信息
        }
        return sdf2.format(d);
    }

    /**
     * 根据日期得到年，月，日
     * 
     * @param date
     * @param part
     *            yyyy,MM,dd
     * @return String
     */
    public final String getPartDate(Date date, String part) {
        if (date == null || part == null)
            return null;

        SimpleDateFormat sdf = new SimpleDateFormat(part);
        return sdf.format(date);
    }

    /**
     * 根据日期得到年，月，日
     * 
     * @param month
     *            1-12不包括0
     * @return String
     */
    public final String getZeroMonth(Integer month) {
        if (month == null || month.compareTo(0) == 0)
            return "";
        if (month > 0 && month < 10) {
            return "0" + String.valueOf(month);
        }
        return String.valueOf(month);
    }

    /**
     * 时间加减运算<br>
     * java中对日期的加减操作<br>
     * gc.add(1,-1)表示年份减一.<br>
     * gc.add(2,-1)表示月份减一.<br>
     * gc.add(3.-1)表示周减一.<br>
     * gc.add(5,-1)表示天减一.<br>
     * gc.add(10,-1)表示小时减一.<br>
     * gc.add(12,-1)表示分减一.<br>
     * gc.add(13,-1)表示秒减一.<br>
     * 以此类推应该可以精确的毫秒吧.没有再试.大家可以试试.<br>
     * GregorianCalendar类的add(int field,int amount)方法表示年月日加减.<br>
     * field参数表示年,月.日等.<br>
     * amount参数表示要加减的数量.<br>
     * 
     * @param date
     *            Date型日期
     * @param pattern
     *            日期格式
     * @param category
     *            运算种类，例如：天之间运算，月之间运算 ,1年,2月,3周,5天
     * @param number
     *            正数代表加、负数代表减
     * @return String
     */
    public static final String calcDate(Date date, String pattern, int category, int number) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(category, number);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
        return convDateToString(gc.getTime(), pattern);
    }

    /**
     * @description 指定日期加几天
     * @date 2016-3-31
     * @return Date
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 
     * @param datetime
     * @return
     */
    public static String getMaxDate(String datetime) {
        Date mindate = null;
        Calendar maxdate = Calendar.getInstance();
        if (datetime != null && datetime.length() > 0) {
            if (datetime.length() >= 8) {
                DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    mindate = fm.parse(datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                maxdate.setTime(mindate);
                maxdate.add(Calendar.DATE, 1);
            } else if (datetime.length() >= 6) {
                DateFormat fm = new SimpleDateFormat("yyyy-MM");
                try {
                    mindate = fm.parse(datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                maxdate.setTime(mindate);
                maxdate.add(Calendar.MONTH, 1);
            } else {
                DateFormat fm = new SimpleDateFormat("yyyy");
                try {
                    mindate = fm.parse(datetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                maxdate.setTime(mindate);
                maxdate.add(Calendar.YEAR, 1);
            }
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(fmt.format(maxdate.getTime()).toString());
        return fmt.format(maxdate.getTime()).toString();
    }

    /**
     * 根据日期计算这个星期的星期一是多少，并且星期一以00:00:00开头
     * 
     * @param queryDate
     * @return String[]
     * @throws ParseException
     */
    public String calcMonday(String queryDate) throws ParseException {
        String date;
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        gc.setTime(df.parse(queryDate));
        gc.add(5, -1);
        while (gc.get(7) != 1) {
            gc.add(5, -1);
        }
        gc.add(5, 1);
        date = df.format(gc.getTime()) + " 00:00:00";
        return date;
    }

    /**
     * 根据日期计算这个星期的星期日是多少，并且星期日以23:59:59开头
     * 
     * @param queryDate
     * @return String[]
     * @throws ParseException
     */
    public String calcSunday(String queryDate) throws ParseException {
        String date;
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        gc.setTime(df.parse(queryDate));
        gc.add(5, -1);
        while (gc.get(7) != 7) {
            gc.add(5, 1);
        }
        gc.add(5, 1);
        date = df.format(gc.getTime()) + " 23:59:59";
        return date;
    }

    /**
     * 根据日期计算这个月的第一天是几号
     * 
     * @param queryDate
     * @return String
     * @throws ParseException
     */
    public Date calcBeginMonth(String queryDate) throws ParseException {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        gc.setTime(df.parse(queryDate));
        int i = gc.get(Calendar.MONTH);
        // 11表示第12月
        while (gc.get(Calendar.MONTH) != (i == 0 ? 11 : i - 1)) {
            gc.add(5, -1);
        }
        gc.add(5, 1);
        return gc.getTime();
    }

    /**
     * 根据日期计算这个月的最后一天是几号
     * 
     * @param queryDate
     * @return String
     * @throws ParseException
     */
    public Date calcEndMonth(String queryDate) throws ParseException {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        gc.setTime(df.parse(queryDate));
        int i = gc.get(Calendar.MONTH);
        // 11表示第12月
        while (gc.get(Calendar.MONTH) != (i == 11 ? 0 : i + 1)) {
            gc.add(5, 1);
        }
        gc.add(5, -1);
        return gc.getTime();
    }

    /**
     * @description 判断所给日期是不是当前月
     * @date 2016-4-11
     * @return int 是本月返回1，不是本月返回0
     * @param date
     * @param pattern
     *            所给日期字符串格式
     * @return
     * @throws ParseException
     */
    public static int checkThisMonth(String date, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String da = df.format(new Date());
        System.out.println("当前日期==" + da);
        if (da.equals(date))
            return 1;
        return 0;
    }

    public static int checkLastMonth(String date, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, -1);
        Date currentTime = cal.getTime();// 当前时间的上个月时间
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String curTime = sdf.format(currentTime);
        System.out.println("上个月的时间为：" + curTime);
        if (curTime.equals(date))
            return 1;
        return 0;
    }
}