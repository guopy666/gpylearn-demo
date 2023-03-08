package com.gpy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.google.common.collect.Lists;
import com.gpy.test.DateUtils;
import com.sun.deploy.net.HttpUtils;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 常用工具类
 * @author Meron
 *
 */
public class QYUtils {
	
	private static final Log log = LogFactory.getLog(QYUtils.class);
	static Map<String, String> map = null;
	static Map<String,String> emojiUrlConvertMap=new HashMap<String,String>();
	static Map<String,String> emojiFieldsConvertMap=new HashMap<String,String>();
	static{
		emojiUrlConvertMap.put("/app/goods/evaluation/info.json", "true");		
		
		emojiFieldsConvertMap.put("content", "true");
	}
	/**新浪微博的AppKey，随便创建一个应用就会分配一个AppKey*/
	private static String SOURCE = "3271760578";
	private static final String API = "http://api.t.sina.com.cn/short_url/shorten.json";
	static String lonMatch = "[\\-+]?(0?\\d{1,2}|0?\\d{1,2}\\.\\d{1,15}|1[0-7]?\\d|1[0-7]?\\d\\.\\d{1,15}|180|180\\.0{1,15})";
	static String latMatch = "[\\-+]?([0-8]?\\d|[0-8]?\\d\\.\\d{1,15}|90|90\\.0{1,15})";

	/**
	 * 文件头和扩展名的映射
	 */
	static Map<String, String> fileHeadExtMap = new HashMap<String, String>();
	static{
		fileHeadExtMap.put("FFD8FF", "jpg");
		fileHeadExtMap.put("89504E47", "png");
		fileHeadExtMap.put("47494638", "gif");
		fileHeadExtMap.put("49492A00", "tiff");
		fileHeadExtMap.put("424D", "bmp");
		fileHeadExtMap.put("41433130", "dwg");
		fileHeadExtMap.put("38425053", "psd");
		fileHeadExtMap.put("7B5C727466", "rtf");
		fileHeadExtMap.put("3C3F786D6C", "xml");
		fileHeadExtMap.put("68746D6C3E", "html");
		fileHeadExtMap.put("44656C69766572792D646174653A", "eml");
		fileHeadExtMap.put("CFAD12FEC5FD746F", "dbx");
		fileHeadExtMap.put("2142444E", "pst");
		fileHeadExtMap.put("D0CF11E0", "xls_doc");
		fileHeadExtMap.put("5374616E64617264204A", "mdb");
		fileHeadExtMap.put("FF575043", "wpd");
		fileHeadExtMap.put("252150532D41646F6265", "eps");
		fileHeadExtMap.put("255044462D312E", "pdf");
		fileHeadExtMap.put("AC9EBD8F", "qdf");
		fileHeadExtMap.put("E3828596", "pwl");
		fileHeadExtMap.put("504B0304", "zip");
		fileHeadExtMap.put("52617221", "rar");
		fileHeadExtMap.put("57415645", "wav");
		fileHeadExtMap.put("41564920", "avi");
		fileHeadExtMap.put("2E7261FD", "ram");
		fileHeadExtMap.put("2E524D46", "rm");
		fileHeadExtMap.put("000001BA", "mpg");
		fileHeadExtMap.put("6D6F6F76", "mov");
		fileHeadExtMap.put("3026B2758E66CF11", "asf");
		fileHeadExtMap.put("4D546864", "mid");
	}
	
	
	//---------------[组装JSON返回信息---------------	
	/**
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String error(int code, String msg){
		return error(code+"", msg);
	}
	/**
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String error(int code){
		String msg = null;
		if(code == 402){
			msg = "";
		}
		return error(code+"", msg);
	}

	/**
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String error(String code, String msg){		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtils.isBlank(msg)){
			msg = "";
		} else {
			if(!"501".equals(code)){
				if("请先登录后再操作".equals(msg) || msg.indexOf("登录后") != -1){
					code = "501";
				}
			}
		}

		map.put("code", code);
		map.put("msg", msg);
		map.put("success", false);		
		
		String json = JSON.toJSONString(map);		
		return json;
	}	

	/**
	 * 提示需要登录，返回 501
	 * @return
	 */
	public static String errorAuth(){
		String code = "501";
		String msg = "请先登录后再操作";
		return error(code, msg);
	}
	
	/**
	 * 只返回code=400
	 * @return
	 */
	public static String simpleError(){	
		return simpleError("400");
	}
	/**
	 * 只返回code
	 * @param code
	 * @return
	 */
	public static String simpleError(String code){		
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("code", code);		
		String json = JSON.toJSONString(map);		
		return json;
	}
	/**
	 * 
	 * @param msg
	 * @return
	 */
	public static String error(String msg){
		return error("400", msg);
	}
	
	public static String error(String msg, String key, Object value){
		Map<String,Object> data = new HashMap<String,Object>();
		data.put(key, value);
		return error(msg, data);
	}
	
	public static String error(String msg, Object data, final SerializeFilter... filters){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", "400");
		map.put("msg", msg);
		map.put("data", data);
		map.put("now", System.currentTimeMillis());
		map.put("success", false);		
		
		
		String json = "";
		if(filters != null && filters.length>0){
			json = JSON.toJSONString(map, filters, SerializerFeature.DisableCircularReferenceDetect);
		} else {
			json = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
		}
				
		return json;
	}


	//---------------组装JSON返回信息]---------------
	
	
	/**
	 * 从ContentType猜测文件类型
	 * @return image|video|audio|other
	 */
	public static String getFileTypeFromContentType(String contentType){
		String image="image", video="video", audio="audio", other="other";
		
		if(StringUtils.isBlank(contentType)){
			return other;
		}
		contentType = contentType.trim().toLowerCase();
		if(contentType.startsWith("image")){
			return image;
		}
		if(contentType.startsWith("video")){
			return video;
		}
		if(contentType.startsWith("audio")){
			return audio;
		}
		if(isInList("jpg,jpeg,png,gif", contentType)){
			return image;
		}
		if(isInList("mp4,avi,mpeg,rm,rmvb,flv,mov", contentType)){
			return video;
		}
		if(isInList("mp3,wav,arm,acc,ogg", contentType)){
			return video;
		}
		return other;
	}
	/**
	 * 从文件扩展名猜测文件类型
	 * @return image|video|audio|other
	 */
	public static String getFileTypeFromExt(String fileExt){
		String image="image", video="video", audio="audio", other="other";
		
		if(StringUtils.isBlank(fileExt)){
			return other;
		}
		fileExt = fileExt.trim().toLowerCase().replaceAll("^\\.*", "");		
		if(isInList("jpg,jpeg,png,gif", fileExt)){
			return image;
		}
		if(isInList("mp4,avi,mpeg,rm,rmvb,flv,mov", fileExt)){
			return video;
		}
		if(isInList("mp3,wav,arm,acc,ogg", fileExt)){
			return audio;
		}
		return other;
	}
	
	/**
	 * 将ContentType转为扩展名(不含点号)
	 * @return
	 */
	public static String getExtFromContentType(String contentType, String defaultExt){
		if(map == null){
			map = new HashMap<String, String>();
			map.put("application/msword", "doc");
			map.put("application/x-ppt", "ppt");
			map.put("application/x-xls", "xls");
			map.put("application/x-msexcel", "xls");
			map.put("application/vnd.ms-excel", "xls");
			map.put("application/pdf", "pdf");
			map.put("text/plain", "txt");
			map.put("image/jpg", "jpg");
			map.put("image/jpeg", "jpg");
			map.put("image/png", "png");
			map.put("image/gif", "gif");
			map.put("image/tiff", "tiff");
			
			map.put("application/x-jpg", "jpg");
			map.put("application/x-png", "png");
			map.put("application/x-bmp", "bmp");

			map.put("video/mp4", "mp4");
			map.put("video/avi", "avi");
			map.put("video/mpeg", "mp2v");
			map.put("video/quicktime", "mov");
			map.put("video/vnd.mpegurl", "mxu");
			map.put("video/x-msvideo", "avi");
			map.put("video/x-sgi-movie", "movie");
			map.put("video/mpeg4", "mp4");
			map.put("video/x-mpeg", "mpe");
			map.put("application/x-shockwave-flash", "swf");
			
			map.put("audio/wav", "wav");
			map.put("audio/x-wav", "wav");
			map.put("audio/mp3", "mp3");
			map.put("audio/mpeg", "mp3");			
		
			map.put("jpg", "jpg");
			map.put("png", "png");
			map.put("gif", "gif");
			map.put("jpge", "jpge");
			map.put("wav", "wav");
			map.put("mp3", "mp3");
			map.put("mp4", "mp4");
			map.put("avi", "avi");
			
			map.put("apk", "apk");
			map.put("ipa", "ipa");
		}
		String ext =map.get(contentType);
		return (ext == null? defaultExt : ext);
	}
	public static String getExtFromContentType(String contentType){
		return getExtFromContentType(contentType, "jpg");
	}
	/**
	 * 从文件判断其content-type
	 * @param file
	 * @return
	 */
	public static String getContentFormFile(File file){
		try{
			ImageInputStream iis = ImageIO.createImageInputStream(file);  
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);  
			if (iter.hasNext()) {
				ImageReader reader = iter.next();
				String[] mimeTypes = reader.getOriginatingProvider().getMIMETypes();
				if(mimeTypes != null && mimeTypes.length > 0){
					return mimeTypes[0];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	/**
	 * 获取文件扩展名(不含点号)
	 * @param fileName
	 * @return 如：jpg
	 */
	public static String getFileExt(String fileName){
		return getFileExt(fileName, "");
	}
	/**
	 * 获取文件扩展名(不含点号)
	 * @param fileName
	 * @param defaultExt 群不到是的默认扩展名
	 * @return 如：jpg
	 */
	public static String getFileExt(String fileName, String defaultExt){
        fileName = getFileName(fileName);
		String ext = fileName.replaceAll("^[^.]+$", "")
                .replaceAll("^.*\\.", "")
                .replaceAll("\\?.+$", "")
                .toLowerCase();
		if(StringUtils.isBlank(ext)){
			ext = defaultExt;
		}
		return ext;
	}

    /**
     * 从文件流判断文件类型
     * 
     * @param inputStream 文件流
     * @return 不带点号的扩展名, 非常见的则返回defaultExt
     */
    public static String getFileExtFromStream(InputStream inputStream, String defaultExt) throws IOException {	         
        String fileHead = getFileHead(inputStream);
         
        if (fileHead == null || fileHead.length() == 0) {
            return defaultExt;
        }
         
        fileHead = fileHead.toUpperCase(); 
        for(Map.Entry<String,String> entry : fileHeadExtMap.entrySet()){
        	if(fileHead.startsWith(entry.getKey())) {
        		return entry.getValue();
        	}
        }
        return defaultExt;
    }

	/**
     * 将文件头转换成16进制字符串
     * 
     * @param 原生byte
     * @return 16进制字符串
     */
    private static String fileHeaderBytesToHexString(byte[] src){         
        StringBuilder stringBuilder = new StringBuilder();   
        if (src == null || src.length <= 0) {   
            return null;   
        }   
        for (int i = 0; i < src.length; i++) {   
            int v = src[i] & 0xFF;   
            String hv = Integer.toHexString(v);   
            if (hv.length() < 2) {   
                stringBuilder.append(0);   
            }   
            stringBuilder.append(hv);   
        }   
        return stringBuilder.toString();   
    }
    
    /**
     * 得到文件头
     * 
     * @param inputStream 文件流
     * @return 文件头
     */
    private static String getFileHead(InputStream inputStream) {
    	if(inputStream == null){
    		return null;
    	}
        byte[] b = new byte[28];	         	         
        try {
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //e.printStackTrace();
    	            return null;
                }
            }
        }
        return fileHeaderBytesToHexString(b);
    }
	public static String getFilePath(String fileName){
		return fileName.replaceAll("^(.*[\\\\/]).*$", "$1");
	}
	public static String getFileName(String filepath){
		return filepath.replaceAll("^.*[\\\\/]", "");
	}
	public static String getFilePathWithOutExt(String fileName){
		if(fileName.lastIndexOf(".") == -1){
			return fileName;
		}
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	
	/**
	 * 取得参数列表中第一个不为空的值
	 * @param strings
	 */
	public static String getFirstNotBlank(Object ...strings){
		for(Object str : strings){
			if(str != null && QYUtils.isNotBlank(String.valueOf(str))){
				return String.valueOf(str);
			}
		}		
		return "";
	}
	/**
	 * "null" --> null
	 * @param str
	 * @return
	 */
	public static String transStrNull(String str){
		if(str == null){
			return null;
		}
		if("null".equalsIgnoreCase(str.trim())){
			return null;
		}
		return str;
	}
	
	/**
	 * 取得参数列表中第一个不为空的值
	 * @param strings
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T  getFirstNotNull(T ...objs){
		for(Object obj : objs){
			if(obj != null){				
				return (T)obj;
			}
		}		
		return null;
	}
	
	public static <T> T ifNull(T in, T out){
		if(in==null){
			return out;
		}
		return in;
	}
	
	/**
	 * 取得参数列表中第一个不为空的值
	 * @param strings
	 * @return
	 */
	public static Integer getFirstNotBlank(Integer ...ids){
		for(Integer id : ids){
			if(id!=null && !isEqual(id,0)){
				return id;
			}
		}		
		return 0;
	}

	public static Integer getFirstNotNull0(Integer ...ids){
		for(Integer id : ids){
			if(isNotNull0(id)){
				return id;
			}
		}		
		return null;
	}
	
	public static Long getFirstNotNull0(Long ...ids){
		for(Long id : ids){
			if(isNotNull0(id)){
				return id;
			}
		}		
		return null;
	}
	
	/**
	  * 生成6位随机数字
	  */
	public static int random6(){
		Random random = new Random();  
		int x = random.nextInt(899999); //返回值范围为0到n - 1的分布 
		x = x + 100000; 
		return x;
	}
	/**
	  * 生成N位随机数字
	  */
	public static int randomN(int n){
		int max = new Double(Math.pow(10,n)).intValue() - 1;
		int min = new Double(Math.pow(10,n-1)).intValue();
		Random random = new Random();  
		int x = random.nextInt(max); //返回值范围为0到max - 1的分布 
		if(x < min){			
			x = x + min;
		}
		return x;
	}

	/**
	 * 生成范围内随机数字
	 */
	public static int randomAB(int min, int max){
		if(max < min){
			int temp = max;
			max = min;
			min = temp;
		}
		if (min == max) {
			return min;
		}
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
	/**
	 * 生成范围内随机数字
	 */
	public static double randomAB(double min, double max){
		if(max < min){
			double temp = max;
			max = min;
			min = temp;
		}

		if (min == max) {
			return min;
		}
		double result =  min + ((max - min) * new Random().nextDouble());
		return  toDecimal(result, 2).doubleValue();
	}
	/**
	 * 
	 * @param idsStr - id列表
	 * @param spilter - 分割符
	 * @return
	 */
	public static Integer[] getInts(String intStr, String spilter){
		Integer[] ints = {};
		if(StringUtils.isNotBlank(intStr)){
			String[] parts = intStr.trim().split(spilter);
			Set<Integer> result = new HashSet<Integer>();
			for (int i = 0; i < parts.length; i++) {
				if(StringUtils.isBlank(parts[i]) || "null".equalsIgnoreCase(parts[i].trim())){
					continue;
				}
				try{
					Integer intVal = Integer.parseInt(parts[i].trim());
					result.add(intVal);
				} catch(Exception e){}    
				
				//result.add(Integer.parseInt(idInfo[i].trim()));
			}
			ints = result.toArray(new Integer[result.size()]);
		}
		return ints;
	}
	/**
	 * 
	 * @param idsStr - id列表
	 * @param spilter - 分割符
	 * @return
	 */
	public static Long[] getLongs(String intStr, String spilter){
		Long[] ints = {};
		if(StringUtils.isNotBlank(intStr)){
			String[] parts = intStr.trim().split(spilter);
			List<Long> result = new ArrayList<Long>();
			for (int i = 0; i < parts.length; i++) {
				if(StringUtils.isBlank(parts[i]) || "null".equalsIgnoreCase(parts[i].trim())){
					continue;
				}
				try{
					Long intVal = toLong(parts[i].trim());
					if(intVal != null && !result.contains(intVal)){
						result.add(intVal);
					}
				} catch(Exception e){}    
				
				//result.add(Integer.parseInt(idInfo[i].trim()));
			}
			ints = result.toArray(new Long[result.size()]);
		}
		return ints;
	}

	public static Long[] getLongArray(String longStr){
		return getLongs(longStr, ",");
	}
	public static Long[] getLongArray(String longStr, String spilter){
		return getLongs(longStr, spilter);
	}
	public static List<Long> getLongList(String longStr){
		return Arrays.asList(getLongs(longStr, ","));
	}
	
	/**
	 * 
	 * @param intStr - 逗号分隔的int值列表
	 * @return
	 */
	public static Integer[] getInts(String intStr){
		return getInts(intStr, ",");
	}
	public static Integer[] getIntArray(String intStr){
		return getInts(intStr);
	}
	
	public static Integer[] getIntArray(String intStr, String spilter){
		return getInts(intStr, ",");
	}

	/**
	 * 
	 * @param idsStr - id列表
	 * @param spilter - 分割符
	 * @return
	 */
	public static List<Integer> getIntList(String idsStr, String spilter){
		List<Integer> ids = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(idsStr)){
			String[] idInfo = idsStr.trim().split(spilter);
			Set<Integer> result = new HashSet<Integer>();
			for (int i = 0; i < idInfo.length; i++) {
				if(StringUtils.isBlank(idInfo[i]) || "null".equalsIgnoreCase(idInfo[i].trim())){
					continue;
				}
				try{
					Integer id = Integer.parseInt(idInfo[i].trim());
					result.add(id);
				} catch(Exception e){}                
			}
			
			for(int id : result){
				ids.add(id);
			}
		}
		return ids;
	}

	/**
	 * 
	 * @param idsStr - 逗号分隔的id列表
	 * @return
	 */
	public static List<Integer> getIntList_repeat(String idsStr){
		return getIntList_repeat(idsStr, ",");
	}
	/**
	 * 
	 * @param idsStr - id列表
	 * @param spilter - 分割符
	 * @return
	 */
	public static List<Integer> getIntList_repeat(String idsStr, String spilter){
		List<Integer> ids = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(idsStr)){
			String[] idInfo = idsStr.trim().split(spilter);
			for (int i = 0; i < idInfo.length; i++) {
				if(StringUtils.isBlank(idInfo[i]) || "null".equalsIgnoreCase(idInfo[i].trim())){
					continue;
				}
				try{
					Integer id = Integer.parseInt(idInfo[i].trim());
					ids.add(id);
				} catch(Exception e){}                
			}
			
//			for(int id : result){
//				ids.add(id);
//			}
		}
		return ids;
	}
	/**
	 * 获取无序的不重复的Long列表
	 * @param idsStr - id列表
	 * @param spilter - 分割符
	 * @return
	 */
	public static List<Long> getLongList(String idsStr, String spilter){
		List<Long> ids = new ArrayList<>();
		if(StringUtils.isNotBlank(idsStr)){
			String[] idInfo = idsStr.trim().split(spilter);
			Set<Long> result = new HashSet<>();
			for (int i = 0; i < idInfo.length; i++) {
				if(StringUtils.isBlank(idInfo[i]) || "null".equalsIgnoreCase(idInfo[i].trim())){
					continue;
				}
				try{
					Long id = toLong(idInfo[i].trim());
					if(id != null){
						result.add(id);
					}
				} catch(Exception e){}                
			}
			
			for(Long id : result){
				ids.add(id);
			}
		}
		return ids;
	}
	/**
	 * 
	 * @param idsStr - 逗号分隔的id列表
	 * @return
	 */
	public static List<Integer> getIntList(String idsStr){
		return getIntList(idsStr, ",");
	}
	
	/**
	 * 
	 * @param idsStr - 逗号分隔的id列表
	 * @param spilter - 分割符
	 * @return
	 */
	public static Set<Integer> getIntSet(String idsStr, String spilter){
		Set<Integer> ids = new HashSet<Integer>();
		if(StringUtils.isNotBlank(idsStr)){
			String[] idInfo = idsStr.trim().split(spilter);            
			for (int i = 0; i < idInfo.length; i++) {
				if(isBlank(idInfo[i])){
					continue;
				}
				ids.add(toInteger(idInfo[i].trim()));
			}  
		}
		return ids;
	}
	/**
	 * 
	 * @param idsStr - 逗号分隔的id列表
	 * @return
	 */
	public static Set<Integer> getIntSet(String idsStr){
		return getIntSet(idsStr, ",");
	}
	
	/**
	 * 
	 * @param strs - 字符串列表
	 * @param spilter - 分割符
	 * @return
	 */
	public static List<String> getStrList(String strs, String spilter){
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(strs)){
			String[] strArray = strs.trim().split(spilter);            
			for (int i = 0; i < strArray.length; i++) {
				if(QYUtils.isBlank(strArray[i]) || list.contains(strArray[i].trim())){
					continue;
				}
				list.add(strArray[i].trim());
			}
		}
		return list;
	}
	public static List<String> getStrList(String strs){		
		return getStrList(strs, ",");
	}
	/**
	 * 判断两个list是否是相同的list(忽略顺序,去除空元素,忽略重复元素)
	 * @param list1
	 * @param list2
	 */
	public static boolean isSameList(String list1, String list2, String spilter){
		if(StringUtils.isBlank(list1) || StringUtils.isBlank(list2)){
			return false;
		}
		list1 = list1.trim();
		list2 = list2.trim();
		if(list1.equals(list2)){
			return true;
		}
		Set<String> set1 = getStrSet(list1, spilter);
		Set<String> set2 = getStrSet(list2, spilter);
		
		return set1.equals(set2);
	}

	/**
	 * 格式化逗号拼接的列表，去除多余的无效的逗号
	 * @param list
	 * @return
	 */
	public static String formatList(String list){
		if(StringUtils.isBlank(list)){
			return "";
		}
		return list.replaceAll("\\s*,\\s*",",").replaceAll(",+",",").replaceAll("^,+|,+$","");
	}
	/**
	 * 
	 * @param strs - 逗号分隔的字符串列表
	 * @param spilter - 分割符
	 * @return
	 */
	public static Set<String> getStrSet(String strs, String spilter){
		Set<String> set = new HashSet<String>();
		if(QYUtils.isNotBlank(strs)){
			String[] strArray = strs.trim().split(spilter);            
			for (int i = 0; i < strArray.length; i++) {
				String thisStr = strArray[i];
				if(QYUtils.isNotBlank(thisStr)){
					set.add(thisStr.trim());
				}
			}  
		}
		return set;
	}	
	/**
	 * 合并两个逗号list,去除重复元素
	 * @param list1  
	 * @param list2  
	 * @return
	 */
	public static String mergeList(String list1, String list2){
		if(StringUtils.isBlank(list1) && StringUtils.isBlank(list2)){
			return "";
		}
		
		String list = ifNull(list1, "") +","+ ifNull(list2, "");
		Set<String> set = new HashSet<String>();
		String[] strArray = list.trim().split(",");            
		for (int i=0; i<strArray.length; i++) {
			String thisStr = strArray[i];
			if(QYUtils.isNotBlank(thisStr)){
				set.add(thisStr.trim());
			}
		}
		if(set.size() == 0 ){
			return "";
		}
		return StringUtils.join(set, ",");
	}	
	
	/**
	 * 获取两个结合的交叉元素
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static Set<String> getCrossElements(Set<String> set1, Set<String> set2){
		Set<String> set = new HashSet<String>();
		if(set1 == null || set1.isEmpty() || set2 == null || set2.isEmpty()){
			return set;
		}
		Set<String> formatedSet1 = new HashSet<String>();
		for(String element : set1){
			if(StringUtils.isNotBlank(element)){
				formatedSet1.add(element.trim().toLowerCase());
			}
		}
		Set<String> formatedSet2 = new HashSet<String>();
		for(String element : set2){
			if(StringUtils.isNotBlank(element)){
				formatedSet2.add(element.trim().toLowerCase());
			}
		}
		for(String element : formatedSet1){
			if(formatedSet2.contains(element)){
				set.add(element);
			}
		}
		return set;
	}
	/**
	 * 获取两个结合的交叉元素个数
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static int getCrossElementsCount(Set<String> set1, Set<String> set2){
		int count = 0;
		if(set1 == null || set1.isEmpty() || set2 == null || set2.isEmpty()){
			return 0;
		}
		Set<String> formatedSet1 = new HashSet<String>();
		for(String element : set1){
			if(StringUtils.isNotBlank(element)){
				formatedSet1.add(element.trim().toLowerCase());
			}
		}
		Set<String> formatedSet2 = new HashSet<String>();
		for(String element : set2){
			if(StringUtils.isNotBlank(element)){
				formatedSet2.add(element.trim().toLowerCase());
			}
		}
		for(String element : formatedSet1){
			if(formatedSet2.contains(element)){
				count ++;
			}
		}
		return count;
	}
	
	public static Set<String> getStrSet(String strs){
		return getStrSet(strs, ",");
	}
	
	
	public static Set<Integer> array2Set(Integer[] array){
		Set<Integer> set = new HashSet<Integer>();
		for(Integer id : array) {
			set.add(id);
		}  
		return set;
	}
	
	public static List<Integer> array2List(Integer[] array){
		List<Integer> list = new ArrayList<Integer>();
		for(Integer id : array) {
			list.add(id);
		}  
		return list;
	}
	public static List<Long> array2List(Long[] array){
		List<Long> list = new ArrayList<Long>();
		for(Long id : array) {
			list.add(id);
		}
		return list;
	}
	public static List<String> array2List(String[] array){
		List<String> list = new ArrayList<String>();
		for(String id : array) {
			if(StringUtils.isNotBlank(id)){
				list.add(id);
			}
		}  
		return list;
	}
	public static Integer[] set2Array(Set<Integer> set){
		Integer[] ids = {};
		ids = set.toArray(new Integer[set.size()]);
		return ids;
	}

	public static String[] set2StringArray(Set<String> set){
		String[] ids = {};
		ids = set.toArray(new String[set.size()]);
		return ids;
	}
	
	public static Integer[] list2Array(List<Integer> list){
		Integer[] ids = {};
		ids = list.toArray(new Integer[list.size()]);
		return ids;
	}
	public static String[] list2StringArray(List<String> list){
		String[] ids = {};
		ids = list.toArray(new String[list.size()]);
		return ids;
	}
	public static <E> List<E> set2List(Set<E> set){
		List<E> list = new ArrayList<E>();
		if(set != null && !set.isEmpty()){
			for(E id : set) {
				list.add(id);
			}  
		}
		return list;
	}
	public static List<String> set2ListStr(Set<String> set){
		List<String> list = new ArrayList<String>();
		for(String id : set) {
			list.add(id);
		}  
		return list;
	}
	public static <E> Set<E> list2Set(List<E> list){
		Set<E> set = new HashSet<E>();
		if(list != null && !list.isEmpty()){
			for(E obj : list) {
				set.add(obj);
			}  
		}
		return set;
	}
	/**
	 * 用connector连接strList中各个元素成字符串，并且忽略空串
	 * @param connector
	 * @param strList
	 * @return
	 */
	public static String join(String connector, List<String> strList){
		if(strList == null || strList.isEmpty()){
			return "";
		}
		List<String> finalList = new ArrayList<String>();
		for(String str : strList){
			if(StringUtils.isBlank(str)){
				continue;
			}
			finalList.add(str.trim());
		}
		return StringUtils.join(finalList, connector);
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
	/**
	 * 用connector连接成字符串，并且忽略空串，去除空白
	 * @param connector
	 * @param strings
	 * @return
	 */
	public static String joinWith(char connector, String...strings){
		StringBuilder sb = new StringBuilder();
		for(String string : strings){
			if(StringUtils.isNotBlank(string)){
				sb.append(connector).append(string.trim());
			}
		}
		String result = sb.toString().replaceAll("^"+connector+"+|"+connector+"+$", "");
		return result;
	}
	 /**
	 * 用connector连接成字符串，并且忽略空串，去除空白
	 * @param connector
	 * @param strings
	 * @return
	 */
	public static String joinWith(String connector, Object...objs){
		StringBuilder sb = new StringBuilder();
		for(Object obj : objs){
			if(obj != null){
				sb.append(connector).append(String.valueOf(obj).trim());
			}
		}
		String result = sb.toString().replaceAll("^"+connector+"+|"+connector+"+$", "");
		return result;
	}
	
	public static List<String> split2StringList(String str, String regex){
		List<String> list = new ArrayList<String>();
		String[] strs = str.split(regex);
		for(String string : strs){
			if(StringUtils.isNotBlank(string) && !list.contains(string.trim())){
				list.add(string.trim());	
			}
		}
		return list;
	}
	
	public static List<String> split2StringList(String str){
		List<String> list = new ArrayList<String>();
		if(isBlank(str)) {
			return list;
		}
		String[] strs = str.split(",");
		for(String string : strs){
			if(StringUtils.isNotBlank(string) && !list.contains(string.trim())){
				list.add(string.trim());	
			}
		}
		return list;
	}
	public static Set<String> split2StringSet(String str){
		Set<String> set = new HashSet<String>();
		String[] strs = str.split(",");
		for(String string : strs){
			if(StringUtils.isNotBlank(string)){
				set.add(string.trim());	
			}
		}
		return set;
	}
	public static List<Integer> split2IntegerList(String str){
		List<Integer> list = new ArrayList<Integer>();
		String[] strs = str.split(",");
		for(String string : strs){
			if(StringUtils.isNotBlank(string)){
				Integer intVal = toInteger(string.trim());
				if(intVal != null && !list.contains(intVal)){
					list.add(intVal);	
				}
			}
		}
		return list;
	}
	public static List<Long> split2LongList(String str){
		List<Long> list = new ArrayList<Long>();
		
		if (isBlank(str)) {
			return list;
		}
		String[] strs = str.split(",");
		for(String string : strs){
			if(StringUtils.isNotBlank(string)){
				Long intVal = toLong(string.trim());
				if(intVal != null && !list.contains(intVal)){
					list.add(intVal);
				}
			}
		}
		return list;
	}
	
	public static String acceptPicContentTypes(){
		String acceptContentTypes = "image/bmp,image/gif,image/jpg,image/jpeg,image/png";   
		return acceptContentTypes;
	}	
	public static String acceptVideoContentTypes(){
		String acceptContentTypes = "video/mp4,video/mpeg4,video/mpeg,video/quicktime,video/x-mpeg,video/x-msvideo,video/avi,video/vnd.mpegurl,application/x-shockwave-flash,video/x-mpeg,video/x-sgi-movie";	
		return acceptContentTypes;
	}
	public static String acceptAudioContentTypes(){
		String acceptContentTypes = "audio/mp3,audio/wav,audio/mpeg";   
		return acceptContentTypes;
	}
	
	public static boolean isInArray(Integer[] ids, Integer id){
		if(ids==null || id==null){
			return false;
		}
		for(Integer thisId : ids){
			//if(thisId == id){
			if(isEqual(thisId, id)){
				return true;
			}
		}
		return false;
	}

	public static boolean isInArray(String[] arr, String str){
		if(arr==null || str==null){
			return false;
		}
		for(String thisStr : arr){
			if(thisStr == null){
				continue;
			}
			if(thisStr.trim().equals(str.trim())){
				return true;
			}
		}
		return false;
	}
	public static boolean isInList(String idsStr, Integer id){
		if(idsStr==null || id==null){
			return false;
		}
		Integer[] ids = getIntArray(idsStr);
		return isInArray(ids, id);
	}
	public static boolean isInList(String list, String str){
		if(list==null || str==null){
			return false;
		}
		String[] arr = list.split(",");
		return isInArray(arr, str);
	}
	
	//
	/**
	 * 如果给定的时间不为空，则直接返回之，否则，返回一周前的时间值
	 * @param lastTime
	 * @return
	 */
	public static Date checkDate(Date lastTime){
		if(lastTime != null){
			return lastTime;
		}
		
		long now = (new Date()).getTime();
		long laskWeek = now - 7 * 24 * 60 * 60 * 1000;//一周前
		Date finalDate = new Date(laskWeek);
		
		lastTime = finalDate;
		
		return finalDate;		
	}
	/**
	 * 判断两个BigDecimal型的对象是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEqual(BigDecimal a, BigDecimal  b){
		if(a == null || b == null){
			return false;
		}
		return (a.compareTo(b) == 0);		
	}
    /**
     * 判断两个BigDecimal型的对象是否相等
     * @param a
     * @param b
     * @return
     */
    public static boolean isNotEqual(BigDecimal a, BigDecimal  b){
        return !isEqual(a, b);
    }
	/**
	 * 判断两个Integer型的对象是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEqual(Integer a, Integer b){
		if(a == null || b == null){
			return false;
		}
		return (a.compareTo(b) == 0);		
	}
	/**
	 * 判断两个Long型的对象是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEqual(Long a, Long b){
		if(a == null || b == null){
			return false;
		}
		return (a.compareTo(b) == 0);		
	}
	/**
	 * 判断两个Double型的对象是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEqual(Double a, Double b){
		if(a == null || b == null){
			return false;
		}
		return (a.compareTo(b) == 0);		
	}
	/**
	 * 判断两个Double型的对象是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEqualStr(Object a, Object b){
		if(a == null || b == null){
			return false;
		}
		return (String.valueOf(a).equalsIgnoreCase(String.valueOf(b)));		
	}
	public static boolean isNotEqual(Integer a, Integer b){
		return !isEqual(a,b);
	}
	public static boolean isNotEqual(Long a, Long b){
		return !isEqual(a,b);
	}
	public static boolean isNotEqual(Double a, Double b){
		return !isEqual(a,b);
	}
	/**
	 * 为null或者为0
	 * @param a
	 * @return
	 */
	public static boolean isNull0(Integer a){
		if(a == null){
			return true;
		}
		return (a.compareTo(0) == 0);		
	}

	/**
	 * 为null或者为0
	 * @param a
	 * @return
	 */
	public static boolean isNull(Integer a){
		if(a == null){
			return true;
		}
		return false;
	}

	public static boolean isNull0(Long a){
		if(a == null){
			return true;
		}
		return (a.compareTo(0l) == 0);		
	}
	/**
	 * 不为null且不为0
	 */
	public static boolean isNotNull0(Integer a){
		if(a == null){
			return false;
		}
		return (a.compareTo(0) != 0);		
	}
	/**
	 * 不为null且不为0
	 */
	public static boolean isNotNull0(BigDecimal a){
		if(a == null){
			return false;
		}
		return (a.compareTo(new BigDecimal(0)) != 0);		
	}
	public static boolean isNotNull0(Long a){
		if(a == null){
			return false;
		}
		return (a.compareTo(0l) != 0);		
	}
    public static boolean isNotNull0(Double a){
        if(a == null){
            return false;
        }
        return (a.compareTo(0d) != 0);
    }
	public static boolean isNotNull0(Float a){
		if(a == null){
			return false;
		}
		return (a.compareTo(0f) != 0);		
	}
	public static boolean isNull0(Float a){
		if(a == null){
			return true;
		}
		return (a.compareTo(0f) == 0);		
	}
	
	public static boolean isNull0(BigDecimal a){
		if(a == null){
			return true;
		}
		return (a.compareTo(BigDecimal.ZERO) == 0);
	}

    public static boolean isNull0(Double a) {
        if(a == null){
            return true;
        }
        return (a.compareTo(0d) == 0);
    }
	
	/**
	 * <pre>
	 * Object  -- != null
	 * String  -- !XtgUtils.isBlank(obj)
	 * Integer -- !XtgUtils.isNull0(obj)
	 * </pre>
	 * @param obj
	 * @return
	 */
	public static boolean isValid(Object obj){
		if(obj == null){
			return false;
		}
		if(obj instanceof String && QYUtils.isBlank((String)obj)){
			return false;
		}
		if(obj instanceof Integer && QYUtils.isNull0((Integer)obj)){
			return false;
		}
		if(obj instanceof Long && QYUtils.isNull0((Integer)obj)){
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param obj
	 * @see QYUtils#isValid
	 */
	public static boolean isNotValid(Object obj){
		return !isValid(obj);
	}
	/**
	 * @param dateTime
	 */
	public static String showTimeOld(long dateTime) {
		if(dateTime==0){
			return "";
		}
		
		long delay = (System.currentTimeMillis() - dateTime) / 1000 / 60;// 单位：分钟	
		String showTime = "刚刚";
		if (delay < 5) {
			return showTime;
		}
		if (delay < 60) {
			showTime = delay + "分钟前";
			return showTime;
		} else {
			delay /= 60;
			if(delay < 24) {
				showTime = delay + "小时前";
			} else {
				delay /= 24;

				showTime = delay + "天前";
				return showTime;
			}
		}
		return showTime;
	}
	/**
	 * @param dateTime
	 */
	public static String showTime(long dateTime) {
		if(dateTime==0){
			return "";
		}
		long now = System.currentTimeMillis();		
		long delay = (now - dateTime) / 1000 / 60;// 单位：分钟	
		String showTime = "刚刚";
		if(delay > 0){//过去的时间
			if (delay < 5) {
				return showTime;
			}
			if (delay < 60) {
				showTime = delay + "分钟前";
				return showTime;
			}
			
			delay /= 60;
			if(delay < 24) {
				showTime = delay + "小时前";
				return showTime;
			}
			
			delay /= 24;
			if(delay < 2) {
				showTime = "昨天";
				return showTime;
			}
		} else {
			delay = 0-delay;
			if (delay < 5) {
				showTime = "稍后";
				return showTime;
			}
			if (delay < 60) {
				showTime = delay + "分钟后";
				return showTime;
			}
			
			delay /= 60;
			if(delay < 24) {
				showTime = delay + "小时后";
				return showTime;
			}
			
			delay /= 24;
			if(delay < 2) {
				showTime = "明天";
				return showTime;
			}
		}
		
		return shortDate(dateTime);
	}
	/**
	 * @param dateTime
	 */
	public static String showTime(Date dateTime) {	
		if(dateTime==null){
			return "";
		}
		return showTime(dateTime.getTime());
	}
	/**
	 * 返回简洁的日期
	 * @param dateTime
	 */
	public static String shortDate(long dateTime) {	
		return shortDate(new Date(dateTime));
	}
	/**
	 * 返回简洁的日期
	 * @param dateTime
	 */
	public static String shortDate(Date dateTime) {	
		Date now = new Date();
		if(DateUtils.sameDay(now, dateTime)){//同一天
			return DateUtils.DateToString(dateTime, "HH:mm");
		}
		if(DateUtils.sameYear(now, dateTime)){//同一年
			return DateUtils.DateToString(dateTime, "MM-dd");
		}
		return DateUtils.DateToString(dateTime, "yyyy-MM-dd");
	}
	/**
	 * 获取通用图片地址
	 * @param frontBaseUrl
	 * @param picFileName
	 * @return
	 */
	public static String getCommonPicUtr(String frontBaseUrl, String picFileName) {
		String url = mergeUrl(frontBaseUrl, "/res/jianyeft/img/common/", picFileName);
		
		return url;
	}
	/**
	 * 将 4 个字节的字符（如：未经过处理的表情符）转换掉
	 * @param str
	 * @param replacement
	 * @return
	 */
	public static String trans4bytesChar(String str, String replacement){
		if(StringUtils.isBlank(str)){
			return str;
		}
		
		try {
			byte[] bytes = str.getBytes("UTF-8");
			for (int i=0; i<bytes.length; i++)  {  
				if((bytes[i] & 0xF8) == 0xF0){//四字节长度的字符
					for(int j=0; j<4; j++) {                          
						bytes[i+j] = 0x60;//重音符`，16进制的ASCII值 
					}//四个字节都替换为重音符
					i += 3;  
				}
			} 
			String newStr = new String(bytes, "UTF-8");
			return newStr.replaceAll("````", replacement);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 将字符串中的汉字 一、二、三等译为1,2,3以便排序
	 * @param str
	 * @return
	 */
	public static String decodeHanZi(String str) {	
		if(StringUtils.isBlank(str)){
			return "";
		}
		return str.replaceAll("[一]", "1")
			.replaceAll("[二]", "2")
			.replaceAll("[三]", "3")
			.replaceAll("[四]", "4")
			.replaceAll("[五]", "5")
			.replaceAll("[六]", "6")
			.replaceAll("[七]", "7")
			.replaceAll("[八]", "8")
			.replaceAll("[九]", "9")
			.replaceAll("[十]", "10")
			.replaceAll("[零]", "0");
	}
	/**
	 * 批量替换
	 * regexs 和 replacements的长度需一致
	 * @param str 
	 * @param regexs 
	 * @param replacements
	 * @return
	 */
	public static String decode(String str, String[] regexs, String[] replacements) {	
		if(str == null){
			return null;
		}
		if(StringUtils.isBlank(str)){
			return "";
		}
		if(regexs==null||replacements==null){
			return str;
		}
		int len = Math.min(regexs.length, replacements.length);
		String newStr = str;
		for(int i=0; i<len; i++){
			newStr = newStr.replaceAll(regexs[i], replacements[i]);
		}
		return newStr;
	}
	/**
	 * 批量替换
	 * 
	 * regexStr 和 replacementStr的逗号分割后的长度需一致
	 * 
	 * @param str
	 * @param regexStr 逗号分隔的正则列表
	 * @param replacementStr 逗号分隔的替换列表
	 * @return
	 */
	public static String decode(String str, String regexStr, String replacementStr) {	
		String[] regexs = regexStr.split(",");
		String[] replacements = replacementStr.split(",");
		return decode(str, regexs, replacements);
	}
	public static String decodeDic(Integer code, String codeList, String nameList) {	
		return transDic(code+"", codeList, nameList);
	}
	public static String decodeDic(String code, String codeList, String nameList) {	
		return transDic(code, codeList, nameList);
	}
	/**
	 * 解析字典的code为名称
	 * <pre>如果code不在codeList中：
	 * 		- 如果nameList的元素数  > codeList的元素，则返回nameList的最后一个，视其为默认值
	 * 		- 如果nameList的元素数 <= codeList的元素，则返回 code本身
	 *其他特殊情况，返回code本身
	 * </pre>
	 * @param code, 如: 1
	 * @param codeList, 如: 1,2
	 * @param nameList, 如: 家长,教师
	 * @return
	 */
	public static String transDic(Object code, String codeList, String nameList) {	
		String str = null;
		if(code==null){
			str = "";
		} else {
			str = String.valueOf(code);
		}
		if(StringUtils.isBlank(codeList) || StringUtils.isBlank(nameList)){
			return "";
		}
		if(StringUtils.isBlank(str)){
			str = "";
		}
		str = trim(str);
		String[] codeParts = codeList.split("[,，]");
		String[] nameParts = nameList.split("[,，]");
		for(int i=0; i<codeParts.length; i++){			
			if(str.equals(trim(codeParts[i]))){
				if(nameParts.length>i){
					return trim(nameParts[i]);
				} else {//code列表长度和比name列表长度大
					return str;
				}
			}
		}
		//到此则codeList中没有目标code
		if(codeParts.length < nameParts.length){//nameList较长，则认为其最后一个为默认值
			return trim(nameParts[nameParts.length-1]);
		}
		//返回原值
		return str;
		
	}
	public static long totalPage(long totalRecords, int pageSize){
		if(totalRecords <= 1){
			return 1;
		}
		if(pageSize <= 0){//不分页
			return 1;
		}
		long totalPage = 0;
		if(totalRecords%pageSize==0) {
			totalPage = totalRecords / pageSize;
		}else {
			totalPage = totalRecords / pageSize + 1;
		}
		return totalPage;
	}
	
	public static int totalPage(int totalRecords, int pageSize){
		if(totalRecords <= 1){
			return 1;
		}
		if(pageSize <= 0){//不分页
			return 1;
		}
		int totalPage = 0;
		if(totalRecords%pageSize==0) {
			totalPage = totalRecords / pageSize;
		}else {
			totalPage = totalRecords / pageSize + 1;
		}
		return totalPage;
	}
	/**
	 * value 转为整型，如果value是浮点型的，直接去掉末尾 
	 * @param value
	 * @return
	 */
	public static Integer toInteger(Object value){
		return toInteger(value, null);				
	}
	public static Integer toInteger(Object value, Integer defaultValue){
		if(value == null){
			return defaultValue;
		}
		Integer result = null;
		try{
			result = (Integer)value;
			return result;
		}catch(ClassCastException cce){
			String clazz = value.getClass().getSimpleName();
			if("Double".equals(clazz)){
				result = ((Double)value).intValue();
				return result;
			} else if("Float".equals(clazz)){
				result = ((Float)value).intValue();
				return result;
			} else if("BigDecimal".equals(clazz)){
				result = ((BigDecimal)value).intValue();
				return result;
            } else if("Boolean".equals(clazz)){
                result = ((Boolean)value).booleanValue()? 1 : 0;
                return result;
            } else {
				try{
				    String valStr = value.toString().trim().replaceAll("\\..*$", "");
				    if("true".equalsIgnoreCase(valStr)){
				        result = 1;
                    } else if("false".equalsIgnoreCase(valStr)){
                        result = 0;
                    } else {
                        result = Integer.parseInt(valStr);
                    }
					return result;
				} catch(Exception e){
					//e.printStackTrace();
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		return defaultValue;
	}
	public static List<Integer> toIntegerList(List<Object> list){
		List<Integer> result = new ArrayList<Integer>();
		if(list != null){
			for(Object obj : list){
				Integer temp = QYUtils.toInteger(obj);
				if(temp != null){
					result.add(temp);
				}				
			}
		}
		return result;		
	}
	public static List<Long> toLongList(List<Object> list){
		List<Long> result = new ArrayList<Long>();
		if(list != null){
			for(Object obj : list){
				Long temp = QYUtils.toLong(obj);
				if(temp != null){
					result.add(temp);
				}				
			}
		}
		return result;		
	}
	public static Float toFloat(Object value){
		return toFloat(value, null);
	}
	public static Float toFloat(Object value, Float defaultValue){
		if(value == null){
			return defaultValue;
		}
		Float result = defaultValue;
		try{
			result = (Float)value;
			return result;
		}catch(ClassCastException cce){
			try{
				result = Float.parseFloat(value.toString().trim());
				return result;
			} catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return defaultValue;
	}
	public static Double toDouble(Object value){
		return toDouble(value, null);
	}
	public static Double toDouble(Object value, Double defaultValue){
			if(value == null){
				return defaultValue;
			}
			Double result = defaultValue;
			try{
				String valueClass = value.getClass().getSimpleName();
				//基本类型数据
				if("Double".indexOf(valueClass) != -1){
					result = (Double)value;
				} else {
					result = Double.parseDouble(value.toString().trim());
				}
				return result;

			}catch(ClassCastException cce){
				try{
					result = Double.parseDouble(value.toString().trim());
					return result;
				} catch(Exception e){
					//e.printStackTrace();
				}
			}catch(Exception e){
				//e.printStackTrace();
			}
			return defaultValue;
	}
	/**
	 * 获取最大值
	 * @return
	 */
	public static BigDecimal max(BigDecimal... ds){ 
		if(ds == null || ds.length == 0){
			return null;
		}
		BigDecimal max = ds[0]; 	    	
		for(BigDecimal d : ds){
			if(d.compareTo(max) > 0){
				max = d;
			}
		}
		return max;	    	
	} 
	
	/**
	 * 获取最小值
	 * @return
	 */
	public static BigDecimal min(BigDecimal... ds){ 
		if(ds == null || ds.length == 0){
			return null;
		}
		BigDecimal min = ds[0]; 	    	
		for(BigDecimal d : ds){
			if(d.compareTo(min) < 0){
				min = d;
			}
		}
		return min;	    	
	} 
	
	
	//---------浮点数加减乘除---------
	/*
	  在进行数字运算时，如果有double或float类型的浮点数参与计算，偶尔会出现计算不准确的情况
	 	System.out.println(0.05+0.01); //==>0.060000000000000005 
    	System.out.println(1.0-0.42);  //==>0.5800000000000001
    	System.out.println(19.9*100); //==>1989.9999999999998
    	System.out.println(123.3/100); //==>1.2329999999999999  
    	
    	《Effective Java》中提到一个原则，那就是float和double只能用来作科学计算或者是工程计算，
    	但在商业计算中我们要用java.math.BigDecimal
	 */
	public static BigDecimal getDecimal(Object d){
		if(d == null){
			return null;
		}
		if(d instanceof BigDecimal){
			return (BigDecimal)d;
		}
		return new BigDecimal(String.valueOf(d));
	}
	/**
	 * d1 + d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double add(Object d1, Object d2){  		
    	BigDecimal b1 = getDecimal(d1);
        BigDecimal b2 = getDecimal(d2);
        return b1.add(b2).doubleValue(); 
	}
	/**
	 * d1 + d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal addBD(Object d1, Object d2){  		
    	BigDecimal b1 = getDecimal(d1);
        BigDecimal b2 = getDecimal(d2);
        return b1.add(b2); 
	}
	/**
	 * 元转分
	 * @param price 要转换的金额，单位：元
	 * @return
	 */
	public static Integer yuanToFen(BigDecimal price){
		if(isNull0(price)){
			return 0;
		}
		Integer amount = toInteger(mul(price, 100));
		return amount;
	}
    /**
     * 分转元
     * @param amount 要转换的金额，单位：分
     * @return
     */
    public static BigDecimal fenToYuan(Integer amount){
        if(isNull0(amount)){
            return BigDecimal.ZERO;
        }
        BigDecimal price = divBD(new BigDecimal(amount), new BigDecimal(100));
        //BigDecimal price = toDecimal(div(amount, 100));
        return price;
    }

    /**
	 * d1 - d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
	 * @param d1
	 * @param d2
	 * @return
	 */
    public static double sub(Object d1,Object d2){  
    	BigDecimal b1 = getDecimal(d1);
        BigDecimal b2 = getDecimal(d2);
        return b1.subtract(b2).doubleValue(); 
    } 
    /**
   	 * d1 - d2
   	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
   	 * @param d1
   	 * @param d2
   	 * @return
   	 */
       public static BigDecimal subBD(Object d1,Object d2){  
       	   BigDecimal b1 = getDecimal(d1);
           BigDecimal b2 = getDecimal(d2);
           return b1.subtract(b2); 
       }
    
     /**
      * d1*d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
      * @param d1
      * @param d2
      * @return
      */
    public static double mul(Object d1,Object d2){  
    	BigDecimal b1 = getDecimal(d1);
        BigDecimal b2 = getDecimal(d2);
        return b1.multiply(b2).doubleValue();  
    }
    /**
     * d1*d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
     * @param d1
     * @param d2
     * @return
     */
   public static BigDecimal mulBD(Object d1,Object d2){  
   	   BigDecimal b1 = getDecimal(d1);
       BigDecimal b2 = getDecimal(d2);
       return b1.multiply(b2).setScale(2, BigDecimal.ROUND_HALF_UP);
   }
    
    /**
     * d1/d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
     * @param d1
     * @param d2
     * @return
     */
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
    /**
     * d1/d2
	 * <p>浮点数加减乘除用float、double直接运算，有时会出现精度不准确的情况
     * @param d1
     * @param d2
     * @param scale 四舍五入保留小数位数
     * @return
     */
    public static BigDecimal divBD(Object d1, Object d2, int scale){  
        if(scale<0){
        	scale = 2;
        }
    	BigDecimal b1 = getDecimal(d1);
        BigDecimal b2 = getDecimal(d2);
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP);            
    }
    
    public static BigDecimal min(BigDecimal b1, BigDecimal b2){
        return b1.compareTo(b2)<0 ? b1 : b2;            
    }

    public static BigDecimal max(BigDecimal b1, BigDecimal b2){
        return b1.compareTo(b2)>0 ? b1 : b2;            
    }
	//-------------------------------
	
	/**
	 * 转为BigDecimal并保留指定小数位数
	 * @param value
	 * @param scale - 小数位数
	 * @return
	 */
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
			e.printStackTrace();
			return defaultValue;
		}
	}
	
	/**
	 * 转为BigDecimal并保留指定小数位数
	 * @param value
	 * @param scale - 小数位数
	 * @return
	 */
	public static BigDecimal toDecimal(Object value, int scale){
		if(value == null){
			return null;
		}
		if(value instanceof BigDecimal){
			return (BigDecimal)value;
		}
		try{
			Double doubleValue = toDouble(value, 0.00d);
			String decimalString = getDecimalAutoStr(doubleValue, scale);
			BigDecimal decimal = new BigDecimal(decimalString);
			return decimal;
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 转为BigDecimal并保留2位小数
	 * @param value
	 * @return
	 */
	public static BigDecimal toDecimal(Object value){
		return toDecimal(value, 2);
	}
	
	public static Long toLong(Object value){
		return toLong(value, null);
	}
	public static Long toLong(Object value, Long defaultValue){
		if(value == null){
			return defaultValue;
		}
		Long result = defaultValue;
		try{
			result = (Long)value;
			return result;
		}catch(ClassCastException cce){
			if(value instanceof Double) {
				return ((Double) value).longValue();
			}else if(value instanceof Float) {
				return ((Float) value).longValue();
			}
			try{
				result = Long.parseLong(value.toString().trim());
				return result;
			} catch(Exception e){
				//e.printStackTrace();
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		return defaultValue;
	}
		
	public static Short toShort(Object value){
		return toShort(value, null);
	}
	public static Short toShort(Object value, Short defaultValue){
		if(value == null){
			return defaultValue;
		}
		Short result = defaultValue;
		try{
			result = (Short)value;
			return result;
		}catch(ClassCastException cce){
			try{
				result = Short.parseShort(value.toString().trim());
				return result;
			} catch(Exception e){
				//e.printStackTrace();
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		return defaultValue;
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
	public static Boolean toBoolean(Object value){
		return toBoolean(value, null);
	}
	public static Boolean toBoolean(Object value, Boolean defaultValue){
		if(value == null){
			return defaultValue;
		}
		Boolean result = defaultValue;
		try{
			result = (Boolean)value;
			return result;
		}catch(ClassCastException cce){
			String strValue = value.toString().trim();
			if("true".equalsIgnoreCase(strValue) || "yes".equalsIgnoreCase(strValue) || "1".equalsIgnoreCase(strValue)){
				return true;
			}
			return false;
		}catch(Exception e){
			//e.printStackTrace();
		}
		return defaultValue;
	}
	public static List<Boolean> toBooleanList(List<Object> list){
		List<Boolean> result = new ArrayList<Boolean>();
		if(list != null){
			for(Object obj : list){
				Boolean temp = QYUtils.toBoolean(obj);
				if(temp != null){
					result.add(temp);
				}
			}
		}
		return result;		
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
	/**
	 * <p>Checks if a String is whitespace, empty ("") or null(null, "null").</p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("null")    = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str  the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 */
	public static boolean isBlank(String str){
		if(StringUtils.isBlank(str)){
			return true;
		}
		if("null".equalsIgnoreCase(str.trim())){
			return true;
		}
		return false;
	}
	/**
	 * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
	 *
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("null")    = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param str  the String to check, may be null
	 * @return <code>true</code> if the String is
	 *  not empty and not null and not whitespace
	 */
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	
	public static String trim(String str){
		if(str==null){
			return "";
		}
		return str.replaceAll("　", " ").trim();
	}
	public static String trimSpecialChar(String str){
		if(StringUtils.isBlank(str)){
			return str;
		}
		return str.replaceAll("[~`!+@#$%^&*(){}\\[\\]\\\\;\"'/?<>,.]", "");
	}

	public static <E> boolean isEmpty(Collection<E> c){
		return c==null || c.isEmpty();
	}
	public static <K,V> boolean isEmpty(Map<K, V> m){
		return m==null || m.isEmpty();
	}
	public static <E> boolean isNotEmpty(Collection<E> c){
		return !isEmpty(c);
	}
	public static <K,V> boolean isNotEmpty(Map<K, V> m){
		return !isEmpty(m);
	}
	
	/** 
	 * 检测邮箱地址是否合法 
	 * @param email 
	 * @return true合法 false不合法 
	 */  
	public static boolean isEmail(String email){  
	  if(StringUtils.isBlank(email)) return false;
	  email = email.trim();
	  Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
	  Matcher m = p.matcher(email);  
	  return m.matches();  
	 }

	/** 
	 * 检测伪邮箱地址是否合法,如a#c.com
	 * @param email 
	 * @param pseudo - 伪装符号,如:#
	 * @return true合法 false不合法 
	 */  
	public static boolean isPseudoEmail(String email, String pseudo){  
	  if(StringUtils.isBlank(email)) return false;
	  email = email.trim();
	  Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*"+pseudo+"\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配  
	  Matcher m = p.matcher(email);  
	  return m.matches();  
	}
	/** 
	 * 将伪邮箱地址转为合法邮箱,如a#c.com --> a@c.com
	 * @param email 
	 * @param pseudo - 伪装符号,如:#
	 * @return 
	 */  
	public static String transPseudo2Email(String email, String pseudo){  
		 if(StringUtils.isBlank(email)) return "";
		return email.replaceAll(pseudo, "@");
	}
	/** 
	 * 将合法邮箱转为伪邮箱地址,如a@c.com --> a#c.com
	 * @param email 
	 * @param pseudo - 伪装符号,如:#
	 * @return 
	 */  
	public static String transEmail2Pseudo(String email, String pseudo){  
		 if(StringUtils.isBlank(email)) return "";
		return email.replaceAll("@", pseudo);
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
	/**
	 * <pre>
	 * 替换字符串模板中的变量,变量格式{n}, n为变量序号,从0开始
	 * 如：xxxx{0}yyy{1}zzz,
	 * </pre>
	 * @param template - 字符串模板
	 * @param values - 替换后的值列表
	 * @return
	 */
	public static String format(String template, Object... values){
		return parseTemplate(template, values);
	}

    /**
     * <pre>
     * 按顺序替换字符串模板中的占位符,占位符使用{}
     * 如：xxxx{}yyy{}zzz,
     * </pre>
     * @param template - 字符串模板
     * @param values - 替换后的值顺序列表
     * @return
     */
    public static String template(String template, Object... values){
        if(StringUtils.isBlank(template)){
            return "";
        }
        String format = template.replaceAll("\\{\\s*\\d*\\s*\\}", "%s");

        int count = StringUtils.countMatches(format, "%s");
        if(count > values.length){
            int missing = count - values.length;
            Object[] newValues = Arrays.copyOf(values, count);
            Arrays.fill(newValues, values.length, count, "");
            return String.format(format, newValues);
        } else {
            return String.format(format, values);
        }
    }
	/**
	 * 条件表达式
	 * @param cond
	 * @param trueResult
	 * @param falseResult
	 * @return
	 */
	public static Object condExp(Object cond, Object trueResult, Object falseResult){
		boolean flag = true;
		if(cond == null){
			flag = false;
		} else {
			String clazz = cond.getClass().getSimpleName();
			if("Boolean".equals(clazz)){
				flag = QYUtils.toBoolean(cond);
			} else if("String".equals(clazz)){
				flag = QYUtils.isNotBlank((String)cond);
			}
		}
		return flag ? trueResult : falseResult;
	}
	/**
	 * <pre>
	 * 替换字符串模板中的变量,变量格式{n}, n为变量序号,从0开始
	 * 如：xxxx{0}yyy{1}zzz,
	 * </pre>
	 * @param template - 字符串模板
	 * @param values - 替换后的值列表
	 * @return
	 */
	public static String parseTemplate(String template, Object... values){
	    if(true){
	        return template(template, values);
        }

		String result = template;
		
		for(int i=0; i<values.length; i++){
			result = result.replaceAll("\\{"+i+"\\}", String.valueOf(values[i]));
		}
		
		return result;
	}

	/**
	 *  将template中的{key}占位符替换为context中对应的value
	 * @param template
	 * @param context
	 * @return
	 */
	public static String parseTemplate(String template, Map<String,Object> context){
		if(StringUtils.isBlank(template) || QYUtils.isEmpty(context) ){
			return template;
		}
		for(String key : context.keySet()){
			template = template.replaceAll("\\{"+key+"\\}", QYUtils.toString(context.get(key)));
		}
		return template;
	}
	/**
	 * 转义正则特殊字符 （$()*+.[]?\^{},|）
	 * 
	 * @param keyword
	 * @return
	 */
	public static String escapeRegexChar(String keyword) {
		if (StringUtils.isNotBlank(keyword)) {
			//注意:\\需要第一个替换，否则replace方法替换时会有逻辑bug
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}

	/** 
	  * 替换一个字符串中的某些指定字符 
	  * @param strData String 原始字符串 
	  * @param regex String 要替换的字符串 
	  * @param replacement String 替代字符串 
	  * @return String 替换后的字符串 
	  */  
	 public static String replaceString(String strData, String regex,  
			 String replacement)  
	 {  
		 if (strData == null)  
		 {  
			 return null;  
		 }  
		 int index;  
		 index = strData.indexOf(regex);  
		 String strNew = "";  
		 if (index >= 0)  
		 {  
			 while (index >= 0)  
			 {  
				 strNew += strData.substring(0, index) + replacement;  
				 strData = strData.substring(index + regex.length());  
				 index = strData.indexOf(regex);  
			 }  
			 strNew += strData;  
			 return strNew;  
		 }  
		 return strData;  
	 }  

		/**
		 * 将含有emoji别名的 字符串转换为Unicode字符串<p>
		 * 如: 哈哈:smile:[e]smile[/e]! ==> 哈哈【表情】!
		 * @param content
		 */
		public static String parseEmojiToUnicode(Object content){
            if(content == null){
                return "";
            }
            String str = String.valueOf(content);
            if(StringUtils.isBlank(str)){
                return "";
            }
            str = str.replaceAll("\\[e\\](.+?)\\[/e\\]", ":$1:");

            Pattern p = Pattern.compile(":(.+):", Pattern.MULTILINE);
            Matcher m = p.matcher(str);
            if(m.find()){
                return 	EmojiParser.parseToUnicode(str);
            } else {
                return str;
            }
        }

	    public static String formatString(String oldStr){
		    if(QYUtils.isBlank(oldStr)){
		        return "";
            }
		    return oldStr.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
	    }

		/**
		 * 将emoji Unicode字符串转换为含有emoji别名的字符串<p>
		 * 如: 哈哈【表情】【表情】!==> 哈哈:smile::smile:! 
		 * @param content
		 */
		public static String parseEmojiToAliases(Object content){
			if(content == null){
				return "";
			}
			String str = String.valueOf(content);
			if(StringUtils.isBlank(str)){
				return "";
			}
			String transed = EmojiParser.parseToAliases(str);
			
			//最后再去掉未识别的4字节字符
			return trans4bytesChar(transed, "");
		}
		
	/**
	 * 替换字符串中特殊字符为HTML实体, &-->&amp;
	 * @param strData
	 * @return
	 */
	public static String encodeString(String strData)
	 {  
		 if(StringUtils.isBlank(strData)) {  
			 return "";  
		 }

		 //先反转义，再转义，避免重复转义
		 strData = decodeString(strData);
         return StringEscapeUtils.escapeHtml(strData);

         /*
		 strData = replaceString(strData, "&", "&amp;");  
		 strData = replaceString(strData, "<", "&lt;");  
		 strData = replaceString(strData, ">", "&gt;");  
		 strData = replaceString(strData, "\"", "&quot;"); 
		 strData = replaceString(strData, "'", "&apos;");  
		 //strData = replaceString(strData, "&apos;", "&apos;"); 
		 //strData = replaceString(strData, "\\", "&quot;");
		 
		 return strData;
		 */
	 } 
	public static String urlEncoder(String str){
		return encodeHTML(str);
	}
	public static String encodeHTML(String str){
		try {
			if(StringUtils.isBlank(str)){
				return "";
			}
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}
	public static String urlDecoder(String str){
		return decodeHTML(str);
	}
	public static String decodeHTML(String str){
		try {
			if(StringUtils.isBlank(str)){
				return "";
			}
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return str;
	}
	/**
	 * 替换字符串中HTML实体为特殊字符, &amp;-->&
	 * @param strData
	 * @return
	 */
	public static String decodeString(String strData)
	 {  
		 if(StringUtils.isBlank(strData)) {  
			 return "";
		 }
         return StringEscapeUtils.unescapeHtml(strData);

		 /*
		 strData = strData.replaceAll("&lt;", "<")
			.replaceAll("&gt;", ">")
			.replaceAll("&quot;", "\"")
			.replaceAll("&apos;", "'")
			.replaceAll("&amp;", "&");
		 return strData;
		 */

     }

	/**
	 * 获取字符串的字节长度,一个汉字2个字节长度
	 * @param s
	 * @return
	 */
	 public static int len(String s){
		s = s.replaceAll("[^\\x00-\\xff]", "**");
		int length = s.length();
		return length;
	 } 

	 public static String getString(HttpServletRequest request, String paramName){
		 String value = request.getParameter(paramName);
		 return value;
	 }
	 public static Integer getInteger(HttpServletRequest request, String paramName){
		 String value = request.getParameter(paramName);
		 return toInteger(value);
	 }
	 public static Boolean getBoolean(HttpServletRequest request, String paramName){
		 String value = request.getParameter(paramName);
		 return toBoolean(value);
	 }
	 public static Date getDate(HttpServletRequest request, String paramName){
		 String value = request.getParameter(paramName);
		 return toDate(value);
	 }
		 
	 /**
	  * 根据账号，判断其类型
	  * @param account
	  * @return 0:未知类型，1:手机号, 2:邮箱, 3:普通电话号码(存于phoneNumber2)
	  */
	 public static int getAccountType(String account){
		 if(StringUtils.isBlank(account)){
			 return 0;
		 }
		 //去除"_用户类型"后缀
		 account = account.trim().replaceAll("(^.+)_[12]{1}$", "$1");
		 
		 if(account.matches("^1\\d{10}$")){//有效的11位手机号
			 return 1;
		 }

		 if(isEmail(account)){
			 return 2;
		 }
		 
		 if(account.matches("^\\d{11}$")){//11位非手机号的电话号码(存在phoneNumber2中)
			 return 3;
		 }
		 
		 return 0;
	 }
	 public static boolean isMobilePhone(String phone){
		 if(StringUtils.isBlank(phone)){
			 return false;
		 }
		 if(phone.matches("^1\\d{10}$")){//有效的11位手机号
			 return true;
		 }
		 return false;
	 }
	 public static boolean isMobilePhone_new(String phone){
		 if(StringUtils.isBlank(phone)){
			 return false;
		 }
		 if(phone.matches("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$")){//有效的11位手机号
			 return true;
		 }
		 return false;
	 }
	 
	 

	 public static String getString(Object obj){
		 return toString(obj);
	 }
	 
	 /**
	  * 取得字符串的字节长度
	  * 
	  * @param str
	  * @param chineseLength - 一个中文字符占的长度
	  * @return
	  */
	 public static int getLength(String str, int chineseLength){
		 if(StringUtils.isBlank(str)){
			 return 0;
		 }
		 StringBuilder sb = new StringBuilder();
		 for(int i=0; i<chineseLength; i++){
			 sb.append("_");
		 }
		 String replacer = sb.toString();
		 return str.replaceAll("[\u4E00-\u9FA5]", replacer).length();
	 }
	 /**
	  * 取得字符串的字节长度(字一个汉字两个字符)
	  * 
	  * @param str
	  * @return
	  */
	 public static int getLength(String str){
		 if(StringUtils.isBlank(str)){
			 return 0;
		 }
		 return str.replaceAll("[\u4E00-\u9FA5]", "__").length();
	 }
	 /**
	  * 取得比例
	  * @param fenzi - 分子
	  * @param fenmu - 分母
	  * @return
	  */
	 public static double getRatio(long fenzi, long fenmu){
		if(fenzi==0 || fenmu==0){
			return 0;
		}
		return (double)fenzi/fenmu;
	 }
	 
	 /**
	  * 取得比例
	  * @param fenzi - 分子
	  * @param fenmu - 分母
	  * @return
	  */
	 public static float getRate(int fenzi, int fenmu){
		if(fenzi==0 || fenmu==0){
			return 0f;
		}
		return (float)fenzi/fenmu;
	 }
	
	 public static float getRate(float fenzi, int fenmu){
		if(fenzi==0 || fenmu==0){
			return 0f;
		}
		return (float)fenzi/fenmu;
	 }
	 public static float getRate(int fenzi, float fenmu){
		if(fenzi==0 || fenmu==0){
			return 0f;
		}
		return (float)fenzi/fenmu;
	 }
	 public static float getRate(float fenzi, float fenmu){
		if(fenzi==0 || fenmu==0){
			return 0f;
		}
		return (float)fenzi/fenmu;
	 }
	 public static String getRateStr(float rate){
		 return String.format("%.2f", rate * 100) + "%";
	 }
	 /**
	  * 获取小数显示值
	  * @param decimal - 小数
	  * @param scale -最大小数位数
	  * @return
	  */
	 public static String getDecimalAutoStr(float decimal, int scale){
		 NumberFormat nf = NumberFormat.getInstance();  
		 // 设置是否使用分组  
		 nf.setGroupingUsed(false);   
		 // 设置最小整数位数  
		 nf.setMaximumFractionDigits(scale);  
		 
		 return nf.format(decimal);
	 }
	 
	 /**
	  * 获取小数显示值
	  * @param decimal - 小数
	  * @param scale -最大小数位数
	  * @return
	  */
	 public static String getDecimalAutoStr(double decimal, int scale){
		 NumberFormat nf = NumberFormat.getInstance();  
		 // 设置是否使用分组  
		 nf.setGroupingUsed(false);   
		 // 设置最小整数位数  
		 nf.setMaximumFractionDigits(scale);  
		 
		 return nf.format(decimal);
	 }
	 /**
	  * 获取小数显示值
	  * @param decimal - 小数
	  * @param scale -最大小数位数
	  * @return
	  */
	 public static String getDecimalAutoStr(BigDecimal decimal, int scale){
	     if(decimal == null){
	         return "0";
         }
		 NumberFormat nf = NumberFormat.getInstance();  
		 // 设置是否使用分组  
		 nf.setGroupingUsed(false);   
		 // 设置最小整数位数  
		 nf.setMaximumFractionDigits(scale);  
		 
		 return nf.format(decimal);
	 }
	 /**
	  * 获取小数显示值
	  * @param decimal - 小数
	  * @param scale -固定小数位数
	  * @return
	  */
	 public static String getDecimalStr(double decimal, int scale){
		 return String.format("%."+scale+"f", decimal);
	 }
	 /**
	  * 获取小数显示值
	  * @param decimal - 小数
	  * @param scale -固定小数位数
	  * @return
	  */
	 public static String getDecimalStr(float decimal, int scale){
		 return String.format("%."+scale+"f", decimal);
	 }
	 /**
	  * 获取小数显示值
	  * @param decimal - 小数
	  * @param scale -固定小数位数
	  * @return
	  */
	 public static String getDecimalStr(BigDecimal decimal, int scale){
		 return String.format("%."+scale+"f", decimal);
	 }
	/**
	 * <p>Escapes the characters in a <code>String</code> to be suitable to pass to
	 * an SQL query.</p>
	 *
	 * <p>For example,
	 * <pre>statement.executeQuery("SELECT * FROM MOVIES WHERE TITLE='" + 
	 *   StringEscapeUtils.escapeSql("McHale's Navy") + 
	 *   "'");</pre>
	 * </p>
	 *
	 * <p>At present, this method only turns single-quotes into doubled single-quotes
	 * (<code>"McHale's Navy"</code> => <code>"McHale''s Navy"</code>). It does not
	 * handle the cases of percent (%) or underscore (_) for use in LIKE clauses.</p>
	 *
	 * see http://www.jguru.com/faq/view.jsp?EID=8881
	 * @param str  the string to escape, may be null
	 * @return a new String, escaped for SQL, <code>null</code> if null string input
	 */
	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return StringUtils.replace(str, "'", "\\'");
	}
	 public static String makeUUID(){
		return uuid();
	 }
	 public static String uuid(){
		return UUID.randomUUID().toString();
	 }
	 public static String uuidShort(){
			return uuid().replaceAll("-", "");
	 }

	/**
	 * Map拷贝
	 * @param from
	 * @param to
	 * @param fromKeys 源Map中要拷贝的键,逗号分割
	 */
	public static Map<String, Object> copyMap(Map<String, Object> from,  Map<String, Object> to, String fromKeys){
		return copyMap(from, to, fromKeys, fromKeys);
	}
	/**
	 * Map拷贝
	 * @param from
	 * @param to
	 * @param fromKeys 	源Map中要拷贝的键,逗号分割
	 * @param toKeys	目标Map中要设置的键,逗号分割
	 */
	public static Map<String, Object> copyMap(Map<String, Object> from,  Map<String, Object> to, String fromKeys, String toKeys){
		if(to == null){
			to = new HashMap<String, Object>();
		}
		if(from == null){
			return to;
		}
		if(StringUtils.isBlank(fromKeys)){
			return to;
		}
		if(StringUtils.isBlank(toKeys)){
			toKeys = fromKeys;
		}
		String [] fromKeyArr = fromKeys.split(",");
		String [] toKeyArr = toKeys.split(",");
		int len = Math.min(fromKeyArr.length, toKeyArr.length);
		for(int i=0; i<len; i++){
			String fromKey = fromKeyArr[i];
			String toKey = toKeyArr[i];
			if(StringUtils.isBlank(fromKey) || StringUtils.isBlank(toKey)){
				continue;
			}
			fromKey = fromKey.trim();
			toKey = toKey.trim();
			to.put(toKey, from.get(fromKey));
		}
		return to;
	}	
	
	/**
	 * 将 str 的 beginIndex~endIndex部分用replacement替换
	 * @param str
	 * @param beginIndex 	起始位置，从0开始，包含在替换范围内
	 * @param endIndex		结束位置，从0开始，包含在替换范围内
	 * @param replacement
	 * @return
	 */
	public static String subReplace(String str, int beginIndex, int endIndex, String replacement){	
		if(StringUtils.isBlank(str)){
			return "";
		}
		return str.substring(0, beginIndex) + replacement + str.substring(endIndex+1);
	}
	/**
	 * 将一个元素加入到唯一性列表尾部
	 * @param list
	 * @param element
	 * @return
	 */
	public static String addToList(String list, Object element){
		return addToList(list, element, true);
	}
	/**
	 * 将一个元素加入到列表中尾部
	 * @param list
	 * @param element
	 * @param unique
	 * @return
	 */
	public static String addToList(String list, Object element, boolean unique){
		return addToList(list, element, unique, true);
	}
	/**
	 * @param count
	 * @param limit
	 * @return
	 */
	public static String showCount(int count, int limit){
		return count<=limit ? count+"" : limit+"+";
	}
	/**
	 * @param count
	 * @return
	 */
	public static String showCount(int count){
		return showCount(count, 99);
	}
	/**
	 * @param count
	 * @param limit
	 * @return
	 */
	public static String showCount(long count, int limit){
		return count<=limit ? count+"" : limit+"+";
	}
	/**
	 * @param count
	 * @return
	 */
	public static String showCount(long count){
		return showCount(count, 99);
	}
	/**
	 * 将一个元素加入到列表中
	 * @param list
	 * @param element
	 * @param unique
	 * @param add2Tail  - 是否从队尾追加， true:队尾追加, false:队首添加
	 * @return
	 */
	public static String addToList(String list, Object element, boolean unique, boolean add2Tail){
		if(element == null){
			return list;
		}
		String str = String.valueOf(element).trim();
		if(StringUtils.isBlank(list)){
			return str;
		}
		list = list.trim();
		if(unique && isInList(list, str)){
			//return list;
		} else {
			if(add2Tail){
				list = list + "," + str;
			} else {
				list =  str + "," + list;
			}
		}
		
		list = list.replaceAll("\\s+", "").replaceAll("^,*", "").replaceAll(",*$", "").replaceAll(",+", ",");		
		return list;		
	}


	/**
	 * 将一个元素从列表中移除
	 * @param list
	 * @param element
	 * @return
	 */
	public static String removeFromList(String list, Object element){
		if(element == null || list == null){
			return list;
		}
		if(StringUtils.isBlank(list)){
			return list;
		}
		String str = String.valueOf(element).trim();
		if(StringUtils.isBlank(str)){
			return list;
		}	
		list = ","+list.trim()+",";				
		list = list.replaceAll(","+str+",", ",").replaceAll("\\s+", "").replaceAll("^,*", "").replaceAll(",*$", "").replaceAll(",+", ",");		
		return list;		
	}

	/**
	 * 从list中移除exclude中的元素
	 * @param list
	 * @param exclude
	 * @return
	 */
	public static String removeExcludeFromList(String list, String exclude){
		String srcList = formatList(list);
		String excludeList = formatList(exclude);
		if(StringUtils.isBlank(srcList)){
			return "";
		}
		if(StringUtils.isBlank(excludeList)){
			return srcList;
		}

		//String regex = "(,"+excludeList.replaceAll(",", ",|,")+",)";
		//String result = formatList((","+srcList+",").replaceAll(regex,","));
        String[] parts = excludeList.split(",");
        String result = ","+list+",";
		for(String part : parts){
            result = result.replaceAll(","+part+",", ",");
        }
		return formatList(result);
	}

	
	/**
	 * 删除Map中的键值
	 * @param map
	 * @param keys
	 */
	public static Map<String, Object> deleteMapKeys(Map<String, Object> map, String keys){
		if(map == null){
			return null;
		}
		if(StringUtils.isBlank(keys)){
			return map;
		}
		String [] keyArr = keys.split(",");
		for(String key : keyArr){
			key = key.trim();
			map.remove(key);
		}
		return map;
	}
	/**
	 * 删除Map中的键值
	 * @param map
	 * @param keys
	 */
	public static Map<String, Object> removeMapKeys(Map<String, Object> map, String keys){
		return deleteMapKeys(map, keys);
	}
    /**
     * 合并URL。 从后往前合并，如果合并完后面的部分出现完整的url，则前面的部分丢弃
     */
    public static String mergeUrl(String...parts){
        int length = parts.length;
        StringBuilder urlSb = new StringBuilder();
        for(int i=length-1; i>=0; i--){
            String part = parts[i];
            if(StringUtils.isBlank(part)){
                continue;
            }

            if(urlSb.length() > 0){
                urlSb.insert(0, "/");
            }
            urlSb.insert(0, part);
            if(part.matches("^http(s?)://.+$")){
                break;
            }
        }

        String url = urlSb.toString()
                .replaceAll("///","/")
                .replaceAll("\\/\\?","?")
                .replaceAll("://", ":##")
                .replaceAll("/+", "/")
                .replaceAll(":##", "://");

        return url;
    }
	public static String mergeUrlOld(String...parts){
		String url = StringUtils.join(parts, "/");		
		//return url.replaceAll("/+", "/");
		url = url.replaceAll("///","/").replaceAll("\\/\\?","?");
		return url.replaceAll("://", ":##").replaceAll("/+", "/").replaceAll(":##", "://");
	}

	/**
	 * 从fastdfs的完整文件路径中截取出文件id
	 * @param fastDfsFullUrl 如，http://123.57.255.1:8068/group1/M00/00/08/ezn_AVU8uYOAHIdKAAEKVm1GFRg786.apk
	 * @return group1/M00/00/08/ezn_AVU8uYOAHIdKAAEKVm1GFRg786.apk
	 */
	public static String getFastDfsFileId(String fastDfsFullUrl){	
		if(StringUtils.isBlank(fastDfsFullUrl)){
			return "";
		}
		if(fastDfsFullUrl.indexOf("://") == -1){
			return  fastDfsFullUrl.replaceAll("^\\s*/", "");
		}
		return fastDfsFullUrl.replaceAll("^.*://", "").replaceAll("^.*?/", "");
	}
	/**
	 * 克隆一个map，并添加一个随机键randomKey
	 * @param map
	 * @return
	 */
	public static Map<String, Object> genNewMap(Map<String, Object> map){
		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap.putAll(map);
		newMap.put("randomKey", System.currentTimeMillis() + "_" + random6());
		return newMap;
	}
	
	/**
	 * 将布尔值转化为0和1
	 * @return
	 */
	public static ValueFilter getBooleanValueFilter(){
		 return new ValueFilter() {
			public Object process(Object object, String propertyName, Object propertyValue) {
			   if(propertyValue == null){
				   return null;
			   }
			   if(propertyValue instanceof Boolean){	       		   
				   return  (Boolean)propertyValue ?  1 : 0;
			   }
			   return propertyValue;
			}
		};
	}
	
	/**
	 * 解析emoji表情
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static String transEmoji(String str){
		String pattern = "\\[e\\]\\s*(.+?)\\s*\\[/e\\]";
		Pattern p = Pattern.compile(pattern); 
		Matcher matcher = p.matcher(str);
		if(matcher.find()){
			str = matcher.replaceAll("<span class=\"emoji emoji-$1\"></span>");
		}
		return str;
	}

	/**
	 * 去掉emoji表情
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static String killEmoji(String str){

		String pattern = "\\[e\\]\\s*(.+?)\\s*\\[/e\\]";
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(str);
		if(matcher.find()){
			str = matcher.replaceAll("[表情]");
		}
		return str;
	}
	
	/**
	* 数字不足位数左补0
	* @param str
	* @param strLength
	*/
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);//左补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
	
	/**
	 * 保留两位小数
	 * @param param1  分子
	 * @param param2 分母
	 */
	public static double getRatio(double param1, double param2){
		
		if(param2 == 0){
			return 0;
		}
		
		BigDecimal bg = new BigDecimal(param1 / param2);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return f1;
	}
	/**
	 * 获取 taskName 对应的 triggerName
	 * @param taskName
	 * @return
	 */
	public static String getTriggerName(String taskName){
		return "CronTrigger_" + taskName;
	}
	
	/** 
	 * 获取本机所有IP 
	 */  
	public static String[] getAllLocalHostIP() {  
		List<String> res = new ArrayList<String>();  
		Enumeration<NetworkInterface> netInterfaces;  
		try {  
			netInterfaces = NetworkInterface.getNetworkInterfaces();  
			InetAddress ip = null;  
			while (netInterfaces.hasMoreElements()) {  
				NetworkInterface ni = netInterfaces.nextElement();  
				Enumeration<InetAddress> nii = ni.getInetAddresses();  
				while (nii.hasMoreElements()) {  
					ip = nii.nextElement();  
					if (ip.getHostAddress().indexOf(":") == -1) {  
						res.add(ip.getHostAddress());  
						//System.out.println("本机的ip=" + ip.getHostAddress());  
					}  
				}  
			}  
		} catch (SocketException e) {  
		}  
		return (String[]) res.toArray(new String[0]);  
	}
	
	/**
	 * 转换成环信用户id
	 * @param userId
	 * @return
	 */
	public static String getEasemobUsername(Integer userId){
		return "jyft_" + userId;
	}
	

	/**
	 * 将nativeQuery的结果转为List<Map>
	 * @param sqlResults - nativeQuery 查询结果
	 * @param keyStr - 一条记录中的各个字段名有序拼串
	 * @return
	 */
	public static List<Map<String, Object>> sqlResultToList(List<Object[]> sqlResults, String keyStr){
		return sqlResultToList(sqlResults, keyStr, false, false);
	}
	/**
	 * 将nativeQuery的结果转为List<Map>
	 * @param sqlResults - nativeQuery 查询结果
	 * @param keyStr - 一条记录中的各个字段名有序拼串
	 * @param useStringValue - 是否都统一使用字符串值
	 * @return
	 */
	public static List<Map<String, Object>> sqlResultToList(List<Object[]> sqlResults, String keyStr, boolean useStringValue){
		return sqlResultToList(sqlResults, keyStr, useStringValue, false);
	}
	/**
	 * 将nativeQuery的结果转为List<Map>
	 * @param sqlResults - nativeQuery 查询结果
	 * @param keyStr - 一条记录中的各个字段名有序拼串
	 * @param useStringValue - 是否都统一使用字符串值
	 * @param date4excel - 日期字段是否要针对导出excel做特殊处理
	 * @return
	 */
	public static List<Map<String, Object>> sqlResultToList(List<Object[]> sqlResults, String keyStr, boolean useStringValue, boolean date4excel){
		List<Map<String, Object>> list = new ArrayList<>();
		if(sqlResults == null || sqlResults.isEmpty()){
			return list;
		}
		
		String[] keys = keyStr.split(",");
		for(Object[] objs: sqlResults){ 
			Map<String, Object> map = new HashMap<String, Object>();
			int count = Math.min(objs.length, keys.length);
			for(int i=0; i<count; i++){
				//将java.math.BigInteger等复杂类型转换简单类型
				Object value = objs[i];
				String thisKey = QYUtils.trim(keys[i]);
				if(value != null){
					if(useStringValue){
						String className = value.getClass().getSimpleName();
						if("Date".equals(className) || "Timestamp".equals(className)){
							if(date4excel){
								map.put(thisKey, DateUtils.DateToString((Date)value, "yyyy-MM-dd'T'HH:mm:ss"));	
							} else {
								map.put(thisKey, DateUtils.DateToString((Date)value, "yyyy-MM-dd HH:mm:ss"));									
							}
						} else {
							map.put(thisKey, String.valueOf(value));
						}
					} else {
						String className = value.getClass().getSimpleName();
						if("BigInteger".equals(className)){
							map.put(thisKey, toLong(value));	
						} else if("BigDecimal".equals(className)){
							map.put(thisKey, toDouble(value));	
						} else {
							map.put(thisKey, value);						
						}
					}
				} else {
					map.put(thisKey, null);
				}
				
			}
			list.add(map);					
		}
		return list;
	}
	
	/**
	 * 将nativeQuery的结果转为Map
	 * @param sqlResults - nativeQuery 查询结果
	 * @param keyStr - 对应map中的各个字段名有序拼串
	 * @return
	 */
	public static Map<String, Object> sqlResultToMap(List<Object[]> sqlResults, String keyStr){
		Map<String, Object> map = new HashMap<>();
		if(sqlResults == null || sqlResults.isEmpty()){
			return map;
		}
		
		String[] keys = keyStr.split(",");
		Object[] objs = sqlResults.get(0);
		
		int count = Math.min(objs.length, keys.length);
		for(int i=0; i<count; i++){
			//将java.math.BigInteger等复杂类型转换简单类型
			Object value = objs[i];
			String thisKey = QYUtils.trim(keys[i]);
			if(value != null){
				String className = value.getClass().getSimpleName();
				if("BigInteger".equals(className)){
					map.put(thisKey, toLong(value));	
				} else if("BigDecimal".equals(className)){
					map.put(thisKey, toDouble(value));	
				} else {
					map.put(thisKey, value);						
				}
			} else {
				map.put(thisKey, null);
			}
		}
		return map;
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
	 * 将name中间部分用星号代替
	 * @param str
	 * @param stars
	 * @return
	 */
	public static String starName(Object nameObj, String stars){
		if(nameObj == null){
			return null;
		}
		String name = nameObj.toString();
		if(StringUtils.isBlank(name)){
			return name;
		}
		int length = name.length();
		if(length == 1){
			return name;
		}
		if(length == 2){
			return QYUtils.subReplace(name, 1, 1, stars);
		}
		return maskStr(name, stars);
	}
	/**
	 * 将name中间部分用星号代替
	 * @param str
	 * @return
	 */
	public static String starName(Object nameObj){
		if(nameObj == null){
			return null;
		}
		String name = nameObj.toString();
		if(StringUtils.isBlank(name)){
			return name;
		}
		int length = name.length();
		if(length == 1){
			return name;
		}
		if(length == 2){
			return QYUtils.subReplace(name, 1, 1, "***");
		}
		if(length >= 11){
			return maskStr(name, "****");
		}
		return maskStr(name, "***");
	}
	/**
	 * 将str中间的三分之一用****代替
	 * @param str
	 * @return
	 */
	public static String starStr(String str){
		return maskStr(str, "****");
	}
	/**
	 * 将str中间的三分之一用mask代替
	 * @param str
	 * @param mask
	 * @return
	 */
	public static String maskStr(String str, String mask){
		if(StringUtils.isBlank(str)){
			return str;
		}
		int length = str.length();
		if(length == 1){
			return str;
		}
		if(length == 2){
			return QYUtils.subReplace(str, 0, 0, mask);
		}
		if(StringUtils.isBlank(mask)){
			mask = "***";
		}
		if(length == 3 || length == 4){
			return QYUtils.subReplace(str, 1, 1, mask);
		}
		//分成3段，替换掉第二段
		int segment = length / 3;
		return QYUtils.subReplace(str, segment, 2 * segment, mask);
	}
	/**
	 * 截取前几位字符串
	 * @param content 截取的内容
	 * @param length  截取的长度
	 * @return
	 */
	public static String cutContent(String content,Integer length){
		String cutContent = "";
		if(StringUtils.isNotBlank(content)){
			if(content.length()>length){
				cutContent = content.substring(0,length)+"...";
			}else{
				cutContent = content;
			}
		}
		return cutContent;		
	}
	/**
	 * 从详情中截取分享内容
	 * @param content
	 * @param length
	 * @return
	 */
	public static String getShareDesc(String content, int length){
		if(StringUtils.isBlank(content)){
			return "";
		}
		return cutContent(content.replaceAll("<.+?>|\n|\r|&.+?;", ""), length);
	}

	
	/**
	 *  把list转换为一个用逗号分隔的字符串
	 * @param list
	 * @return
	 */
	public static String listToString(List list) {  
		StringBuilder sb = new StringBuilder();  
		if (list != null && list.size() > 0) {  
			for (int i = 0; i < list.size(); i++) {  
				if (i < list.size() - 1) {  
					sb.append(list.get(i) + ",");  
				} else {  
					sb.append(list.get(i));  
				}  
			}  
		}  
		return sb.toString();  
	}  
	
	
	/**
	 * 
	 * @param list 获取内容的list
	 * @param length  去list的长度
	 * @param key list中对象在map的key
	 * @param prefix  分隔符
	 * @return
	 */
	public static String listToString(List<Map<String,Object>> list,int length,String key,String prefix){
		String result = "";
		if(list == null || list.size()==0){
			return null;
		}
		int listsize = list.size();
		if(listsize<=length){
			for(int i=0;i<listsize;i++){
				if(i==listsize-1){
					result += list.get(i).get(key);
				}else{
					result += list.get(i).get(key)+prefix;
				}
			}
		}else{
			for(int i=0;i<length;i++){
				if(i==length-1){
					result += list.get(i).get(key)+"...";
				}else{
					result += list.get(i).get(key)+prefix;
				}
			}
		}
		return result;
	}

	/**
	 * 解析html中的图片地址
	 * @return 图片url列表
	 */
	public static  List<String> parseImgsInHtml(String html){
		List<String> urls = new LinkedList<String>();
		if(StringUtils.isNotBlank(html)){
			try{
				Document doc = Jsoup.parse(html);
				Elements imgs = doc.select("img");
				for(Element item : imgs){
					urls.add(item.attr("src"));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return urls;
	}
    /**
     *
     * @param map
     * @return key1=1&key2=true
     */
    public static String map2QueryString(Map<String, ?> map){
        return map2QueryString(map, true);
    }
	/**
	 * 
	 * @param map
	 * @return key1=1&key2=true
	 */
	public static String map2QueryString(Map<String, ?> map, boolean encode){
		if(map==null ||map.isEmpty()){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, ?> entry : map.entrySet()){
		    if(encode) {
                sb.append("&" + entry.getKey() + "=" + encodeHTML(QYUtils.toString(entry.getValue(), "")));
            } else {
                sb.append("&" + entry.getKey() + "=" + QYUtils.toString(entry.getValue(), ""));
            }
		}
		return sb.substring(1);
	}
	/**
	 *  url后面增加GET请求参数对
	 * @param url
	 * @param kvPairs
	 * @return url?kvPairs 或 url&kvPairs
	 */
	public static String addQueryParam(String url, String kvPairs){
		if(StringUtils.isBlank(url) || StringUtils.isBlank(kvPairs) ){
			return url;
		}
		if(url.contains("?")){
			return url + "&" + kvPairs;
		} else {
			return url + "?" + kvPairs;
		}
	}
	/**
	 *  将url中的{key}占位符替换为context中对应的value
	 * @param url
	 * @param context
	 * @return
	 */
	public static String parseContext(String url, Map<String,Object> context){
		if(StringUtils.isBlank(url) || QYUtils.isEmpty(context) ){
			return url;
		}
		for(String key : context.keySet()){
			url = url.replaceAll("{"+key+"}", encodeHTML(QYUtils.toString(context.get(key))));
		}
		return url;
	}

	/**
	 *  url后面增加一个GET请求参数
	 * @param url
	 * @param key
	 * @param value
	 * @return
	 */
	public static String addQueryParam(String url, String key, Object value){
		if(StringUtils.isBlank(url)){
			return url;
		}
		String valueStr = encodeHTML(toString(value));
		if(url.contains("?")){
			return url + "&" + key + "=" + valueStr;
		} else {
			return url + "?" + key + "=" + valueStr;
		}
	}
    /**
     * 去掉url参数中的重复部分,以后面的值为准
     * @param url 带querystring的url或者直接是url
     * @return
     */
    public static String distinctQueryString(String url){
        if(StringUtils.isBlank(url)){
            return url;
        }
        String[] parts = url.split("\\?");
        String urlStr = "";
        String queryStr = "";
        if(parts.length != 2){
            if(url.toLowerCase().startsWith("http")) {
                return url;
            } else {
                urlStr = "";
                queryStr = parts[0];
            }
        } else {
            urlStr = parts[0];
            queryStr = parts[1];
        }
        String[] queryPairs = queryStr.split("&");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for(String pairs : queryPairs){
            String[] pairsArr = pairs.split("=");
            String key = pairsArr[0];
            String value = "";
            if(pairsArr.length == 2){
                value = pairsArr[1];
            }
            map.put(key, value);
        }

        String newQueryString = "";
        for(Map.Entry<String, String> entry : map.entrySet()){
            newQueryString = newQueryString + "&" + entry.getKey() + "=" + entry.getValue();
        }
        if("".equals(urlStr)) {
            return newQueryString.substring(1);
        } else {
            return urlStr + "?" + newQueryString.substring(1);
        }
    }


    /**
	 * 目标版本号appversion是否高于或等于起始版本号startVersion
	 * @param startVersion 起始版本号，如：ios2.3.0或2.3.0
	 * @param appversion 目标版本号，如：ios2.4.0或2.4.0
	 * @return 
	 */
	public static boolean versionGe(String startVersion, String appversion){
		if(StringUtils.isBlank(startVersion) || StringUtils.isBlank(appversion)){
			return false;
		}
		
		String version = appversion.toLowerCase().replaceAll("ios", "").replaceAll("android", "").replaceAll("andriod", "").replaceAll(" ", "");
		startVersion = startVersion.toLowerCase().replaceAll("ios", "").replaceAll("android", "").replaceAll("andriod", "").replaceAll(" ", "");
		
		if(version.equalsIgnoreCase(startVersion)){
			return true;
		}
		
		String[] versionParts = version.split("\\.");
		String[] startVersionParts = startVersion.split("\\.");
		for(int i=0; i<versionParts.length; i++){
			int appverPart = QYUtils.toInteger(versionParts[i], 0);
			int startVersionPart = 0;
			if(startVersionParts.length > i){
				startVersionPart = QYUtils.toInteger(startVersionParts[i], 0);
			}		
			if(appverPart > startVersionPart){
				return true;
			} else if(appverPart < startVersionPart){
				return false;
			}
		}	
		return false;
	}
	public static JSONObject toJson(Object obj){
    	if(obj == null){
    		return new JSONObject();
    	}
    	if(obj instanceof JSONObject){
    	    return (JSONObject)obj;
        }

    	String str = obj.toString();
    	if(isBlank(str)){
    		return new JSONObject();    		
    	}
    	try{
    		JSONObject json = JSON.parseObject(str.trim());
			if(json == null){
				return new JSONObject();
			} else {
				return json;
			}
    	}catch(Exception e){
    		return new JSONObject();
    	}    	
    }
    public static JSONArray toJsonArray(Object obj){
    	if(obj == null){
    		return new JSONArray();
    	}
        if(obj instanceof JSONArray){
            return (JSONArray)obj;
        }

    	try{
    		JSONArray json = JSON.parseArray(obj.toString());
			if(json == null){
				return new JSONArray();
			} else {
				return json;
			}
    	}catch(Exception e){
    		return new JSONArray();
    	}    	
    }
	/**
	 * 四舍五入
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static double toRound(Object value,Double defaultValue) {
		if(value == null){
			return defaultValue;
		}
        BigDecimal bg = new BigDecimal(String.valueOf(value));
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
    }
	public static boolean isNum(String str){
		 if(StringUtils.isBlank(str)){
			 return false;
		 }
		 if(str.matches("^[0-9]*$")){
			 return true;
		 }
		 return false;
	 }
	/***
	 * 去掉list中重复的元素
	 * @param list
	 * @return
	 */
	public static <T> List<T> removeDuplicate(List<T> list){
		HashSet<T> h  =   new  HashSet<T>(list); 
	    list.clear(); 
	    list.addAll(h); 
		return list;
	}
	/**
	 * 截取前几位字符串
	 * @param content 截取的内容
	 * @param cutStr  被截取的字符串
	 * @return
	 */
	public static String cutContent(String content,String cutStr){
		String cutContent = "";
		if(StringUtils.isNotBlank(content) && StringUtils.isNotBlank(cutStr)){
			int loc = content.indexOf(cutStr);
			if(loc>-1){
				cutContent = content.substring(0,loc);
			}else{
				cutContent = content;
			}
		}
		return cutContent;		
	}
	/**
	 * 翻译订单类型名称
	 * @param srcfrom
	 * @param orderFlag
	 * @return
	 */
	public static String getOrderManage(String srcfrom,String orderFlag){
		String orderType = "";
        	if(String.valueOf(srcfrom).equals("tuan")){
        		orderType = "团购订单";
        	}else if((StringUtils.isBlank(String.valueOf(srcfrom)) || String.valueOf(srcfrom)!="tuan") && (String.valueOf(orderFlag).equals("0"))){
        		orderType = "普通订单";
        	}else if((StringUtils.isBlank(String.valueOf(srcfrom)) || String.valueOf(srcfrom)!="tuan") && (String.valueOf(orderFlag).equals("1"))){
        		orderType = "一套订单";
        	}
		return orderType;		
	}
	/**
	 * 翻译一套配送记录的状态名称
	 * @param status 执行状态,1:未准备, 2:准备期, 3:待配送, 4:配送中, 5:已暂停, 6:已终止, 7:已完成
	 * @param prepareDate 准备期时间
	 * @param performDate 计划配送时间
	 * @return
	 */
	public static String getServicePerformStatusName(Object status, Object prepareDate, Object performDate){
		String stautsName = "--";
		if(status == null){
			return stautsName;
		}
		int intStatus = QYUtils.toInteger(status, 0);
		if(intStatus >= 4){
			return QYUtils.transDic(status, "4,5,6,7", "配送中,已暂停,已终止,已完成");
		}		
	    int today = QYUtils.toInteger(DateUtils.DateToString(new Date(), "yyyyMMdd"));
	    int intPrepareDate = QYUtils.toInteger(prepareDate);
	    int intPerformDate = QYUtils.toInteger(performDate);
	    if(intStatus==3 || intPerformDate <= today){
	    	return "待配送";
	    }
	    if(intStatus==2 || (intPerformDate>today && intPrepareDate<=today)){
	    	return "准备期";
	    }
	    if(intStatus==1 || intPrepareDate>today){
	    	return "未准备";
	    }
	    
		return stautsName;
	}
	
	/**
	 * 将返回的数组结果转换为
	 * @param list
	 * @param metaArr
	 * @return
	 */
	public static List<Map<String,Object>> arrayListToMapList(List list,String[] metaArr){
		List<Map<String,Object>> rtnList=new ArrayList<>();
		Map<String,Object> tmpMap;
		for(Object obj:list){
			tmpMap=new HashMap<>();
			int i=0;
			for(String meta:metaArr){
				tmpMap.put(meta, ((Object[])obj)[i]);
				i++;
			}
			rtnList.add(tmpMap);
		}
		return rtnList;
	}
	
	
	public static void main(String[] args) throws Exception {

		String s = "30_90/f";
		String[] a = s.split("/|\\\\");

		System.out.println(a[0]+"");

//		String appid = "SOON-LIANZ-0001";
//		String appkey = "bf0cf811e8ec9eb9a65dfa914b39c087e8ec9eb9a65dfa91";
//
//
//		String src = "{\"accessToken\":\"942820ab62744617a8df0d8e38a82593\"}";
//
//		//String mi = QYUtils.encryptMode(src, appkey);
//		String mi = DESedeCoder.encode(src, appkey);
//		System.out.println(mi);
//
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("param", mi);
//		map.put("appid", appid);
//
//		String sign = QYUtils.signRequest2(appid, map, appkey);
//		System.out.println(sign);
		
		//param=28c350cdda2721e687de7fb4857bc4cfb48bbe5f094d7cb1037ad607b130dd0c2657ec71e784743cea777e05d6235a1f1cb1f71640c3ec38cc7aaa4b1b3e9e67812571c8852eb0f8255d0f454eba74f7249abae071be47943513fb0f2f9e1b6ee44aea9532a7447dbe2accee385bdc5d
		
		
//		map.put("param", "28c350cdda2721e687de7fb4857bc4cfb48bbe5f094d7cb1037ad607b130dd0c2657ec71e784743cea777e05d6235a1f1cb1f71640c3ec38cc7aaa4b1b3e9e67812571c8852eb0f8255d0f454eba74f7249abae071be47943513fb0f2f9e1b6ee44aea9532a7447dbe2accee385bdc5d");
//		map.put("appid", appid);
//		
//		String sourcestr = "{\"param\":\"52c215ec3fabf9833e03baeac6d90927546b69a91e5eb3295ad0eef812f69e0c806d2ea273388df33cc58ed91adc6e93\",\"appid\":\"SOON-MENGMA-0001\"}";
//		
//		String sign = QYUtils.signRequest(appid, map, appkey);
//		System.out.println(sign);
		
//		System.out.println(QYUtils.checkSign(appid, appkey, sourcestr, sign));
		
//		String mi = QYUtils.encryptMode(sourcestr, appkey);
		
//		String mi = "28c350cdda2721e613afb27529d1b043cd3cc3e13421fddc2fa2a16266b7f9f63e507d5cb82e160acae62899b6ed4584674c27c5a13e66efa76e122a69e6f36a3a3f287c6e3ab5eac6ab58aa79f14c3cb6e2ef483560c6cc0f575986a74a9fec0309269abdfaeed0be091bee3287e24a09a5aef23f76b0aaf6f69c0a331b5c919f5f478a8d233ab629c8a6baae2ad364fe02025e2e16bc72d752fae1df6dbba3a2056b30b1ed5db3e31a8ae8a6abd96c00c0f81a38b560ae923dc64b345bf2e84e9f886a365517392f028e2939f4d531ed0154a875f4290c04ca6b71788f818920a70d5e82fac22d5d693e6d7fb77bf4a75273e814cda9082e5745dccf20dc5cdef065a95082dab3a1b3c0a16372db9436b000684ac26792255d0f454eba74f7249abae071be47943513fb0f2f9e1b6e652a588bc9b0b85d572848a6fb505c9f";
		
//		String ming = QYUtils.decryptMode(mi, appkey);
		
//		System.out.println(ming);
		
//		Map<String,Object> src = new HashMap<String,Object>();
//		src.put("orderId", 123);
//		src.put("appid", "SOON-MENGMA-0001");
//		String appid = "SOON-MENGMA-0001";
//		String appkey = "dbc2bd42922d023d3092be2cf2da903b922d023d3092be2c";
//		try {
//			String sign = QYUtils.signRequest(appid, src, appkey);
//			System.out.println(QYUtils.checkSign(appid, appkey, JSON.toJSONString(src), sign));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		
		//System.out.println((int) 2839.39/1000);
		
//		System.out.println(QYUtils.randomN(1));
//		List<Map<String ,Object>> numList = new ArrayList<Map<String ,Object>>();
//		Map<String, Object> item1 = new HashMap<String, Object>();
//		item1.put("partakeNum", 1);
//		Map<String, Object> item2 = new HashMap<String, Object>();
//		item2.put("partakeNum", 1);
//		Map<String, Object> item3 = new HashMap<String, Object>();
//		item3.put("partakeNum", 1);
//		Map<String, Object> item4 = new HashMap<String, Object>();
//		item4.put("partakeNum", 1);
//		Map<String, Object> item5 = new HashMap<String, Object>();
//		item5.put("partakeNum", 1);
//		Map<String, Object> item6 = new HashMap<String, Object>();
//		item6.put("partakeNum", 1);
//		
//		numList.add(item1);
//		numList.add(item2);
//		numList.add(item3);
//		numList.add(item4);
////		numList.add(item5);
////		numList.add(item6);
//		System.out.println(QYUtils.cutContent("90.001", ".00"));
		

	}

	/**
	 * 格式化支付成功观察者回调失败信息
	 * @param action
	 * @param param
	 * @return action失败【WatcherError-Param:param】
	 */
	public static String watcherError(String action, Map<String, Object> param){
		return new StringBuilder()
			.append(action)
			.append("失败【WatcherError-Param:").append(param).append("】")
			.toString();
	}
	
	/**
	 * 去掉HTML标签
	 * @param content
	 * @param trybetter 是否尝试将br p &nbsp;替换为空白
	 * @return
	 */
	public static String trimHtmlTag(String content, boolean trybetter){
		if(StringUtils.isBlank(content)){
			return content;
		}
		String str = content;
		if(trybetter){
			str = Pattern.compile("<br\\s*/?>", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll(" ");
			str = Pattern.compile("</?\\s*p\\s*>", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll(" ");
			str = Pattern.compile("&nbsp;", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll(" ");
			str = Pattern.compile("&lt;", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll("<");
			str = Pattern.compile("&gt;", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll(">");
			str = Pattern.compile("&amp;", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll("&");
			str = Pattern.compile("&quot;", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll("\"");
			str = Pattern.compile("&apos;", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll("'");
			str = Pattern.compile("&yen;", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll("¥");		
		}
		str = str.replaceAll("<.+?>|\n|\r|&.+?;", "");
		//str = str.replaceAll("<.+?>|&.+?;", "");
		str = QYUtils.trim(str);
		return str;
	}
	

	/**
	 * 格式化商品名称，去除换行等特殊字符
	 * @param name
	 * @return
	 */
	public static String formatName(String name){
		if(StringUtils.isBlank(name)){
			return "";
		}
		
		return clearContent(name.trim())
            .replaceAll("[\n\r	]", "")
            .replaceAll("\\s+", " ")
            .replaceAll("\\\\", "＼")
            .replaceAll("'", "＇")
            .replace('/', '／')
            .replace('~', '～')
            .replace('`', '｀')
            .replace('!', '！')
            .replace('@', '＠')
            .replace('#', '＃')
            .replace('$', '＄')
            .replace('%', '％')
            .replace('^', '＾')
            .replace('&', '＆')
            .replace('*', '＊')
            .replace('(', '（')
            .replace(')', '）')
            .replace('+', '＋')
            .replace('}', '｝')
            .replace('{', '｛')
            .replace('[', '［')
            .replace(']', '］')
            .replace('|', '｜')
            .replace(':', '：')
            .replace(';', '；')
            .replace('"', '＂')
            .replace('<', '＜')
            .replace('>', '＞')
            .replace(',', '，')
            .replace('?', '？')
			;
	}
	public static String clearContent(String inputString) {
		String htmlStr = inputString;
		String textStr = "";
		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>";
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>";
			String regEx_html = "<[^>]+>";
			String regEx_html1 = "<[^>]+";
			Pattern p_script = Pattern.compile(regEx_script, 2);
			Matcher m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");

			Pattern p_style = Pattern.compile(regEx_style, 2);
			Matcher m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");

			Pattern p_html = Pattern.compile(regEx_html, 2);
			Matcher m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");

			Pattern p_html1 = Pattern.compile(regEx_html1, 2);
			Matcher m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll("");

			htmlStr = QYUtils.trans4bytesChar(htmlStr, "");
			
			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;
	}
	

	/**
	 * (使用DESedeCoder类的加密方法处理)校验请求报文的签名，对报文的body部分进行BASE64>MD5>3DES处理得到签名值，与报文head中sign值进行比对
	 * 
	 * @param appid
	 * @param src
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean checkSign2(String appid,String appkey, String src, String sign)
			throws Exception {
		if (appid == null || "".equals(appid) || appkey == null || "".equals(appkey) || sign == null
				|| "".equals(sign)) {
			return false;
		}
		if(src==null){
			return true;
		}
		
//		String srcText = cutContent(src);
		// String destText = MD5Util.md5Digest(appid+srcText);
		// 对报文进行BASE64编码，避免中文处理问题
//		String base64Text = new String(Base64.encodeBase64((appid + src).getBytes("utf-8"), false));
		String base64Text = new String(Base64.encodeBase64(src.getBytes("utf-8"), false));
		// MD5摘要，生成固定长度字符串用于加密
		String destText = MD5Util.md5(base64Text);
		/*AlgorithmData data = new AlgorithmData();
		data.setDataMing(destText);
		data.setKey(appkey);
		// 3DES加密
		Algorithm3DES.encryptMode(data);
		log.error("sign:"+sign+"--dataming:"+src+"--datami:"+data.getDataMi());*/
		
		String datami = DESedeCoder.encode(destText, appkey);
		return sign.equals(datami);
	}

	
	/**
	 * （DESedeCoder）对请求报文进行签名，对报文的body部分进行BASE64>MD5>3DES处理得到签名值
	 * @param appid
	 * @param src
	 * @param appkey
	 * @return
	 * @throws Exception
	 */
	public static String signRequest2(String appid, Object src, String appkey)
			throws Exception {
		String srcText = JSON.toJSONString(src);
//				gson.toJson(src);
		// 对报文进行BASE64编码，避免中文处理问题
//		String base64Text = new String(Base64.encodeBase64((appid + srcText).getBytes("utf-8"), false));
		String base64Text = new String(Base64.encodeBase64(srcText.getBytes("utf-8"), false));
		// MD5摘要，生成固定长度字符串用于加密
		String destText = MD5Util.md5(base64Text);
/*		AlgorithmData data = new AlgorithmData();
		data.setDataMing(destText);
		data.setKey(appkey);
		// 3DES加密
		Algorithm3DES.encryptMode(data);
		return data.getDataMi();*/
		return DESedeCoder.encode(destText, appkey);
	}


	/**
	 * 通用的key生成器
	 * @return prefix_items[0]_items[1]...
	 */
	public static String getKey(Object prefix, Object... items){
		return (prefix+"_"+StringUtils.join(items, '_'))
				//.replaceAll("[\\s~!@#$%^&()+\\-{}|<>?,./:;'\"()/\\[\\]\\\\￥《》？、【】！；：｛｝（） ]", "_")
				.replaceAll("\\W", "_")
				.replaceAll("_+", "_");
	}
	
	public static String signMobile(String mobile){
		String signmobile = "0";
		if(QYUtils.isMobilePhone(mobile)){
			if(QYUtils.isNotBlank(mobile)){
				if(mobile.length()>3){
					signmobile = mobile.substring(0, 3);
					String lastmobile = mobile.substring(3, mobile.length());
					char[] mobilec = lastmobile.toCharArray();
					String signlast = "";
					for(char c : mobilec){
						signlast =signlast+QYUtils.toString(9-QYUtils.toInteger(c));
					}
					signmobile = signmobile+signlast;
				}
			}
		}
		return signmobile;
	}
	
	/**
	 * 域名
	 * @param domainName
	 * @return
	 */
	public static Boolean isIP(String domainName){  
		if(domainName.length() < 7 || domainName.length() > 15 || "".equals(domainName)){  
             return false;  
         }  
         /** 
          * 判断IP格式和范围 
          */  
         String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";  
         Pattern pat = Pattern.compile(rexp);   
         Matcher mat = pat.matcher(domainName);    
         boolean ipAddress = mat.find();   
         return ipAddress;  
    }
    /**
	 * hystrix
	 * */
    public static String successHystrixFallback(Object data){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", "100");
		map.put("msg", "服务降级！");
		map.put("data",data);
		map.put("now", System.currentTimeMillis());
		map.put("success", true);
		return JSON.toJSONString(map);
	}

	/**
	 * 判断两个list是否有共同元素
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static boolean hasSameElement(String list1, String list2){
		if(StringUtils.isBlank(list1) || StringUtils.isBlank(list2)){
			return false;
		}
		String[] arr1 = list1.split(",");
		String list2Plus = ","+list2+",";
		for(String thisStr : arr1){
			if(StringUtils.isBlank(thisStr)){
				continue;
			}

			//if(list2Plus.indexOf(","+thisStr.trim()+",") != -1){
			if(list2Plus.matches("^.*,\\s*"+thisStr.trim()+"\\s*,.*$")){
				return true;
			}
		}
		return false;
	}

    /**
     * 判断list1是否包含list2中的所有元素
     * @param list1
     * @param list2
     * @return true:list1包含list2中的所有元素
     */
	public static boolean listContains(String list1, String list2){
        String listA = formatList(list1);
        String listB = formatList(list2);
        if(isBlank(listA) || isBlank(listB)){
            return false;
        }

        String list1Formated = ","+listA+",";
        String[] list2Parts = listB.split(",");
        for(int i=0; i<list2Parts.length; i++){
            String element = list2Parts[i];
            if(isBlank(element)){
                continue;
            }
            if(list1Formated.indexOf(","+element+",") == -1){
                return false;
            }
        }
        return true;
    }
    /**
     * 判断list1是否包含list2中的任何元素
     * @param list1
     * @param list2
     * @return true:list1包含list2中的任何元素
     */
    public static boolean listHasAny(String list1, String list2){
        String listA = formatList(list1);
        String listB = formatList(list2);
        if(isBlank(listA) || isBlank(listB)){
            return false;
        }

        String list1Formated = ","+listA+",";
        String[] list2Parts = listB.split(",");
        for(int i=0; i<list2Parts.length; i++){
            String element = list2Parts[i];
            if(isBlank(element)){
                continue;
            }
            if(list1Formated.indexOf(","+element+",") > -1){
                return true;
            }
        }
        return false;
    }
    /**
     * 判断list是否包含element元素
     * @param list
     * @param element
     * @return true:list包含element元素
     */
    public static boolean listHas(String list, Object element){
        String listA = formatList(list);
        if(isBlank(listA) || element == null){
            return false;
        }

        String list1Formated = ","+listA+",";
        return list1Formated.contains(","+element+",");
    }

	
	/**
	 * bean->map
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String,Object> beanToMap(T t){
		Map<String,Object> map = null;
		try {
			map = JSON.parseObject(JSON.toJSONString(t),Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * collection->String
	 * @param c
	 * @param delimiter 间隔
	 * @return
	 */
	public static String collectionToString(Collection<?> c,String delimiter){
		String result = "";
		if (isEmpty(c)){
			return result;
		}
		if (QYUtils.isBlank(delimiter)) {
			delimiter = ",";
		}
		result = c.stream().map(v->String.valueOf(v)).collect(Collectors.joining(delimiter));
		return result;
	}

	/**
	 *
	 * @param code
	 * @param msg
	 * @param  data
	 * @return
	 */
	public static String errorRecRights(int code, String msg,Object data){
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isBlank(msg)){
			msg = "";
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("data",data);
		map.put("success", false);
		String json = JSON.toJSONString(map);
		return json;
	}

	/**
	 * 增加后缀
	 * @param str 目标字符串
	 * @param affix 后缀
	 * @return
	 */
	public static String addAffix(String str, String affix){
		if(StringUtils.isBlank(str)){
			return affix;
		}
		if(StringUtils.isBlank(affix)){
			return str;
		}
		return str+affix;
	}
	/**
	 * 查看字符串是否包含特定后缀
	 * @param str 目标字符串
	 * @param affix 后缀
	 * @return
	 */
	public static boolean checkAffix(String str, String affix){
		if(StringUtils.isBlank(str) || StringUtils.isBlank(affix)){
			return false;
		}
		return str.endsWith(affix);
	}

	/**
	 * 去除后缀
	 * @param str 目标字符串
	 * @param affix 后缀
	 * @return
	 */
	public static String removeAffix(String str, String affix){
		if(StringUtils.isBlank(str)){
			return "";
		}
		if(StringUtils.isBlank(affix)){
			return str;
		}
		return StringUtils.substringBeforeLast(str, affix);
	}
	

	/**
	 * 笛卡尔组合
	 * @param orig     原始集合   
	 * @param result   转化后集合
	 * @param layer    中间参数
	 * @param curList  中间参数
	 */
	public static <T> void cartesianProduct(List<List<T>> orig, List<List<T>> result, int layer, List<T> curList) {
        if (layer < orig.size() - 1) {
            if (orig.get(layer).size() == 0) {
            	cartesianProduct(orig, result, layer + 1, curList);
            } else {
                for (int i = 0; i < orig.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(curList);
                    list.add(orig.get(layer).get(i));
                    cartesianProduct(orig, result, layer + 1, list);
                }
            }
        } else if (layer == orig.size() - 1) {
            if (orig.get(layer).size() == 0) {
                result.add(curList);
            } else {
                for (int i = 0; i < orig.get(layer).size(); i++) {
                    List<T> list = new ArrayList<T>(curList);
                    list.add(orig.get(layer).get(i));
                    result.add(list);
                }
            }
        }
	}


	/**
	 * 包含判断
	 * @param str1 "1,2,3"
	 * @param str2  1
	 * @param regex ,
	 * @return
	 */
	public static boolean contains(String str1,String str2,String regex){
		List<String> strs = Lists.newArrayList();
		if (isBlank(str1)) {
			return false;
		}
		if(isBlank(regex)){
			regex = ",";
		}
		strs = Arrays.asList(str1.split(regex)).stream()
				.sorted()
				.collect(Collectors.toList());
		return strs.contains(str2);
	}

	public static boolean contains(String str1,String str2){
		return contains(str1,str2,null);
	}

	/**
	 * 生成单号
	 * @param prex - 如LM
	 * @return repairPrex+yyyyMMddhhmm+Sequence+_userId
	 */
	public static String generateNo(String prex, Integer randomNum,Integer code){
		SimpleDateFormat dataformat = new SimpleDateFormat("yyyyMMddhhmmss");
		Date now = new Date();
		String nowStr = dataformat.format(now);
		StringBuffer repairNo = new StringBuffer();
		repairNo.append(prex);//订单类型的前缀
		repairNo.append(nowStr);//日期时分
		//获取流水号
		//repairNo.append(getSequence());
		repairNo.append(QYUtils.addZeroForNum(QYUtils.toString(code+1), 8));
		repairNo.append(randomNum);
		return repairNo.toString();
	}


	/**
	 * 获取第三方商品每日同步的操作标识，用于识别当日的同步。将复用goods.ztc_admin_id,因此该值必须是有效的用户id。
	 * 只选用id 1,2,3,4
	 */
	public static Long getGoodsSyncDayKey(){
		int day = DateUtils.getDay(new Date());
		int key = (day % 4) + 1;
		return toLong(key);
	}

	/**
	 * 校验并格式化ES的经纬度坐标<p>
	 *     中国经度范围：73°33′E至135°05′E；纬度范围：3°51′N至53°33′N。当中的字母E,表示东经；字母N，表示北纬。东经正数，西经为负数；北纬为正数，南纬为负数。
	 * @param location 纬度(lat),经度(lng)
	 * @return 无效的坐标则返回null
	 */
	public static String formatEsLocation(String location){
		if(StringUtils.isBlank(location) || (!location.contains(",") && !location.contains("，"))){
			log.info("ES地理位置坐标有误："+location);
			return null;
		}
		String[] formatted = location.replaceAll("，", ",").replaceAll("\\s", "").split(",");
		if(formatted.length != 2){
			log.info("ES地理位置坐标有误："+location);
			return null;
		}
		Double lat = toDouble(formatted[0]);
		Double lng = toDouble(formatted[1]);
		if(lat == null || lng == null){
			log.info("ES地理位置坐标有误："+location);
			return null;
		}
		//国内经度>纬度
		if(lat.compareTo(lng) >= 0){
			Double temp = lat;
			lat = lng;
			lng = temp;
		}
		return lat+","+lng;

	}

	/**
	 * 格式化日志信息
	 * @param connector
	 * @param className
	 * @param actionName
	 * @param objs
	 * @return
	 */
	public static String logmsg_format(String className,String actionName, Object...objs){
		String connector = "---";
		StringBuilder sb = new StringBuilder();
		sb.append(className).append(connector).append(actionName).append(connector);
		for(Object obj : objs){
			if(obj != null){
				sb.append(connector).append(String.valueOf(obj).trim());
			}
		}
		String result = sb.toString().replaceAll("^"+connector+"+|"+connector+"+$", "");
		return result;
	}
	
	/**
	 * 获取最小时间
	 * @param ds
	 * @return
	 */
	public static Date min(Date... ds){ 
		if(ds == null || ds.length == 0){
			return null;
		}
		Date min = null;
		List<Date> dl = Arrays.asList(ds);
		dl.sort((v1,v2)->v1.compareTo(v2));
		min = dl.get(0);
		return min;	    	
	}
	
	/**
	 * 随机排序集合  相同效果方法  Collections.shuffle(List<?> list);
	 * @param <T>
	 * @param in
	 * @return
	 */
	public static <T> void applyRandom(List<T> in){ 
		if(isEmpty(in)) {
			return;
		}
		ThreadLocalRandom random = ThreadLocalRandom.current();
		in.sort(Comparator.comparing(v->random.nextInt(9)));
	}

	/**
	 * 时间转换7月31日
	 * @param dateTime
	 * @return
	 */
	public static String showDateStr(Date dateTime) {
		if(dateTime==null){
			return "";
		}
		Date now = new Date();
		long nows = System.currentTimeMillis();
		long delay = (nows - dateTime.getTime()) / 1000 / 60;// 单位：分钟
		if(DateUtils.sameDay(now, dateTime)){//同一天
			String showTime = "刚刚";
			if(delay > 0){//过去的时间
				if (delay < 5) {
					return showTime;
				}
				if (delay < 60) {
					showTime = delay + "分钟前";
					return showTime;
				}
				delay /= 60;
				if(delay < 24) {
					showTime = delay + "小时前";
					return showTime;
				}
			}
		}
		if(DateUtils.sameYear(now, dateTime)){//同一年
			delay=delay /60/24;
			if(delay < 2) {
				return "昨天";
			}
			return DateUtils.DateToString(dateTime, "M月d日");
		}
		return DateUtils.DateToString(dateTime, "yyyy年M月d日");
	}
	
	
	/**
	 * 集合合并
	 * @param oldList
	 * @param newList
	 * @param oldKey
	 * @param newKey
	 * @return
	 */
	public static List<Map<String,Object>> mergeList(List<Map<String,Object>> oldList,List<Map<String,Object>> newList,String oldKey,String newKey){
		List<Map<String,Object>> result = Lists.newArrayList();
		if(isEmpty(oldList)||isEmpty(newList)) {
			return result;
		}
		Map<Long, Map<String, Object>> nm = newList.stream()
				.collect(Collectors.toMap(v->QYUtils.toLong(v.get(isBlank(newKey)?"id":newKey)), Function.identity()));
		result = oldList.stream().map(v->{
			Long id = toLong(v.get(isBlank(oldKey)?"id":oldKey));
			Map<String, Object> m = nm.get(id);
			if(isNotEmpty(m)) {
				v.putAll(m);
			}else {
				v = null;
			}
			return v;
		}).filter(v->isNotEmpty(v)).collect(Collectors.toList());
		return result;
	}
	
	/**
	 * 获取map中随机值
	 * @param <K>
	 * @param <V>
	 * @param in
	 * @param limit
	 * @return
	 */
	public static <K, V> Map<K, V> getRandomLimit(Map<K, V> in, int limit) {
		if (isEmpty(in)) {
			return in;
		}
		limit = (limit<1||limit>in.size())?in.size():limit;
		List<K> el = in.keySet().stream().collect(Collectors.toList());
		List<K> ell = getRandomLimit(el, limit);
		Map<K, V> ol = in.entrySet().stream().filter(v -> ell.contains(v.getKey()))
				.collect(Collectors.toMap(Map.Entry<K, V>::getKey, Map.Entry<K, V>::getValue));
		return ol;
	}
	
	/**
	 * 获取list中随机值
	 * @param <E>
	 * @param in
	 * @param limit
	 * @return
	 */
	public static <E> List<E> getRandomLimit(List<E> in, int limit) {
		if (isEmpty(in)) {
			return in;
		}
		limit = (limit<1||limit>in.size())?in.size():limit;
		List<E> ol = Lists.newArrayList();
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Collections.shuffle(in,random);
		ol= in.stream().limit(limit).collect(Collectors.toList());
		return ol;
	}
	
    /**
     * map 转 string
     * get参数
     * @param map
     * @return
     */
    public static String getParams(Map<String,?> map){
        String rs ="";
        if (QYUtils.isEmpty(map)) {
            return rs;
        }
        for (Map.Entry<String,?> entry : map.entrySet()){
            if (QYUtils.isNotBlank(rs)){
                rs+="&";
            }
            rs+=entry.getKey()+"="+entry.getValue();
        }
        return rs;
    }

    /**
     * 根据配置的扣点值计算社员价格
     * @author Meron
     * @date 2020-01-07 11:19
     * @param supplyPrice
     * @param vipPriceRatio
     * @return java.math.BigDecimal
     */
    public static BigDecimal calcVipPriceByRatio(BigDecimal supplyPrice, BigDecimal vipPriceRatio){
        BigDecimal vipPrice = supplyPrice.divide(vipPriceRatio, 1, BigDecimal.ROUND_UP);//1位小数，只入不舍
        return vipPrice;
    }
	
    /**
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static long random(long start, long end) {
		if (start == end||(start == 0L&&end== 0L)) {
			return 0L;
		}
		long rtn = start + (long) (Math.random() * (end - start));
		if (rtn == start || rtn == end) {
			return random(start, end);
		}
		return rtn;
	}
	
	public static LocalTime randomTime(LocalTime startTime,LocalTime endTime){
		LocalTime nt = LocalTime.now();
		long start = startTime.toNanoOfDay();
		long end = endTime.toNanoOfDay();
		long n = random(start, end);
		nt = LocalTime.ofNanoOfDay(n);
		return nt;
	}
	
	/**
	 * 数字转时间
	 * @param num
	 * @return
	 */
	public static LocalTime numTurnTime(Long num) {
		Instant timestamp = Instant.ofEpochSecond(num);
        LocalTime localTime = LocalTime.from(timestamp.atZone(ZoneId.of("GMT+00:00")));
		return localTime;
	}

    /**
     * 异常堆栈信息转为字符串
     * @param exception
     * @return
     */
    public static String getExceptionStack(Throwable exception){
        String exceptionMsg = null;
        if(exception != null){
            StringWriter sw = null;
            PrintWriter pw = null;
            try {
                sw = new StringWriter();
                pw = new PrintWriter(sw);
                exception.printStackTrace(pw);
                exceptionMsg = sw.toString();
            }
            catch (Exception e){
            }
            finally {
                try {
                    if(sw != null){
                        sw.close();
                    }
                    if(pw != null){
                        pw.close();
                    }
                }catch (Exception e){}
            }
        }
        return exceptionMsg;
    }

    /**
     * 将list转换为map(item.key, item)
     * @author Meron
     * @date 2020-08-30 16:24
     * @param list
     * @param key key字段名称, key字段值必须是K类型的
     */
    public static <K> Map<K, Map<String,Object>> list2Map(List<Map<String,Object>> list, String key){
        if(list == null || list.size()==0){
            return new HashMap<>();
        }
        Map<K, Map<String,Object>> result = new HashMap<>();
        for(Map<String,Object> item : list){
            K value = (K)item.get(key);
            result.put(value, item);
        }
        return result;
    }

    public static String getPayChannelName(String channel){
        if(isBlank(channel)){
            return "其他";
        }
		//2022-10-13 xn 增加线下支付
        String channelName = transDic(channel,
                "alipay,upacp,wx,supervip,jycoin,qpay,wxpub,offline,orgShipfreeGoods,orgShipfreeOut,orgShipfreeIn," +
                        "vipchange,jycoinplus,wxh5,H5_zz,H5_tb,H5_tbj,wxmini_jy,wxmini_zh,wxmini_tuan,jycoin_offline," +
                        "offline_givepackage,receive_givepackage,activate_card_givepackage,sendtongbao_givepackage," +
                        "identitytag_givepackage,jpressGiftGoods,interactGameGiftGoods,pointsGiftGoods,largeRemittance," +
                        "wxmini_sf,shuaka_pos_zhizun,saoma_pos_zhizun,H5_ali,H5_wx,H5_zz_target,app_zz_target,freeRights_givepackage,offline_payment",
                "支付宝,银联,微信,余额支付,建业通宝,快捷支付,微信公众号支付,线下支付,社区购,社区购+,社区购-," +
                        "全粮票,通宝+,微信支付,H5余额支付,H5通宝支付,H5通宝+支付,微信建业+小程序,微信种花小程序,微信团购小程序,通宝线下支付," +
                        "套餐线下支付,套餐赠送,会员卡激活,套餐赠送通宝," +
                        "身份匹配套餐,jpress奖励商品,幸福制造机互动支付,成长体系赠送,大额汇款," +
                        "微信售房小程序,至尊pos刷卡,至尊pos扫码,H5支付宝,H5微信,H5至尊定向支付,至尊定向支付,免费权益赠送,线下支付");

        return channelName;
    }
}




