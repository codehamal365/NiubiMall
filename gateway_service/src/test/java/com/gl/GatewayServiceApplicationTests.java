package com.gl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class GatewayServiceApplicationTests {
    public static int getValue(int i) {
        int result = 0;
        switch (i) {
            case 1:
                result = result + i;
            case 2:
                result = result + i * 2;
            case 3:
                result = result + i * 3;
        }
        return result;
    }


    static char[] reverse(char[] data){
        for (int i = 0; i < data.length/2; i++) {
              char temp;
              temp = data[i];
              data[i] = data[data.length - i - 1];
              data[data.length - i - 1] = temp;
        }
        return data;
    }




        public static Integer backup(int time) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, time);       // 控制秒

            Date times = calendar.getTime();         // 得出执行任务的时间
            long time1 = times.getTime();
            // 执行任务

            // 得到执行完的日历
            Calendar calendar2 = Calendar.getInstance();
            Date times2 = calendar2.getTime();
            long time2 = times2.getTime();
            if (time2 > time1){
                return time;
            }else {
                return null;
            }
        }





    @Test
    void contextLoads() {
        List<String> words = new ArrayList<>();
        words.add("hello");
        words.add("world");
        List<String> distinct = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String data = words.get(i);
            for (int i1 = 0; i1 < data.length(); i1++) {
                distinct.add(data.charAt(i1)+"");
            }
        }

        List<String> collect = distinct.stream().distinct().collect(Collectors.toList());
        String arr [] = new String[collect.size()];
        for (int i = 0; i < collect.size(); i++) {
            arr[i] = collect.get(i);
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
