package com.gpy.tstc;

import com.alibaba.fastjson.JSON;
import com.gpy.utils.MD5Util;

import java.util.HashMap;
import java.util.Map;

/**
 * 天时同城工具类
 */
public class TstcUtil {

    /**
     *  天时同城签名
     * @param requestBody 请求体数据 json字符串
     * @param timestamp 时间戳（与请求时间戳一致，秒级时间戳）
     * @return
     */
    public static String getSign(String requestBody, long timestamp) {
        String key = "ffac907e8de7d4faef9a9e40161f6e00";
        return MD5Util.md5(key + requestBody + timestamp);
    }

    /**
     * 天时同城组装请求参数
     * @param requestBody 请求体数据 json字符串
     * @return
     */
    public static String assembleRequestParams(String requestBody) {
        Map<String,String> paramsMap = new HashMap<>();
        long timestamp = System.currentTimeMillis() / 1000;
        String pid = "2403141614400624403";
        paramsMap.put("g_pid",pid);
        paramsMap.put("g_time",String.valueOf(timestamp));
        paramsMap.put("g_sign",getSign(requestBody,timestamp));
        paramsMap.put("body",requestBody);
        return JSON.toJSONString(paramsMap);
    }
}
