package ldy.bigdata.gather.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

/**
 * @author Created by wangtuo on 2018/6/25.
 */
public class DateUtils {

    private static int one=1;
    private static int  two=2;
    private static int three=3;
    private static int four=4;
    private static int eleven=11;
    /**
     * 日期计算
     *
     * @param etldate 原始日期
     * @param day 增加至
     * @return 计算之后的值
     * @throws ParseException 日期解析异常
     */
    public static String getDate(String etldate, int day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date base = sdf.parse(etldate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(base);
        cal.add(Calendar.DATE, day);
        Date result = cal.getTime();
        return sdf.format(result);
    }
    /**
     * 获取每月月末日期
     *
     * @param etldate
     */
    public static String getMaxMonthDate(String etldate) throws ParseException {
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dstDateFormat.parse(etldate));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dstDateFormat.format(calendar.getTime());
    }

    /**
     * 获取每月月初日期
     * @param etldate
     * @return
     * @throws ParseException
     */
    public static String getMinMonthDate(String etldate) throws ParseException {
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dstDateFormat.parse(etldate));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dstDateFormat.format(calendar.getTime());
    }

    /**
     * 获取每年年初日期
     * @param etldate
     * @return
     * @throws ParseException
     */
    public static String getMinYearDate(String etldate) throws ParseException {
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dstDateFormat.parse(etldate));
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        return dstDateFormat.format(calendar.getTime());
    }
    /**
     * 获取每年年底日期
     * @param etldate
     * @return
     * @throws ParseException
     */
    public static String getMaxYearDate(String etldate) throws ParseException {
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dstDateFormat.parse(etldate));
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return dstDateFormat.format(calendar.getTime());
    }

    /**
     * 获取指定日期上一个月的月末日期
     * @param etldate 8位日期格式
     * @return 8位日期格式的字符串
     */
    public static String getPreMonthLastDay(String etldate) throws ParseException {
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dstDateFormat.parse(etldate));
        calendar.set(Calendar.DAY_OF_MONTH,0);
        return dstDateFormat.format(calendar.getTime());
    }
    public static int  getMonth(String etldate) throws ParseException {
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dstDateFormat.parse(etldate));
        int month=calendar.get(Calendar.MONTH);
        return month+1;
    }

    /**
     * 获取指定日期上一年的年末日期
     * @param etlDate 8位日期格式
     * @return 8位日期格式的字符串
     */
    public static String getYearLastDay(String etlDate,int year) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date base = sdf.parse(etlDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(base);
        cal.add(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        Date result = cal.getTime();
        return sdf.format(result);
    }
    public static String  getQuarterLastDay(String etlDate,int quarter) throws ParseException {
        if(quarter>four || quarter<one)
        {
            throw  new ParseException("ERROR :get Quarter Last Day",quarter);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date base = sdf.parse(etlDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(base);
        cal.set(Calendar.DATE, 1);
        if(quarter==1)
        {
            cal.set(Calendar.MONTH, 2);
        } else if (quarter == two)
        {
            cal.set(Calendar.MONTH, 5);
        }else if (quarter == three)
        {
            cal.set(Calendar.MONTH, 8);
        }else if (quarter == four)
        {
            cal.set(Calendar.MONTH, 11);
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date result = cal.getTime();
        return sdf.format(result);
    }


    /**
     * 获取指定日期上一个月的月末日期
     * @param etldate 8位日期格式
     * @return 中文日期格式的字符串 例： 2018年08月12日
     */
    public static String getPreMonthLastDay6Num(String etldate) throws ParseException {
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dstDateFormat.parse(etldate));
        calendar.set(Calendar.DAY_OF_MONTH,0);
        SimpleDateFormat dstDateFormat6Num = new SimpleDateFormat("yyyyMM");
        return dstDateFormat6Num.format(calendar.getTime());
    }

    /**
     * 获取指定日期的月末日期
     * @param etldate 8位日期格式
     * @return 中文日期格式的字符串 例： 2018年08月12日
     */
    public static String getMonthLastDayCn(String etldate) throws ParseException {
        SimpleDateFormat dstDateFormat = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dstDateFormat.parse(etldate));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat dstDateFormatCn = new SimpleDateFormat(" yyyy 年 MM 月 dd 日");
        return dstDateFormatCn.format(calendar.getTime());
    }

    /**
     * 计算指定日期与固定月份的差值，返回计算后的月初值
     * @param etldate
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getMothFirstDay(String etldate,int month) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date base = sdf.parse(etldate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(base);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.MONTH, month);
        Date result = cal.getTime();
        return sdf.format(result);
    }

    /**
     * 计算指定日期与固定月份的差值，返回计算后的月末值
     * @param etldate
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getMothLastDay(String etldate,int month) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date base = sdf.parse(etldate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(base);
        cal.add(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date result = cal.getTime();
        return sdf.format(result);
    }
    public static String changeDateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public static long getTimeStamp(String etldate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date base = sdf.parse(etldate);
        return base.getTime();
    }
    /**
     * 获取传入日期所在月份的月末 lzhl
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }
    /**
     * 格式化时间 lzl
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    /**
     * 获得两个日期之间的所有月份；[start,  end] lzhl
     * @param start
     * @param end
     * @return
     */
    public static List<Date> getMonthsBetween(Date start, Date end)
    {
        List<Date> lstDate=new ArrayList<Date>();
        Calendar min=Calendar.getInstance();
        Calendar max=Calendar.getInstance();
        min.setTime(start);
        max.setTime(end);
        Calendar current=min;
        while (current.before(max)|| current.get(Calendar.MONTH)==max.get(Calendar.MONTH))
        {
            lstDate.add(current.getTime());
            current.add(Calendar.MONTH,1);
        }
        return lstDate;
    }
    /**
     * 判断时间是否是月末 lzhl
     * @param date
     * @return 如果是月末返回调true，否则返回false
     */
    public static  boolean isLastDayOfMonth(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE,(calendar.get(Calendar.DATE)+1));
        if(calendar.get(Calendar.DAY_OF_MONTH)==1)
        {
            return true;
        }
        return false;
    }
    /**
     * 获得指定日期月份偏移量后的月份的月末日期 lzhl
     * @param date
     * @param offsetMonth
     * @return
     */
    public static Date getLastDayOfMonth(Date date,int offsetMonth)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,offsetMonth);
        Date newDade=calendar.getTime();
        Date result=getLastDayOfMonth(newDade);
        return result;
    }
    /**
     * 获取传入日期所在月份的月初 lzhl
     * @param date
     * @return
     */
    public static Date getFirsDayOfMonth(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 获得指定日期月份偏移量后的月份的月末日期 lzhl
     * @param date
     * @param offsetMonth
     * @return
     */
    public static Date getFirsDayOfMonth(Date date, int offsetMonth)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, offsetMonth);
        Date newDade = calendar.getTime();
        Date result = getFirsDayOfMonth(newDade);
        return result;
    }

    /**
     * 将字符串转为时间类型，如果转换失败返回null lzhl
     * @param strDate 要转化时间字符串
     * @param format  要转化时间字符串的格式
     * @return 返回转化后的时间
     */
    public static Date getDate(String strDate,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date result= sdf.parse(strDate);
            return result;
        }catch (ParseException e)
        {
            return  null;
        }
    }

    /**
     * 日期格式化字符串 lzhl
     * @param date 日期
     * @param format 日期格式化字符串
     * @return
     */
    public static String dateFormat(Date date, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 日期加减 lzhl
     * @param date
     * @param offsetDay
     * @return
     */
    public static Date addDay(Date date, int offsetDay)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, offsetDay);
        return cal.getTime();
    }
    public static void main(String[] args) throws ParseException {
        System.out.println(getTimeStamp("20190213"));
        System.out.println(getDate("20190213",-1));
        System.out.println(getMothLastDay("20190213",-1));

    }
}
