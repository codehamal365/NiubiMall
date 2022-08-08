package com.gl.utlils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderIdUtil {
    /**
     * 获取YYYY-MM-DD HH:mm:ss:SS格式
     * @return
     */
    public static String getTime() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("时间戳：" + sdfTime.format(new Date()));
        return sdfTime.format(new Date());
    }
    /**
     * 随机生成六位数随机码
     * @return
     */
    public static int getRandomNum(){
        //r.nextInt(900000)+100000;
        return (int)(Math.random()*999999);
    }
    public static String getOrderId(){
        String s = getTime().replaceAll("[[\\s-:punct:]]", "");
        return s+getRandomNum();
    }

}
