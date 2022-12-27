package com.gpy.utils;

import java.math.BigDecimal;

/**
 * @ClassName CommonUtils
 * @Description
 * @Author guopy
 * @Date 2021/12/1 11:16
 */
public class CommonUtils {

    public static double div(Object d1, Object d2, int scale) {
        if (scale < 0) {
            scale = 2;
        }

        BigDecimal b1 = getDecimal(d1);
        BigDecimal b2 = getDecimal(d2);
        return b1.divide(b2, scale, 4).doubleValue();
    }

    public static BigDecimal getDecimal(Object d) {
        if (d == null) {
            return null;
        } else {
            return d instanceof BigDecimal ? (BigDecimal)d : new BigDecimal(String.valueOf(d));
        }
    }



}
