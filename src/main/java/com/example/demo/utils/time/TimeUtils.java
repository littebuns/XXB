package com.example.demo.utils.time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtils {

    public static final String DAY_FORMAT = "yyyy-MM-dd";
    public static final String MONTH_FORMAT = "yyyy-MM";
    public static final String WEEK = "week";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_FORMAT = "yyyy";


    public static String getStartTime(String time, String type) throws Exception {
        SimpleDateFormat yearSdf = new SimpleDateFormat(YEAR_FORMAT);
        SimpleDateFormat daySdf = new SimpleDateFormat(DAY_FORMAT);
        SimpleDateFormat monSdf = new SimpleDateFormat(MONTH_FORMAT);
        Calendar calendar = Calendar.getInstance();
        Date date = daySdf.parse(time);
        calendar.setTime(date);
        switch (type) {
            case WEEK:
                //往前推六个维度
                calendar.add(Calendar.WEEK_OF_YEAR, -5);
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                break;
            case MONTH:
                calendar.add(Calendar.MONTH, -5);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                break;
            case YEAR:
                calendar.add(Calendar.YEAR, -5);
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                break;
        }
        return daySdf.format(calendar.getTime());
    }

    //根据时间类型,获取两个时间节点之间的列表
    public static List<String> getDates(String dateType, String startTime, String endTime) throws Exception {
        List<String> list = new ArrayList<>();
        SimpleDateFormat yearSdf = new SimpleDateFormat(YEAR_FORMAT);
        SimpleDateFormat daySdf = new SimpleDateFormat(DAY_FORMAT);
        SimpleDateFormat monSdf = new SimpleDateFormat(MONTH_FORMAT);
        if (dateType.equals(YEAR)) {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(daySdf.parse(startTime));
            end.setTime(daySdf.parse(endTime));

            while (start.before(end)) {
                list.add(yearSdf.format(start.getTime()));
                start.add(Calendar.YEAR, 1);
            }
            if (start.get(Calendar.YEAR) == end.get(Calendar.YEAR)){
                list.add(yearSdf.format(start.getTime()));
            }
        } else if (dateType.equals(MONTH)) {
            //获取一个Calendar类
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            //设置Calendar的时间
            start.setTime(daySdf.parse(startTime));
            end.setTime(daySdf.parse(endTime));
            //如果没有超过endTime,放入list,将时间往后挪一个维度
            while (start.before(end)) {
                list.add(monSdf.format(start.getTime()));
                start.add(Calendar.MONTH, 1);
            }
            //时间往后挪，防止直接超过endTime
            if (start.get(Calendar.MONTH) == end.get(Calendar.MONTH)){
                list.add(monSdf.format(start.getTime()));
            }
        } else if (dateType.equals(WEEK)) {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            start.setTime(daySdf.parse(startTime));
            end.setTime(daySdf.parse(endTime));
            //改成统计周一到周日
            end.add(Calendar.DAY_OF_YEAR, -1);
            start.add(Calendar.DAY_OF_YEAR, -1);
            while (start.before(end)) {
                String str = "";
                int i = start.get(Calendar.WEEK_OF_YEAR);
                //对1到9周转成01-09好与数据库结果进行匹配
                if (i>0 && i<10){
                    str = "0"+String.valueOf(i);
                } else {
                    str = String.valueOf(i);
                }
                list.add(yearSdf.format(start.getTime())+str);
                start.add(Calendar.WEEK_OF_YEAR, 1);
            }
            if (start.get(Calendar.WEEK_OF_YEAR) == end.get(Calendar.WEEK_OF_YEAR)){
                String str = "";
                int i = start.get(Calendar.WEEK_OF_YEAR);
                if (i>0 && i<10){
                    str = "0"+String.valueOf(i);
                } else {
                    str = String.valueOf(i);
                }
                list.add(yearSdf.format(start.getTime())+str);
            }
        }
        return list;
    }


    public static void main(String[] args) throws Exception {

        String year = TimeUtils.getStartTime("2020-11-03 16:16:34", "s");
        System.out.println(year);
        List<String> dates = TimeUtils.getDates("week", "2020-1-06 00:00:00", "2020-1-12 00:00:00");
        System.out.println(dates);
    }

}
