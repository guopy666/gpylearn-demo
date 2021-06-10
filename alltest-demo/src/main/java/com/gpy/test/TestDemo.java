package com.gpy.test;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        List<Long> longs = Arrays.asList(111L, 3333L, 544444L);
        String s = list2String(longs);
        System.out.println(s);
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

    public static String list2String(List<Long> longList){
        if (longList==null) {
            return null;
        }
        String result="";
        for (Long id : longList) {
            if (id==null){
                continue;
            }
            if(isBlank(result)){
                result=toString(id, null);
            }else{
                result=result+","+ toString(id, null);
            }
        }
        return result;
    }

    public static boolean isBlank(String str){
        if(StringUtils.isEmpty(str)){
            return true;
        }
        if("null".equalsIgnoreCase(str.trim())){
            return true;
        }
        return false;
    }

    public static String toString(Object value, String defaultValue){
        if(value == null){
            return defaultValue;
        }
        return String.valueOf(value).trim();
    }

}
