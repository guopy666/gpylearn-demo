package com.gpy.test;

import com.gpy.utils.DateStyle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *日期工具类
 */
public class DateUtils {
	private StringBuffer buffer = new StringBuffer();
	private static String ZERO = "0";
	private static DateUtils date;
	public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	public static SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//时间格式化类型 年月日
	public static DateTimeFormatter ymd_dtf = DateTimeFormatter.ofPattern(DateStyle.YYYY_MM_DD.getValue());

	//时间格式化类型 年月日时分秒
	public static DateTimeFormatter ymdhms_dtf = DateTimeFormatter.ofPattern(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());

	public String getNowString() {
		Calendar calendar = getCalendar();
		buffer.delete(0, buffer.capacity());
		buffer.append(getYear(calendar));

		if (getMonth(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMonth(calendar));

		if (getDate(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getDate(calendar));
		if (getHour(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getHour(calendar));
		if (getMinute(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMinute(calendar));
		if (getSecond(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getSecond(calendar));
		return buffer.toString();
	}
	
	/**
	 * 是否是今天。根据System.currentTimeMillis() / 1000 / 60 / 60 / 24计算。
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		long day = date.getTime() / 1000 / 60 / 60 / 24;
		long currentDay = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
		return day == currentDay;
	}
	
	/**
	 * 
	 * @param date
	 *            判断是否是本周，取出日期，依据日期取出该日所在周所有日期，在依据这些日期是否和本日相等
	 * @return
	 */
	public static boolean isThisWeek(Date date) {
		List<Date> dates = dateToWeek(date);
		Boolean flag = false;
		for (Date d : dates) {
			if (isToday(d)) {
				flag = true;
				break;
			} else {
				continue;
			}
		}
		return flag;
	}

	/**
	 * 
	 * @param date
	 *            判断是否是本月的日期
	 * @return
	 */
	public static boolean isThisMonth(Date date) {
		long year = date.getYear();
		long month = date.getMonth();
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime().getYear() == year
				&& calendar.getTime().getMonth() == month;
	}

	/**
	 * 
	 * @param date
	 *            判断是否是本年的日期
	 * @return
	 */
	public static boolean isThisYear(Date date) {
		long year = date.getYear();
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime().getYear() == year;
	}


	private static int getDateField(Date date, int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}

	public static int getYearsBetweenDate(Date begin, Date end) {
		int bYear = getDateField(begin, Calendar.YEAR);
		int eYear = getDateField(end, Calendar.YEAR);
		return eYear - bYear;
	}
	
	public static String parseDate(Date date, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		String dateString;
		dateString = formater.format(date);
		return dateString;
	}

	public static Date afterDate(Date date, Integer after) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + after);
		return calendar.getTime();
	}


	/**
	 * 
	 * @param mdate
	 *            取出改日的一周所有日期
	 * @return
	 */
	public static List<Date> dateToWeek(Date mdate) {
		int day = mdate.getDay();
		Date fdate;
		List<Date> list = new ArrayList();
		Long fTime = mdate.getTime() - day * 24 * 3600000;
		for (int i = 0; i < 7; i++) {
			fdate = new Date();
			fdate.setTime(fTime + (i * 24 * 3600000));
			list.add(i, fdate);
		}
		return list;
	}
	
	public static Double diffTwoDate(Date begin, Date end) {
		return (end.getTime() - begin.getTime()) / 1000 / 60.0;
	}

	public static int getMonthsBetweenDate(Date begin, Date end) {
		int bMonth = getDateField(begin, Calendar.MONTH);
		int eMonth = getDateField(end, Calendar.MONTH);
		return eMonth - bMonth;
	}

	public static int getWeeksBetweenDate(Date begin, Date end) {
		int bWeek = getDateField(begin, Calendar.WEEK_OF_YEAR);
		int eWeek = getDateField(end, Calendar.WEEK_OF_YEAR);
		return eWeek - bWeek;
	}

	public static int getDaysBetweenDate(Date begin, Date end) {
		return (int) ((end.getTime()-begin.getTime())/(1000 * 60 * 60 * 24));
	}
	/**判断是否超过24小时
	   *   
	   * @param beginTime
	   * @param endTime
	   * @return boolean
	   * @throws Exception
	*/
    public static boolean isExcess24(Date beginTime, Date endTime) throws Exception { 
        long cha = endTime.getTime() - beginTime.getTime(); 
        if(cha<0){
          return false; 
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if(result<=24){ 
             return true; 
        }else{ 
             return false; 
        } 
    }

	/**判断是否超过1小时
	 *
	 * @param beginTime
	 * @param endTime
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isExcess1(Date beginTime, Date endTime) throws Exception {
		long cha = endTime.getTime() - beginTime.getTime();
		if(cha<0){
			return false;
		}
		double result = cha * 1.0 / (1000 * 60 * 60);
		if(result<=1){
			return true;
		}else{
			return false;
		}
	}

	public static void main(String args[]) {
//		System.out.println(getSpecficDateStart(new Date(), 288));
	//	System.out.println( ((100-9000)/(1000 * 60 * 60 * 24)));1565756474865
	//	System.out.println(timeCompare(new Date(),"20:00:00","22:00:00")+"dddddddddd");
		System.out.println(getDateBefore(new Date(),7));
    }

	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date, int amount) {
		Date temp = getStartDate(getSpecficYearStart(date, amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date, amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekEnd(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}

	public static Date getSpecficDateStart(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}

	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = format.format(date);
		temp += " 23:59:59";

		try {
			return format1.parse(temp);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = format.format(date);
		temp += " 00:00:00";

		try {
			return format1.parse(temp);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param date
	 *            指定比较日期
	 * @param compareDate
	 * @return
	 */
	public static boolean isInDate(Date date, Date compareDate) {
		if (compareDate.after(getStartDate(date))
				&& compareDate.before(getFinallyDate(date))) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 获取两个时间的差值秒
	 * @param d1 靠前的时间
	 * @param d2 靠后的时间
	 * @return
	 */
	public static Integer getSecondBetweenDate(Date d1,Date d2){
		Long second=(d2.getTime()-d1.getTime())/1000;
		return second.intValue();
	}

	/**
	 * 将秒转换为年月日时分秒
	 * 如：75--1分15秒
	 * @param seconds
	 * @return
	 */
	public static String getSecondsShow(int seconds){
		if(seconds <= 60){
			return QYUtils.leftPad(seconds, 2, '0') + "秒";
		}
		int secondsPerMinute = 60;
		int secondsPerHour = 60 * secondsPerMinute;
		int secondsPerDay = 24 * secondsPerHour;
		
		int days = seconds / secondsPerDay;
		int hours = (seconds % secondsPerDay) / secondsPerHour;
		int minutes = (seconds % secondsPerHour) / secondsPerMinute;
		int sec = (seconds % secondsPerMinute);
		
		boolean hasHead = false;
		StringBuilder sb = new StringBuilder();
		if(days > 0){
			sb.append(days).append("天");
			hasHead = true; 
		}
		if(hasHead){
			sb.append(QYUtils.leftPad(hours, 2, '0')).append("小时");
			hasHead = true; 
		} else if(hours > 0){
			sb.append(QYUtils.leftPad(hours, 2, '0')).append("小时");
			hasHead = true; 
		}
		
		if(hasHead){
			sb.append(QYUtils.leftPad(minutes, 2, '0')).append("分");
			hasHead = true; 
		} else if(minutes > 0){
			sb.append(QYUtils.leftPad(minutes, 2, '0')).append("分");
			hasHead = true; 
		}
		
		sb.append(QYUtils.leftPad(sec, 2, '0')).append("秒");
		
		
		return sb.toString();
	}
	
	private int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONDAY) + 1;
	}

	private int getDate(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}

	private int getHour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	private int getMinute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}

	private int getSecond(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	public static DateUtils getDateInstance() {
		if (date == null) {
			date = new DateUtils();
		}
		return date;
	}
	
	/* ----------------------------------------------
	 * ---------------------------------------------- */
	

	  /**
	   * 获取SimpleDateFormat
	   * @param parttern 日期格式
	   * @return SimpleDateFormat对象
	   * @throws RuntimeException 异常：非法日期格式
	   */
	  private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
	    return new SimpleDateFormat(parttern);
	  }

	  /**
	   * 获取日期中的某数值。如获取月份
	   * @param date 日期
	   * @param dateField 日期字段，如：Calendar.HOUR_OF_DAY
	   * @return 数值
	   */
	  private static int getInteger(Date date, int dateField) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar.get(dateField);
	  }
	  
	  /**
	   * 增加日期中某类型的某数值。如增加日期
	   * @param date 日期字符串
	   * @param dateField 日期字段，如：Calendar.HOUR_OF_DAY
	   * @param amount 数值
	   * @return 计算后日期字符串
	   */
	  private static String addInteger(String date, int dateField, int amount) {
	    String dateString = null;
	    DateStyle dateStyle = getDateStyle(date);
	    if (dateStyle != null) {
	      Date myDate = StringToDate(date, dateStyle);
	      myDate = addInteger(myDate, dateField, amount);
	      dateString = DateToString(myDate, dateStyle);
	    }
	    return dateString;
	  }
	  
	  /**
	   * 增加日期中某类型的某数值。如增加日期
	   * @param date 日期
	   * @param dateField  日期字段，如：Calendar.HOUR_OF_DAY
	   * @param amount 数值
	   * @return 计算后日期
	   */
	  private static Date addInteger(Date date, int dateField, int amount) {
	    Date myDate = null;
	    if (date != null) {
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(date);
	      calendar.add(dateField, amount);
	      myDate = calendar.getTime();
	    }
	    return myDate;
	  }

	  /**
	   * 获取精确的日期
	   * @param timestamps 时间long集合
	   * @return 日期
	   */
	  private static Date getAccurateDate(List<Long> timestamps) {
	    Date date = null;
	    long timestamp = 0;
	    Map<Long, long[]> map = new HashMap<Long, long[]>();
	    List<Long> absoluteValues = new ArrayList<Long>();

	    if (timestamps != null && timestamps.size() > 0) {
	      if (timestamps.size() > 1) {
	        for (int i = 0; i < timestamps.size(); i++) {
	          for (int j = i + 1; j < timestamps.size(); j++) {
	            long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
	            absoluteValues.add(absoluteValue);
	            long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
	            map.put(absoluteValue, timestampTmp);
	          }
	        }

	        // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
	        long minAbsoluteValue = -1;
	        if (!absoluteValues.isEmpty()) {
	          // 如果timestamps的size为2，这是差值只有一个，因此要给默认值
	          minAbsoluteValue = absoluteValues.get(0);
	        }
	        for (int i = 0; i < absoluteValues.size(); i++) {
	          for (int j = i + 1; j < absoluteValues.size(); j++) {
	            if (absoluteValues.get(i) > absoluteValues.get(j)) {
	              minAbsoluteValue = absoluteValues.get(j);
	            } else {
	              minAbsoluteValue = absoluteValues.get(i);
	            }
	          }
	        }

	        if (minAbsoluteValue != -1) {
	          long[] timestampsLastTmp = map.get(minAbsoluteValue);
	          if (absoluteValues.size() > 1) {
	            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
	          } else if (absoluteValues.size() == 1) {
	            // 当timestamps的size为2，需要与当前时间作为参照
	            long dateOne = timestampsLastTmp[0];
	            long dateTwo = timestampsLastTmp[1];
	            if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
	              timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
	            } else {
	              long now = new Date().getTime();
	              if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
	                timestamp = dateOne;
	              } else {
	                timestamp = dateTwo;
	              }
	            }
	          }
	        }
	      } else {
	        timestamp = timestamps.get(0);
	      }
	    }

	    if (timestamp != 0) {
	      date = new Date(timestamp);
	    }
	    return date;
	  }

	  /**
	   * 判断字符串是否为日期字符串
	   * @param date 日期字符串
	   * @return true or false
	   */
	  public static boolean isDate(String date) {
	    boolean isDate = false;
	    if (date != null) {
	      if (StringToDate(date) != null) {
	        isDate = true;
	      }
	    }
	    return isDate;
	  }

	  /**
	   * 获取日期字符串的日期风格。失敗返回null。
	   * @param date 日期字符串
	   * @return 日期风格
	   */
	  public static DateStyle getDateStyle(String date) {
	    DateStyle dateStyle = null;
	    Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
	    List<Long> timestamps = new ArrayList<Long>();
	    for (DateStyle style : DateStyle.values()) {
	      Date dateTmp = StringToDate(date, style.getValue());
	      if (dateTmp != null) {
	        timestamps.add(dateTmp.getTime());
	        map.put(dateTmp.getTime(), style);
	      }
	    }
	    dateStyle = map.get(getAccurateDate(timestamps).getTime());
	    return dateStyle;
	  }

	  /**
	   * 将日期字符串转化为日期。失败返回null。
	   * @param date 日期字符串
	   * @return 日期
	   */
	  public static Date StringToDate(String date) {
	    DateStyle dateStyle = null;
	    return StringToDate(date, dateStyle);
	  }

	  /**
	   * 将日期字符串转化为日期。失败返回null。
	   * @param date 日期字符串
	   * @param parttern 日期格式
	   * @return 日期
	   */
	  public static Date StringToDate(String date, String parttern) {
	    Date myDate = null;
	    if (date != null && date.trim().length()>0) {
	      if(date.matches("^14\\d+$")){//毫秒值
	              try {
	                long timeLong = Long.parseLong(date);
	                  return new Date(timeLong);
	              } catch (Exception e) {}
	      }
	      try {
	        myDate = getDateFormat(parttern).parse(date);
	      } catch (Exception e) {}
	    }
	    return myDate;
	  }

	  /**
	   * 将日期字符串转化为日期。失败返回null。
	   * @param date 日期字符串
	   * @param dateStyle 日期风格
	   * @return 日期
	   */
	  public static Date StringToDate(String date, DateStyle dateStyle) {
	    Date myDate = null;
	    if(date.matches("^14\\d+$")){//毫秒值
	            try {
	              long timeLong = Long.parseLong(date);
	                return new Date(timeLong);
	            } catch (Exception e) {}
	        }
	    if (dateStyle == null) {
	      List<Long> timestamps = new ArrayList<Long>();
	      for (DateStyle style : DateStyle.values()) {
	        Date dateTmp = StringToDate(date, style.getValue());
	        if (dateTmp != null) {
	          timestamps.add(dateTmp.getTime());
	        }
	      }
	      myDate = getAccurateDate(timestamps);
	    } else {
	      myDate = StringToDate(date, dateStyle.getValue());
	    }
	    return myDate;
	  }
	  /**
	   * 将日期转化为日期字符串。失败返回null。
	   * @param date 日期
	   * @param parttern 日期格式
	   * @return 日期字符串
	   */
	  public static String DateToString(long date, String parttern) {
		  return DateToString(new Date(date), parttern);
	  }
	  /**
	   * 将日期转化为日期字符串。失败返回null。
	   * @param date 日期
	   * @param parttern 日期格式
	   * @return 日期字符串
	   */
	  public static String DateToString(Date date, String parttern) {
	    String dateString = "";
	    if (date != null) {
	      try {
	        dateString = getDateFormat(parttern).format(date);
	      } catch (Exception e) {
	      }
	    }
	    return dateString;
	  }
	  /**
	   * 将日期转化为日期字符串。失败返回null。
	   * @param date 日期
	   * @param format 日期格式
	   * @return 日期字符串
	   */
	  public static String DateToString(Date date, SimpleDateFormat format) {
	    String dateString = "";
	    if (date != null) {
	      try {
	        dateString = format.format(date);
	      } catch (Exception e) {
	      }
	    }
	    return dateString;
	  }

	  /**
	   * 将日期转化为日期字符串。失败返回null。
	   * @param date 日期
	   * @param dateStyle 日期风格
	   * @return 日期字符串
	   */
	  public static String DateToString(Date date, DateStyle dateStyle) {
	    String dateString = "";
	    if (dateStyle != null) {
	      dateString = DateToString(date, dateStyle.getValue());
	    }
	    return dateString;
	  }

	  /**
	   * 将日期字符串转化为另一日期字符串。失败返回null。
	   * @param date 旧日期字符串
	   * @param parttern 新日期格式
	   * @return 新日期字符串
	   */
	  public static String StringToString(String date, String parttern) {
	    return StringToString(date, null, parttern);
	  }

	  /**
	   * 将日期字符串转化为另一日期字符串。失败返回null。
	   * @param date 旧日期字符串
	   * @param dateStyle 新日期风格
	   * @return 新日期字符串
	   */
	  public static String StringToString(String date, DateStyle dateStyle) {
	    return StringToString(date, null, dateStyle);
	  }

	  /**
	   * 将日期字符串转化为另一日期字符串。失败返回null。
	   * @param date 旧日期字符串
	   * @param olddParttern 旧日期格式
	   * @param newParttern 新日期格式
	   * @return 新日期字符串
	   */
	  public static String StringToString(String date, String olddParttern, String newParttern) {
	    String dateString = null;
	    if (olddParttern == null) {
	      DateStyle style = getDateStyle(date);
	      if (style != null) {
	        Date myDate = StringToDate(date, style.getValue());
	        dateString = DateToString(myDate, newParttern);
	      }
	    } else {
	      Date myDate = StringToDate(date, olddParttern);
	      dateString = DateToString(myDate, newParttern);
	    }
	    return dateString;
	  }

	  /**
	   * 将日期字符串转化为另一日期字符串。失败返回null。
	   * @param date 旧日期字符串
	   * @param olddDteStyle 旧日期风格
	   * @param newDateStyle 新日期风格
	   * @return 新日期字符串
	   */
	  public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
	    String dateString = null;
	    if (olddDteStyle == null) {
	      DateStyle style = getDateStyle(date);
	      dateString = StringToString(date, style.getValue(), newDateStyle.getValue());
	    } else {
	      dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
	    }
	    return dateString;
	  }

	  /**
	   * 增加日期的年份。失败返回null。
	   * @param date 日期
	   * @param yearAmount 增加数量。可为负数
	   * @return 增加年份后的日期字符串
	   */
	  public static String addYear(String date, int yearAmount) {
	    return addInteger(date, Calendar.YEAR, yearAmount);
	  }
	  
	  /**
	   * 增加日期的年份。失败返回null。
	   * @param date 日期
	   * @param yearAmount 增加数量。可为负数
	   * @return 增加年份后的日期
	   */
	  public static Date addYear(Date date, int yearAmount) {
	    return addInteger(date, Calendar.YEAR, yearAmount);
	  }
	  
	  /**
	   * 增加日期的月份。失败返回null。
	   * @param date 日期
	   * @param yearAmount 增加数量。可为负数
	   * @return 增加月份后的日期字符串
	   */
	  public static String addMonth(String date, int monthAmount) {
	    return addInteger(date, Calendar.MONTH, monthAmount);
	  }
	  
	  /**
	   * 增加日期的月份。失败返回null。
	   * @param date 日期
	   * @param yearAmount 增加数量。可为负数
	   * @return 增加月份后的日期
	   */
	  public static Date addMonth(Date date, int monthAmount) {
	    return addInteger(date, Calendar.MONTH, monthAmount);
	  }
	  
	  /**
	   * 增加日期的天数。失败返回null。
	   * @param date 日期字符串
	   * @param dayAmount 增加数量。可为负数
	   * @return 增加天数后的日期字符串
	   */
	  public static String addDay(String date, int dayAmount) {
	    return addInteger(date, Calendar.DATE, dayAmount);
	  }

	  /**
	   * 增加日期的天数。失败返回null。
	   * @param date 日期
	   * @param dayAmount 增加数量。可为负数
	   * @return 增加天数后的日期
	   */
	  public static Date addDay(Date date, int dayAmount) {
	    return addInteger(date, Calendar.DATE, dayAmount);
	  }
	  
	  /**
	   * 增加日期的小时。失败返回null。
	   * @param date 日期字符串
	   * @param hourAmount 增加数量。可为负数
	   * @return 增加小时后的日期字符串
	   */
	  public static String addHour(String date, int hourAmount) {
	    return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	  }

	  /**
	   * 增加日期的小时。失败返回null。
	   * @param date 日期
	   * @param hourAmount 增加数量。可为负数
	   * @return 增加小时后的日期
	   */
	  public static Date addHour(Date date, int hourAmount) {
	    return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	  }
	  
	  /**
	   * 增加日期的分钟。失败返回null。
	   * @param date 日期字符串
	   * @param minuteAmount 增加数量。可为负数
	   * @return 增加分钟后的日期字符串
	   */
	  public static String addMinute(String date, int minuteAmount) {
	    return addInteger(date, Calendar.MINUTE, minuteAmount);
	  }

	  /**
	   * 增加日期的分钟。失败返回null。
	   * @param date 日期
	   * @param minuteAmount 增加数量。可为负数
	   * @return 增加分钟后的日期
	   */
	  public static Date addMinute(Date date, int minuteAmount) {
	    return addInteger(date, Calendar.MINUTE, minuteAmount);
	  }
	  
	  /**
	   * 增加日期的秒钟。失败返回null。
	   * @param date 日期字符串
	   * @param secondAmount 增加数量。可为负数
	   * @return 增加秒钟后的日期字符串
	   */
	  public static String addSecond(String date, int secondAmount) {
	    return addInteger(date, Calendar.SECOND, secondAmount);
	  }

	  /**
	   * 增加日期的秒钟。失败返回null。
	   * @param date 日期
	   * @param secondAmount 增加数量。可为负数
	   * @return 增加秒钟后的日期
	   */
	  public static Date addSecond(Date date, int secondAmount) {
	    return addInteger(date, Calendar.SECOND, secondAmount);
	  }

	  /**
	   * 获取日期的年份。失败返回0。
	   * @param date 日期字符串
	   * @return 年份
	   */
	  public static int getYear(String date) {
	    return getYear(StringToDate(date));
	  }

	  /**
	   * 获取日期的年份。失败返回0。
	   * @param date 日期
	   * @return 年份
	   */
	  public static int getYear(Date date) {
	    return getInteger(date, Calendar.YEAR);
	  }

	  /**
	   * 获取日期的月份。失败返回0。
	   * @param date 日期字符串
	   * @return 月份
	   */
	  public static int getMonth(String date) {
	    return getMonth(StringToDate(date)) + 1;
	  }

	  /**
	   * 获取日期的月份。失败返回0。
	   * @param date 日期
	   * @return 月份
	   */
	  public static int getMonth(Date date) {
	    return getInteger(date, Calendar.MONTH) + 1;
	  }

	  /**
	   * 获取日期的天数。失败返回0。
	   * @param date 日期字符串
	   * @return 天
	   */
	  public static int getDay(String date) {
	    return getDay(StringToDate(date));
	  }

	  /**
	   * 获取日期的天数。失败返回0。
	   * @param date 日期
	   * @return 天
	   */
	  public static int getDay(Date date) {
	    return getInteger(date, Calendar.DATE);
	  }
	  
	  /**
	   * 获取日期的小时。失败返回0。
	   * @param date 日期字符串
	   * @return 小时
	   */
	  public static int getHour(String date) {
	    return getHour(StringToDate(date));
	  }

	  /**
	   * 获取日期的小时。失败返回0。
	   * @param date 日期
	   * @return 小时
	   */
	  public static int getHour(Date date) {
	    return getInteger(date, Calendar.HOUR_OF_DAY);
	  }
	  
	  /**
	   * 判断date2是否在日期上(不算时分秒)晚于date1
	   * @param date1
	   * @param date2
	   * @return
	   */
	  public static boolean afterDay(Date date1, Date date2){
	    Date day1 = setDate(date1, 0, 0, 0);
	    Date day2 = setDate(date2, 0, 0, 0);
	    return day2.after(day1);    
	  }
	  /**
	   * 判断date2是否在日期上(不算时分秒)早于date1
	   * @param date1
	   * @param date2
	   * @return
	   */
	  public static boolean beforeDay(Date date1, Date date2){
	    Date day1 = setDate(date1, 0, 0, 0);
	    Date day2 = setDate(date2, 0, 0, 0);
	    return day2.before(day1);   
	  }
	  
	  /**
	   * 判断date2和date1是否是同一天
	   * @param date1
	   * @param date2
	   * @return
	   */
	  public static boolean sameDay(Date date1, Date date2){
	    Date day1 = setDate(date1, 0, 0, 0, 0);
	    Date day2 = setDate(date2, 0, 0, 0, 0);
	    return day2.equals(day1);   
	  }
	  /**
	   * 判断date2和date1是否是同一月
	   * @param date1
	   * @param date2
	   * @return
	   */
	  public static boolean sameMonth(Date date1, Date date2){
	    String day1 = DateToString(date1, "yyyy-MM");
	    String day2 = DateToString(date2, "yyyy-MM");
	    return day2.equals(day1);   
	  }
	  /**
	   * 判断date2和date1是否是同一年
	   * @param date1
	   * @param date2
	   * @return
	   */
	  public static boolean sameYear(Date date1, Date date2){
	    int year1 = getYear(date1);
	    int year2 = getYear(date2);
	    
	    return year1 == year2;   
	  }
	  /**
	   * 设置日期中的小时值
	   * @param date
	   * @param hour
	   * @return 返回设置后的新日期，原日期不变
	   */
	  public static Date setHour(Date date, int hour){
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, hour);
	    return calendar.getTime();
	  }
	  /**
	   * 设置日期中的分钟值
	   * @param date
	   * @param minute
	   * @return 返回设置后的新日期，原日期不变
	   */
	  public static Date setMinute(Date date, int minute){
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.MINUTE, minute);
	    return calendar.getTime();
	  }
	  
	  /**
	   * 设置日期
	   * @param date
	   * @param hour - 0~23, 为-1不修改
	   * @param minute 0~59, 为-1不修改
	   * @param second 0~59, 为-1不修改
	   * @return 返回设置后的新日期，原日期不变
	   */
	  public static Date setDate(Date date, int hour, int minute, int second){
	    return setDate(date, hour, minute, second, 0);
	  }
	  /**
	   * 设置日期
	   * @param date
	   * @param hour - 0~23, 为-1不修改
	   * @param minute 0~59, 为-1不修改
	   * @param second 0~59, 为-1不修改
	   * @param millisecond 0~999, 为-1不修改
	   * @return 返回设置后的新日期，原日期不变
	   */
	  public static Date setDate(Date date, int hour, int minute, int second, int millisecond){
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    if(hour>=0 && hour<=23){
	      calendar.set(Calendar.HOUR_OF_DAY, hour);
	    }
	    if(minute>=0 && minute<=59){
	      calendar.set(Calendar.MINUTE, minute);
	    }
	    if(second>=0 && second<=59){
	      calendar.set(Calendar.SECOND, second);
	    }
	    if(millisecond>=0 && millisecond<=999){
	      calendar.set(Calendar.MILLISECOND, millisecond);
	    }
	    
	    return calendar.getTime();
	  }
	  
	  /**
	   * 获取日期的分钟。失败返回0。
	   * @param date 日期字符串
	   * @return 分钟
	   */
	  public static int getMinute(String date) {
	    return getMinute(StringToDate(date));
	  }

	  /**
	   * 获取日期的分钟。失败返回0。
	   * @param date 日期
	   * @return 分钟
	   */
	  public static int getMinute(Date date) {
	    return getInteger(date, Calendar.MINUTE);
	  }
	  
	  /**
	   * 获取日期的秒钟。失败返回0。
	   * @param date 日期字符串
	   * @return 秒钟
	   */
	  public static int getSecond(String date) {
	    return getSecond(StringToDate(date));
	  }

	  /**
	   * 获取日期的秒钟。失败返回0。
	   * @param date 日期
	   * @return 秒钟
	   */
	  public static int getSecond(Date date) {
	    return getInteger(date, Calendar.SECOND);
	  }

	  /**
	   * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
	   * @param date 日期字符串
	   * @return 日期
	   */
	  public static String getDate(String date) {
	    return StringToString(date, DateStyle.YYYY_MM_DD);
	  }

	  /**
	   * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	   * @param date 日期
	   * @return 日期
	   */
	  public static String getDate(Date date) {
	    return DateToString(date, DateStyle.YYYY_MM_DD);
	  }

	  /**
	   * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	   * @param date 日期字符串
	   * @return 时间
	   */
	  public static String getTime(String date) {
	    return StringToString(date, DateStyle.HH_MM_SS);
	  }

	  /**
	   * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	   * @param date 日期
	   * @return 时间
	   */
	  public static String getTime(Date date) {
	    return DateToString(date, DateStyle.HH_MM_SS);
	  }

	  /**
	   * 获取日期的星期。失败返回null。
	   * @param date 日期字符串
	   * @return 星期
	   */
	  public static Week getWeek(String date) {
	    Week week = null;
	    DateStyle dateStyle = getDateStyle(date);
	    if (dateStyle != null) {
	      Date myDate = StringToDate(date, dateStyle);
	      week = getWeek(myDate);
	    }
	    return week;
	  }
	  /**
	   * 获取日期的星期。失败返回null。
	   * @param date 日期字符串
	   * @return 星期
	   */
	  public static Week getWeek(String date, String pattern) {
	    Week week = null;
	    Date myDate = StringToDate(date, pattern);
	    week = getWeek(myDate);
	    return week;
	  }
	  /**
	   * 获取当前的星期。失败返回null。
	   * @param date 日期
	   * @return 星期
	   */
	  public static Week getWeek() {
	    return getWeek(new Date());
	  }
	  /**
	   * 获取日期的星期。失败返回null。
	   * @param date 日期
	   * @return 星期
	   */
	  public static Week getWeek(Date date) {
	    Week week = null;
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	    switch (weekNumber) {
	    case 0:
	      week = Week.SUNDAY;
	      break;
	    case 1:
	      week = Week.MONDAY;
	      break;
	    case 2:
	      week = Week.TUESDAY;
	      break;
	    case 3:
	      week = Week.WEDNESDAY;
	      break;
	    case 4:
	      week = Week.THURSDAY;
	      break;
	    case 5:
	      week = Week.FRIDAY;
	      break;
	    case 6:
	      week = Week.SATURDAY;
	      break;
	    }
	    return week;
	  }
	  
	  /**
	   * 判断日期是否是周末
	   * @param date
	   * @return
	   */
	  public static boolean isWeekend(Date date){
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	    if(weekNumber == 0 || weekNumber == 6){
	      return true;
	    }
	    return false;
	  }
	  
	  /**
	   * 获取两个日期相差的天数
	   * @param date 日期字符串
	   * @param otherDate 另一个日期字符串
	   * @return 相差天数
	   */
	  public static int getIntervalDays(String date, String otherDate) {
	    return getIntervalDays(StringToDate(date), StringToDate(otherDate));
	  }
	  /**
	   * 获取两个日期相差的天数
	   * @param date 日期字符串
	   * @param otherDate 另一个日期字符串
	   * @return 相差天数
	   */
	  public static int getIntervalDays(String date, String otherDate, DateStyle dateStyle) {
	    return getIntervalDays(StringToDate(date, dateStyle), StringToDate(otherDate, dateStyle));
	  }
	  /**
	   * 获取两个日期相差的天数
	   * @param date 日期字符串
	   * @param otherDate 另一个日期字符串
	   * @return 相差天数
	   */
	  public static int getIntervalDays(String date, String pattern, String otherDate, String otherPattern) {
	    return getIntervalDays(StringToDate(date, pattern), StringToDate(otherDate, otherPattern));
	  }
	  public static int getIntervalDays(String date, String otherDate, String pattern) {
	    return getIntervalDays(StringToDate(date, pattern), StringToDate(otherDate, pattern));
	  }
	  
	  /**
	   * @param date 日期
	   * @param otherDate 另一个日期
	   * @return 相差天数
	   */
	  public static int getIntervalDays(Date date, Date otherDate) {
	    long time = Math.abs(date.getTime() - otherDate.getTime());
	    return (int)(time/(24 * 60 * 60 * 1000));
	  }
	  /**
	   * @param date 日期
	   * @param otherDate 另一个日期
	   * @return 相差秒
	   */
	  public static int getIntervalSeconds(Date date, Date otherDate) {
	    long time = Math.abs(date.getTime() - otherDate.getTime());
	    return (int)(time/1000);
	  }
	  /**
	   * @param date 日期
	   * @param otherDate 另一个日期
	   * @return 相差分钟
	   */
	  public static int getIntervalMinutes(Date date, Date otherDate) {
	    long time = Math.abs(date.getTime() - otherDate.getTime());
	    return (int)(time/(60 * 1000));
	  }
	  /**
	   * @param date 日期
	   * @param otherDate 另一个日期
	   * @return 相差小时
	   */
	  public static int getIntervalHours(Date date, Date otherDate) {
	    long time = Math.abs(date.getTime() - otherDate.getTime());
	    return (int)(time/(60 * 60 * 1000));
	  }
	  /**
	   * 返回一天的零点零分
	   * @param date
	   * @return
	   */
	  public static Date getDayStart(Date date){
		if(date == null){
			date = new Date();
		}
	    Calendar calendar = Calendar.getInstance();   
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTime();
	  }
	  public static Date getDayBegin(Date date){
	    return getDayStart(date);
	  }
	  
	  /**
	   * 返回一天的23:59:59
	   * @param date
	   * @return
	   */
	  public static Date getDayEnd(Date date){
	    if(date == null){
	      date = new Date();
	    }
	    Calendar calendar = Calendar.getInstance();   
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTime();
	  }
	  /**
	   * 获取月的最后一天
	   * @param date
	   * @return
	   */
	  public static Date getLastDayOfMonth(Date date) {
	    return lastDayOfMonth(date);
	  }
	  public static Date getMonthLastDay(Date date) {
	        return lastDayOfMonth(date);
	    }
	  /**
	   * 获取月的最后一天
	   * @param date
	   * @return
	   */
	  public static Date lastDayOfMonth(Date date) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        cal.roll(Calendar.DAY_OF_MONTH, -1);
	        return cal.getTime();
	    }
	  /**
	   * 获取date所在月的最后一天数值
	   * @param date 目标月中的任何一天
	   * @return 如，28或30或31
	   */
	  public static int lastDayNumOfMonth(Date date) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.DAY_OF_MONTH, 1);
	        cal.roll(Calendar.DAY_OF_MONTH, -1);
	        
	        return cal.get(Calendar.DAY_OF_MONTH);
	  }
	  
	  /**
	   * 获取月的最后一天
	   * @return
	   */
	  public static Date lastDayOfMonth() {
	        return lastDayOfMonth(new Date());
	    }
	  
	  /**
	   * 获取月的首日
	   * @param date
	   * @return
	   */
	  public static Date getFirstDayOfMonth(Date date) {
	    return firstDayOfMonth(date);
	  }
	  public static Date getMonthFirstDay(Date date) {
	    return firstDayOfMonth(date);
	  }
	  /**
	   * 获取月的首日
	   * @param date
	   * @return
	   */
	  public static Date firstDayOfMonth(Date date) {
	    Calendar c = Calendar.getInstance();    
	    c.setTime(date);
	        c.add(Calendar.MONTH, 0);
	        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	        return c.getTime();
	  }
	  /**
	   * 获取月的首日
	   * @return
	   */
	  public static Date firstDayOfMonth() {
	    return firstDayOfMonth(new Date());
	  }
	  /**
	   * 获取月的最后一天
	   * @param year
	   * @param month
	   * @return
	   */
	  public static Date lastDayOfMonth(int year, int month) {
	    Calendar cal = Calendar.getInstance();     
	        cal.set(Calendar.YEAR, year);     
	        cal.set(Calendar.MONTH, month-1);     
	        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
	        return cal.getTime();
	  }
	  /**
	   * 获取月的首日
	   * @param year
	   * @param month
	   * @return
	   */
	  public static Date firstDayOfMonth(int year, int month) {
	    Calendar cal = Calendar.getInstance();     
	        cal.set(Calendar.YEAR, year);     
	        cal.set(Calendar.MONTH, month-1);  
	        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
	        return cal.getTime();
	  }
	  
	  public static String getLastDayOfMonth(int year, int month) {     
	        Calendar cal = Calendar.getInstance();     
	        cal.set(Calendar.YEAR, year);     
	        cal.set(Calendar.MONTH, month-1);     
	        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));  
	       return  new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
	    }   
	    public static String getFirstDayOfMonth(int year, int month) {     
	        Calendar cal = Calendar.getInstance();     
	        cal.set(Calendar.YEAR, year);     
	        cal.set(Calendar.MONTH, month-1);  
	        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));  
	       return   new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());  
	    }  
	    
	    public static int getDaysOfMonth(Date date){      
	      String dateStr = DateToString(getLastDayOfMonth(date), DateStyle.YYYY_MM_DD);
	    String lastDate = dateStr.split("-")[2];
	    int days = Integer.parseInt(lastDate);
	    return days;
	    }
	    public static int getDaysOfMonth(int year, int month){      
	      String dateStr = DateToString(lastDayOfMonth(year, month), DateStyle.YYYY_MM_DD);
	    String lastDate = dateStr.split("-")[2];
	    int days = Integer.parseInt(lastDate);
	    return days;
	    }
	    /**
	     * 获取本月截止指定日期的周末数目
	     * @param date  yyyy-MM-dd
	     * @return
	     */
	    public static int getWeekendDaysOfDate(String date){  
	      int days=0;
	      //1:周一,...,7:周日
	      int dayOfWeek = DateUtils.getWeek(date, "yyyy-MM-dd").number;
	      int day = Integer.valueOf(date.split("-")[2]);
	      int offset = dayOfWeek - day%7;
	      int temp=0;
	      for(int i=1; i<=day; i++){
	        temp = (offset + i)%7;
	        if(temp<0){
	          temp = temp + 7;
	        }
	        //System.out.println(i+"==>"+temp);
	        if(temp==0||temp==6) days+=1;
	      }
	      return days;
	    }
	    /**
	     * 获得指定日期是一年中的第几周
	     * @param date
	     * @return
	     */
	    public static int getWeekNumOfYear(Date date){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);  
	        int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);  
	        return iWeekNum;  
	    }
	    /**
	     * 获得当前日期是一年中的第几周
	     * @return
	     */
	    public static int getWeekNumOfYear(){  
	      return getWeekNumOfYear(new Date());
	    } 
	    /**
	     * 计算某年某周的开始日期（周一为一周开始）
	     * @param year
	     * @param weekNum
	     * @return
	     */
	    public static Date getYearWeekFirstDay(int year,int weekNum){  
	        Calendar cal = Calendar.getInstance();  
	        cal.set(Calendar.YEAR, year);  
	        cal.set(Calendar.WEEK_OF_YEAR, weekNum);  
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
	        
	        return cal.getTime();
	    }
	    /**
	     * 计算指定日期所在周的开始日期（周一为一周开始）
	     * @param weekNum
	     * @return
	     */
	    public static Date getWeekFirstDay(Date date){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);  
	        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
	        return calendar.getTime();
	    }
	    /**
	     * 计算今年某周的开始日期（周一为一周开始）
	     * @param weekNum
	     * @return
	     */
	    public static Date getWeekFirstDay(int weekNum){  
	        Calendar cal = Calendar.getInstance();  
	        cal.set(Calendar.WEEK_OF_YEAR, weekNum);  
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
	        
	        return cal.getTime();
	    }
	    /**
	     * 计算某年某周的开始日期（周一为一周开始）
	     * @param year
	     * @param weekNum
	     * @return
	     */
	    public static Date getWeekFirstDay(int year,int weekNum){  
	        Calendar cal = Calendar.getInstance();  
	        cal.set(Calendar.YEAR, year);  
	        cal.set(Calendar.WEEK_OF_YEAR, weekNum);  
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
	        
	        return cal.getTime();
	    }
	    /**
	     * 计算某年某周的结束日期（周日为一周结束）
	     * @param year
	     * @param weekNum
	     * @return
	     */
	    public static Date getWeekLastDay(int year,int weekNum){  
	        Calendar cal = Calendar.getInstance();  
	        cal.set(Calendar.YEAR, year);  
	        cal.set(Calendar.WEEK_OF_YEAR, weekNum+1);  
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); 
	        
	        return cal.getTime();
	    }
	    /**
	     * 计算今年某周的结束日期（周日为一周结束）
	     * @param weekNum
	     * @return
	     */
	    public static Date getWeekLastDay(int weekNum){  
	        Calendar cal = Calendar.getInstance();  
	        cal.set(Calendar.WEEK_OF_YEAR, weekNum+1);  
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  
	        
	        return cal.getTime();
	    }
	    /**
	     * 计算今年某周的结束日期（周日为一周结束）
	     * @param weekNum
	     * @return
	     */
	    public static Date getWeekLastDay(Date date){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);  
	        calendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR)+1);  
	        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  
	        return calendar.getTime();
	    }
	    /**
	     * 判断是否为闰年
	     * @param year
	     * @return
	     */
	    public static boolean isLeapYear(int year){  
	        boolean isLeep = false;  
	        if((year % 4 == 0) && (year % 100 != 0)){  
	          isLeep = true;  
	        } else if(year % 400 ==0){  
	          isLeep = true;  
	        } else {  
	          isLeep = false;  
	        }  
	        return isLeep;  
	    }  

	    public static boolean before(Date d1, Date d2){
	      return d1.compareTo(d2) < 0;
	    }
	    public static boolean after(Date d1, Date d2){
	      return d1.compareTo(d2) > 0;
	    }
	    
	    /**
	     * 判断d是否在d1和d2直接
	     * 
	     * @param d 目标日期
	     * @param d1 日期1
	     * @param d2 日期2
	     * @return
	     */
	    public static boolean between(Date d, Date d1, Date d2){
	    	if(d == null || d1 == null || d2 == null){
	    		return false;
	    	}
	    	long time = d.getTime();
	    	long time1 = d1.getTime();
	    	long time2 = d2.getTime();
	    	long t1 = Math.min(time1, time2);
	    	long t2 = Math.max(time1, time2);
	    	
	    	return time>=t1 && time<=t2;
	    }
	    
	    /**
	     * 分解一个日期区间
	     * @param beginTime
	     * @param endTime
	     * @return
	     */
	    public static List<Date> getDays(Date beginTime, Date endTime){     
	      List<Date> list = new ArrayList<Date>();
	      if(beginTime == null || endTime == null){
	        return list;
	      }
	      if(endTime.before(beginTime)){
	        Date temp = beginTime;
	        beginTime = endTime;
	        endTime = temp;
	      }
	      
	      while(!DateUtils.getDayBegin(beginTime).equals(DateUtils.getDayBegin(endTime))){
	        list.add(DateUtils.getDayBegin(beginTime));
	        //更新beginTime，设为次日
	        beginTime = DateUtils.addDay(beginTime, 1);
	      }
	      //最后一天
	      list.add(DateUtils.getDayBegin(beginTime));       
	      
	      return list;
	    }
	    
	    /**
	   * @param arg
	   * @param format
	   */
	  public static boolean widthDateFormat(String arg, String format) {
	    if(arg == null || format == null){
	      return false;
	    }
	    if(arg.length() != format.length()){
	      return false;
	    }
	    String str = DateUtils.DateToString(DateUtils.StringToDate(arg, format), format);
	    if(!arg.equals(str)){
	      return false;
	    }   
	    return true;
	  }
	  
	  /**
	   * 分解一个日期区间
	   * @param beginTime
	   * @param endTime
	   * @return
	   */
	  public static List<String> getDayStrs(Date beginTime, Date endTime, String format){     
	    List<Date> list = getDays(beginTime, endTime);
	    List<String> dayStrs = new ArrayList<String>();
	    for(Date day : list){
	      String dayStr = DateUtils.DateToString(day, format);
	      dayStrs.add(dayStr);
	    }
	    return dayStrs;
	  }
	  /**
	   * 将日期转换为CRON表达式
	   * @param date
	   * @return cron 表达式
	   */
	  public static String date2Cron(Date date){
		  if(date == null){
			  return null;
		  }
		  String parttern = "ss mm HH dd MM ? yyyy";  
	      return DateToString(date, parttern);
	  }
	  
	  /**
	   * 秒格式化为xxx小时xxx分xxx秒
	   * @param second
	   * @return
	   */
	  public static String formatSecond(long second){
		  StringBuffer formatTime = new StringBuffer();
		  if(second == 0){
			  return "";
		  }
		  long sec = (second % 60);
		  if(sec != 0){
			  formatTime.append(sec + "秒");
		  }
		  long mins = (second / 60);
		  if(mins != 0){
			  long minsFormat = (mins % 60);
			  if(minsFormat != 0){
				  formatTime.insert(0, minsFormat + "分");
			  }
			  long hour = (mins / 60);
			  if(hour != 0){
				  formatTime.insert(0, hour + "小时");
			  }
		  }
		  return formatTime.toString();
	  }

	  /**
	   * 获取到秒的时间戳
	   * @param date
	   * @return
	   */
	  public static long getUnixTimestamp(Date date){
		  if(date == null){
			  return 0L;
		  }
		  return date.getTime() / 1000;
	  }
	  /**
	   * 获取到秒的时间戳
	   * @param date
	   * @return
	   */
	  public static long getTimestampSeconds(Date date){
		  return getUnixTimestamp(date);
	  }
	  
		/**
		 * 时间区间与当前时间做比较（0：在区间之间，1：未到开始键，2大于结束时间）
		 * 
		 * @param date
		 * @param beginTime
		 * @param endTime
		 * @return
		 */
		public static int timeCompare(Date date,String beginTime,String endTime) {
			int code = -1;
			if(date == null || QYUtils.isBlank(beginTime) || QYUtils.isBlank(endTime)){
				  return code;
			}
			String beginTimeStr = format.format(date)+" "+beginTime;
			String endTimeStr = format.format(date)+" "+endTime;
			try {
				Date beginDate =  format1.parse(beginTimeStr);
				Date endDate =  format1.parse(endTimeStr);
				if(diffTwoDate(beginDate,new Date()) > 0
						 && DateUtils.diffTwoDate(new Date(),endDate) > 0){
					code = 0;
				}else if(diffTwoDate(new Date(),beginDate) > 0){
					code = 1;
				}else if(diffTwoDate(endDate,new Date()) > 0){
					code = 2;
				}
			} catch (Exception e) {
				return code;
			}
			return code;
		}

    /**
     * 获取指定时间的上个月的第一天 00:00:00
     * @param date 指定日期
     * @return
     */
    public static String getLastMonthFirstDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        return simpleDateFormat.format(calendar1.getTime());
    }

    /**
     * 获取指定时间的上个月最后一天 23:59:59
     * @param date 指定时间
     * @return
     */
    public static String getLastMonthLastDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        return simpleDateFormat.format(calendar2.getTime());
    }

    /**
     * 获取指定时间的3个月之前的时间
     */
    public static String getThreeMonthsAgoDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -3);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取指定格式时间
     * @param date  指定的时间
     * @param dateStyle 时间格式化
     * @return
     */
    public static String getDate(Date date, DateStyle dateStyle) {
        return DateToString(date, dateStyle);
    }
	/**
	 * 获取两个月份相差的月数
	 * @param startDate 日期字符串
	 * @param endDate 另一个日期字符串
	 * @return 相差月数
	 */
	public static int getIntervalMonths(String startDate, String endDate, DateStyle dateStyle) {
		return getIntervalMonths(StringToDate(startDate, dateStyle), StringToDate(endDate, dateStyle));
	}
	public static int getIntervalMonths(Date startDate, Date endDate) {
		if(diffTwoDate(startDate,endDate)>0){
			int years = Math.abs(getYear(endDate)-getYear(startDate));
			int monthstart = getMonth(startDate);
			int monthend = getMonth(endDate);
			if(monthstart >= monthend) years --;
			return Math.abs(getMonth(endDate)+12-getMonth(startDate))%12+12*years;
		}else{
			return 0;
		}
	}
	public static int getIntervalYears (String year1,String year2){
		return Math.abs(QYUtils.toInteger(year2)- QYUtils.toInteger(year1));
	}

	/**
	 * 获取一组日期中最晚的一天，为null的则跳过，都为null则返回null
	 * @param dates
	 * @return
	 */
	public static Date getMax(Date... dates) {
		if(dates == null){
			return null;
		}
		Date maxDate = null;
		for (Date date : dates){
			if(date == null){
				continue;
			}
			if(maxDate == null){
				maxDate = date;
				continue;
			}

			if(date.after(maxDate)){
				maxDate = date;
			}
		}
		return maxDate;
	}

	/**
	 * 获取一组日期中最早的一天，为null的则跳过，都为null则返回null
	 * @param dates
	 * @return
	 */
	public static Date getMin(Date... dates) {
		if(dates == null){
			return null;
		}
		Date minDate = null;
		for (Date date : dates){
			if(date == null){
				continue;
			}
			if(minDate == null){
				minDate = date;
				continue;
			}

			if(date.before(minDate)){
				minDate = date;
			}
		}
		return minDate;
	}


	/**
	 * Date->LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime dateToLocalDateTime(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDateTime();
	}

	public static String getDateBefore(Date d, int day) {
		Calendar no = Calendar.getInstance();
		no.setTime(d);
		no.set(Calendar.DATE, no.get(Calendar.DATE) - day);
		String date=parseDate(no.getTime(),"yyyy-MM-dd");
		return date;
	}

	public static String getDateTimeBefore(Date d, int day) {
		Calendar no = Calendar.getInstance();
		no.setTime(d);
		no.set(Calendar.DATE, no.get(Calendar.DATE) - day);
		String date=parseDate(no.getTime(), "yyyy-MM-dd HH:mm:ss");
		return date;
	}

	/**
	 * Date->LocalDate
	 * @param date
	 * @return
	 */
	public static LocalDate dateToLocalDate(Date date){
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDate();
	}

	/** 是否是今天
	 * @param date
	 * @return
	 */
	public static  boolean isCurrentDay(Date date){
		LocalDate now = LocalDate.now();
		LocalDate localDate = dateToLocalDateTime(date).toLocalDate();
		return now.equals(localDate);
	}

	/**是否是明天
	 * @param date
	 * @return
	 */
	public static boolean isTomorrow(Date date){
		LocalDate param = dateToLocalDateTime(date).toLocalDate();
		LocalDate localDate = LocalDate.now().plusDays(1);
		return  localDate.equals(param);
	}

	/**
	 * 时间转换
	 * @param time 1561835118624
	 * @return
	 */
	public static Date longToDate(String time){
		Long longTime=Long.valueOf(time);
		return new Date(longTime);
	}


	/**
	 *
	 * 特殊时间转换 UTC 通用标准
	 * @param time 2015-12-7T16:00:00.000Z
	 * @return
	 */
	public static Date timeByUTC(String time){
		try{
			// String date = "2015-12-7T16:00:00.000Z";
			time = time.replace("Z", " UTC");//注意是空格+UTC
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
			Date date = format.parse(time);
			return date;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间转换
	 * @param time 2019-07-31
	 * @return
	 */
	public static Date parseDate(String time){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date  date = sdf.parse(time+" 00:00:00");
			return date;
		}catch (Exception e){

		}
		return null;
	}

	/**
	 * 两个日期比较
	 * @param date
	 * @param comDate
	 * @return
	 */
	public static boolean comparDate(Date date,Date comDate){
		try{
			String time1=DateToString(date,format3);
			String time2=DateToString(comDate,format3);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date bt=sdf.parse(time1);
			Date et=sdf.parse(time2);
			if (bt.before(et)){ //time1<time2
				return true;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 时间比较
	 * @param date
	 * @param comDate
	 * @return 0相等 date大返回1 否则返回-1
	 */
	public static int compareTime(String date,String comDate){
		int result=0;
		try{
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date oldTime1 = dateFormat.parse(date);
			Date oldTime2 = dateFormat.parse(comDate);
			result = oldTime1.compareTo(oldTime2);
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Date转LocalDate
	 * @param date
	 */
	public static LocalDate date2LocalDate(Date date) {
		if(null == date) {
			return null;
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	/**
	 * LocalDate转Date
	 * @param localDate
	 * @return Date
	 */
	public static Date localDate2Date(LocalDate localDate) {
		if (null == localDate) {
			return null;
		}
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
		return Date.from(zonedDateTime.toInstant());
	}
	/**
	 * 下一周的该星期
	 * @param date
、	 * @return Date
	 */
	public static Date nextWeekDate(Date date){
		try{
			LocalDate localDate = date2LocalDate(date);
			LocalDate localDate1 = localDate.minusWeeks(-1);
			return DateUtils.StringToDate(DateUtils.DateToString(localDate2Date(localDate1),DateStyle.YYYY_MM_DD), DateStyle.YYYY_MM_DD);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取下个月的这天
	 * @param date
	 * @return Date
	 */
	public static Date nextMonthDate(Date date){
		try{
			LocalDate localDate = date2LocalDate(date);
			LocalDate localDate1 = localDate.minusMonths(-1);
			return DateUtils.StringToDate(DateUtils.DateToString(localDate2Date(localDate1),DateStyle.YYYY_MM_DD), DateStyle.YYYY_MM_DD);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
