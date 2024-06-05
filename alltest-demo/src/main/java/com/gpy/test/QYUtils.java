package com.gpy.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 常用工具类
 * @author Meron
 *
 */
public class QYUtils {


	/**
	 * value 转为整型，如果value是浮点型的，直接去掉末尾
	 * @param value
	 * @return
	 */
	public static Integer toInteger(Object value){
		return toInteger(value, null);
	}
	public static BigDecimal toDecimal(Object value) {
		return toDecimal(value, 2);
	}

	public static Long toLong(Object value) {
		return toLong(value, null);
	}

	public static Long toLong(Object value, Long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		Long result = defaultValue;
		try {
			result = (Long) value;
			return result;
		} catch (ClassCastException cce) {
			if (value instanceof Double) {
				return ((Double) value).longValue();
			} else if (value instanceof Float) {
				return ((Float) value).longValue();
			}
			try {
				result = Long.parseLong(value.toString().trim());
				return result;
			} catch (Exception e) {
				//e.printStackTrace();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return defaultValue;
	}

	public static BigDecimal toDecimal(Object value, int scale) {
		if (value == null) {
			return null;
		}
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		try {
			Double doubleValue = toDouble(value, 0.00d);
			String decimalString = getDecimalAutoStr(doubleValue, scale);
			BigDecimal decimal = new BigDecimal(decimalString);
			return decimal;
		} catch (Exception e) {
			return null;
		}
	}
	public static BigDecimal toDecimal(Object value, BigDecimal defaultValue){
		if(value == null){
			return defaultValue;
		}
		if(value instanceof BigDecimal){
			return (BigDecimal)value;
		}
		try{
			Double doubleValue = toDouble(value, 0.00d);
			String decimalString = getDecimalAutoStr(doubleValue, 2);
			BigDecimal decimal = new BigDecimal(decimalString);
			return decimal;
		}catch(Exception e){
			return defaultValue;
		}
	}

	public static boolean isNotNull0(Integer a){
		if(a == null){
			return false;
		}
		return (a.compareTo(0) != 0);
	}

	public static Double toDouble(Object value, Double defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		Double result = defaultValue;
		try {
			String valueClass = value.getClass().getSimpleName();
			//基本类型数据
			if ("Double".indexOf(valueClass) != -1) {
				result = (Double) value;
			} else {
				result = Double.parseDouble(value.toString().trim());
			}
			return result;

		} catch (ClassCastException cce) {
			try {
				result = Double.parseDouble(value.toString().trim());
				return result;
			} catch (Exception e) {
				//e.printStackTrace();
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return defaultValue;
	}


	public static String getDecimalAutoStr(double decimal, int scale){
		NumberFormat nf = NumberFormat.getInstance();
		// 设置是否使用分组
		nf.setGroupingUsed(false);
		// 设置最小整数位数
		nf.setMaximumFractionDigits(scale);

		return nf.format(decimal);
	}


	public static String toString(Object value){
		return toString(value, null);
	}
	public static String toString(Object value, String defaultValue){
		if(value == null){
			return defaultValue;
		}
		if(isBlank(String.valueOf(value))){
			return defaultValue;
		}
		return String.valueOf(value).trim();
	}
	public static List<String> toStringList(List<Object> list){
		List<String> result = new ArrayList<String>();
		if(list != null){
			for(Object obj : list){
				String temp = QYUtils.toString(obj);
				if(temp != null){
					result.add(temp);
				}
			}
		}
		return result;
	}

	public static boolean isBlank(String str){
		if(StringUtils.isBlank(str)){
			return true;
		}
		if("null".equalsIgnoreCase(str.trim())){
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}

	public static boolean isNull0(BigDecimal a){
		if(a == null){
			return true;
		}
		return (a.compareTo(BigDecimal.ZERO) == 0);
	}

	public static BigDecimal mulBD(Object d1,Object d2){
		BigDecimal b1 = getDecimal(d1);
		BigDecimal b2 = getDecimal(d2);
		return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal getDecimal(Object d){
		if(d == null){
			return null;
		}
		if(d instanceof BigDecimal){
			return (BigDecimal)d;
		}
		return new BigDecimal(String.valueOf(d));
	}
	public static BigDecimal addBD(Object d1, Object d2){
		BigDecimal b1 = getDecimal(d1);
		BigDecimal b2 = getDecimal(d2);
		return b1.add(b2);
	}
	public static double div(Object d1,Object d2){
		return div(d1, d2, 2);
	}
	/**
	 * d1/d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal divBD(Object d1,Object d2){
		return divBD(d1, d2, 2);
	}

	/**
	 * d1/d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
	 * @param d1
	 * @param d2
	 * @param scale 四舍五入保留小数位数
	 * @return
	 */
	public static double div(Object d1, Object d2, int scale){
		if(scale<0){
			//throw new IllegalArgumentException("The scale must be a positive integer or zero");
			scale = 2;
		}
		BigDecimal b1 = getDecimal(d1);
		BigDecimal b2 = getDecimal(d2);
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static BigDecimal divBD(Object d1, Object d2, int scale){
		if(scale<0){
			scale = 2;
		}
		BigDecimal b1 = getDecimal(d1);
		BigDecimal b2 = getDecimal(d2);
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);
	}
	public static Integer toInteger(Object value, Integer defaultValue) {
		if (value == null) {
			return defaultValue;
		} else {
			Integer result = null;

			try {
				result = (Integer)value;
				return result;
			} catch (ClassCastException var7) {
				String clazz = value.getClass().getSimpleName();
				if ("Double".equals(clazz)) {
					result = ((Double)value).intValue();
					return result;
				}

				if ("Float".equals(clazz)) {
					result = ((Float)value).intValue();
					return result;
				}

				if ("BigDecimal".equals(clazz)) {
					result = ((BigDecimal)value).intValue();
					return result;
				}

				if ("Boolean".equals(clazz)) {
					result = (Boolean)value ? 1 : 0;
					return result;
				}

				try {
					String valStr = value.toString().trim().replaceAll("\\..*$", "");
					if ("true".equalsIgnoreCase(valStr)) {
						result = 1;
					} else if ("false".equalsIgnoreCase(valStr)) {
						result = 0;
					} else {
						result = Integer.parseInt(valStr);
					}

					return result;
				} catch (Exception var6) {
				}
			} catch (Exception var8) {
			}

			return defaultValue;
		}
	}

	public static JSONArray toJsonArray(Object obj) {
		if (obj == null) {
			return new JSONArray();
		}
		try {
			JSONArray json = JSON.parseArray(obj.toString());
			if (json == null) {
				return new JSONArray();
			} else {
				return json;
			}
		} catch (Exception e) {
			return new JSONArray();
		}
	}

	/**
	 * 将 Date 转化为指定格式的String
	 * 月(M)、月(Y中文月)、日(d)、小时(H)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
	 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
	 * 例子：
	 * formatDate((new Date()), "yyyy-MM-dd HH:mm:ss") ==> 2006-07-02 08:09:04
	 * formatDate((new Date()), "yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
	 */
	public static String formatDate(Object date, String format){
		if(date==null){
			return "";
		}
		if(QYUtils.isBlank(format)){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		String clazz = date.getClass().getSimpleName();
		if("Date".equals(clazz)){
			return DateUtils.DateToString((Date)date, format);
		} else if("Long".equals(clazz)){
			return DateUtils.DateToString((Long)date, format);
		} else if("String".equals(clazz)){
			return (String)date;
		}
		return "";
	}

	public static String leftPad(Object obj, int len, char padding){
		return strPad(obj, len, padding, true);
	}
	public static String rightPad(Object obj, int len, char padding){
		return strPad(obj, len, padding, false);
	}
	public static String strPad(Object obj, int len, char padding, boolean left){
		String str = obj==null? "" : String.valueOf(obj);

		int diff = len - str.length();
		if(diff <= 0){
			return str;
		}

		StringBuilder sb = new StringBuilder();
		for(int i=0; i<diff; i++){
			sb.append(padding);
		}

		if(left){
			return sb.append(str).toString();
		} else {
			return str+sb.toString();
		}
	}

	/**
	 * 连接成字符串
	 * @param objs
	 * @return
	 */
	public static String join(Object...objs){
		StringBuilder sb = new StringBuilder();
		for(Object obj : objs){
			sb.append(obj);
		}
		return sb.toString();
	}

	public static Date toDate(Object value){
		return toDate(value, null);
	}
	/**
	 *
	 * @param value
	 * @param format
	 * @return
	 */
	public static Date toDate(Object value, String format){
		if(value == null){
			return null;
		}
		if("0".equals(value+"")){
			return null;
		}
		Date result = null;
		try{
			result = (Date)value;
			return result;
		}catch(ClassCastException cce){
			String strValue = value.toString().trim();
			strValue = strValue.replaceAll("\\s+", " ").replaceAll("\\s0000$", " +0000");//ios传过来的时区信息
			if (strValue.matches("^\\d+$")) {//long
				try {
					long timeLong = Long.parseLong(strValue);
					return new Date(timeLong);
				} catch (Exception e) {
					throw e;
				}
			} else {
				if(isBlank(format)){
					DateFormat[] acceptDateFormats = {
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z"),
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
							new SimpleDateFormat("yyyy-MM-dd"),
							new SimpleDateFormat("yyyy年MM月dd日"),
							new SimpleDateFormat("yyyy-M-d HH:m:s"),
							new SimpleDateFormat("yyyy-MM-dd HH:mm"),
							new SimpleDateFormat("yyyy-M-d"),
							new SimpleDateFormat("yyyy年M月d日"),
							new SimpleDateFormat("yyyy/MM/dd"),
							new SimpleDateFormat("yyyy/M/d"),
							new SimpleDateFormat("yyyy-MM"),
							new SimpleDateFormat("yyyy-M"),
							new SimpleDateFormat("yyyy年MM月"),
							new SimpleDateFormat("yyyy年M月")
					};
					for (DateFormat f : acceptDateFormats) {
						try {
							return f.parse(strValue);
						} catch (ParseException | RuntimeException e) {
							continue;
						}
					}
				} else {
					try {
						return (new SimpleDateFormat(format)).parse(strValue);
					} catch (ParseException | RuntimeException e) {
						//e.printStackTrace();
						return null;
					}

				}
			}//long..else
		}catch(Exception e){
			//e.printStackTrace();
		}
		return null;
	}
	public static List<Date> toDateList(List<Object> list){
		List<Date> result = new ArrayList<Date>();
		if(list != null){
			for(Object obj : list){
				Date temp = QYUtils.toDate(obj);
				if(temp != null){
					result.add(temp);
				}
			}
		}
		return result;
	}
}


