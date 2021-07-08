package com.gpy.test;

import com.google.common.base.Joiner;

import java.util.*;

/**
 * @ClassName TestDemo
 * @Description
 * @Author guopy
 * @Date 2021/5/20 11:33
 */
public class TestDemo {

    public static void main(String[] args) {
        Date dayStart = getDayStart(new Date());
        System.out.println(dayStart);
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(3);
        list.add(2);
        String join = Joiner.on(",").join(list);
        System.out.println(join);

        System.out.println("---" + list.toString().substring(0, list.size()-1));


        int pageSize = 10;
        int totalNum = 101;
        int threadNum=(totalNum-1)/pageSize+1;
        System.out.println(threadNum);

    }

    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }



    public static String toString(Object value, String defaultValue){
        if(value == null){
            return defaultValue;
        }
        return String.valueOf(value).trim();
    }

}
