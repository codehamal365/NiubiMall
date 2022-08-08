package com.gl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.IntConsumer;

public class Test {
    public static int encode(int num){
        int gw=num%10;
        int sw=num/10%10;
        int bw=num/100%10;
        int qw=num/1000%10;
        gw = (gw + 5)%10;
        sw = (sw + 5)%10;
        bw = (bw + 5)%10;
        qw = (qw + 5)%10;

        return gw * 1000 + qw + sw*100 + bw * 10;
    }

    public static void main(String[] args) throws ParseException {
        List<Date> format = Test.format("2021-03-04,2022-06-25,,");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Date date : format) {
            String format1 = sdf.format(date);
            System.out.println(format1);
        }
    }

    public static List<Date> format(String date) throws ParseException {
        List<Date> dateList = new ArrayList<>();
        // 分割字符串
        String[] split = date.split(",");
        // 定义解析格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 定义空值计数器
        int count = 0;
        for (String dateStr : split) {
            // 将日期解析为Data
            if(!"".equals(dateStr)){
                // 定义日期类
                Calendar cal = Calendar.getInstance();
                // 解析日期
                Date parseDate = sdf.parse(dateStr);
                cal.setTime(parseDate);
                // 让日期减去90
                cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 90);
                Date endDate = cal.getTime();
                dateList.add(endDate);
            }else {
                count++;
            }
        }

        System.out.println("空值的占比为" + count/split.length);
        // 返回日期数组
        return dateList;

    }






}
