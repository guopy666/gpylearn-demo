package com.gpy.tstc;

import com.alibaba.fastjson.JSONObject;
import com.gpy.test.HttpUtils;

public class TstcDemoTest {

    /**
     * https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077
     * 授权id：2403141614400624403
     * 秘钥：ffac907e8de7d4faef9a9e40161f6e00
     */
    public static void main(String[] args) {
        JSONObject jsonObject = new  JSONObject();

        String  result = "";
        /*jsonObject.put("g_api","apiGoodsLista");
        jsonObject.put("page",1);
        jsonObject.put("limit",10);
        String requestParams = TstcUtil.assembleRequestParams(jsonObject.toJSONString());
        System.out.println("requestParams = " + requestParams);
        //https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/44/apiXa.php?cid=2403141619360621017
        //https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077
        String result = HttpUtils.postJson("https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077", requestParams);
        System.out.println("result = " + result);*/

        jsonObject.clear();
        jsonObject.put("g_api","apiOrdersRefunda");
        jsonObject.put("other_orders_id","2021031619360621080");
        // jsonObject.put("other_orders_id","2021031619360621051333");
        jsonObject.put("refund_size",1);
        jsonObject.put("refund_code_list","141415");
        String refundRequestParams = TstcUtil.assembleRequestParams(jsonObject.toJSONString());
        System.out.println("refundRequestParams = " + refundRequestParams);
        result = HttpUtils.postJson("https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077", refundRequestParams);
        System.out.println("result = " + result);

        System.out.println("====================================");

        /*jsonObject.clear();
        jsonObject.put("g_api","apiOrdersAdda");
        jsonObject.put("other_orders_id","2021031619360621080");
        jsonObject.put("goods_id", 2404161558080621054L);
        jsonObject.put("size", 1);
        jsonObject.put("play_time", "2024-06-01");
        jsonObject.put("contact_name", "郭先生");
        jsonObject.put("contact_mobile", "15538708888");
        jsonObject.put("contact_card", "412702199308062348");

        String checkRequestParams = TstcUtil.assembleRequestParams(jsonObject.toJSONString());
        System.out.println("checkRequestParams = " + checkRequestParams);
        result = HttpUtils.postJson("https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077", checkRequestParams);
        System.out.println("result = " + result);*/

        /*jsonObject.clear();
        jsonObject.put("g_api","apiOrdersLista");
        jsonObject.put("orders_id",2405101415220621164L);
        String orderListRequestParams = TstcUtil.assembleRequestParams(jsonObject.toJSONString());
        System.out.println("orderListRequestParams = " + orderListRequestParams);
        result = HttpUtils.postJson("https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077", orderListRequestParams);
        System.out.println("result = " + result);*/

        /*jsonObject.clear();
        jsonObject.put("g_api","apiOrdersChecka");
        jsonObject.put("other_orders_id","2021031619360621051");
        jsonObject.put("goods_id", 2404161620090625449L);
        jsonObject.put("size", 1);
        jsonObject.put("play_time", "2024-05-25");
        jsonObject.put("contact_name", "郭先生");
        jsonObject.put("contact_mobile", "15538708888");

        String checkRequestParams = TstcUtil.assembleRequestParams(jsonObject.toJSONString());
        System.out.println("checkRequestParams = " + checkRequestParams);
        result = HttpUtils.postJson("https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077", checkRequestParams);
        System.out.println("result = " + result);*/

        /*jsonObject.clear();
        jsonObject.put("g_api","apiOrdersAdda");
        jsonObject.put("other_orders_id","2021031619360621062");
        jsonObject.put("goods_id", 2404161620090625449L);
        jsonObject.put("size", 1);
        jsonObject.put("play_time", "2024-05-26");
        jsonObject.put("contact_name", "郭先生");
        jsonObject.put("contact_mobile", "15538708888");

        String checkRequestParams = TstcUtil.assembleRequestParams(jsonObject.toJSONString());
        System.out.println("checkRequestParams = " + checkRequestParams);
        result = HttpUtils.postJson("https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077", checkRequestParams);
        System.out.println("result = " + result);*/



        /*jsonObject.clear();
        jsonObject.put("g_api","apiOrdersSenda");
        jsonObject.put("other_orders_id","2021031619360621052");
        jsonObject.put("orders_id", "2405211533280629038");


        String codeRequestParams = TstcUtil.assembleRequestParams(jsonObject.toJSONString());
        System.out.println("checkRequestParams = " + codeRequestParams);
        result = HttpUtils.postJson("https://jianyetravel.tnci.cc/Api/Duijie/apia/P/In/1/apiXa.php?cid=2405091503590621077", codeRequestParams);
        System.out.println("result = " + result);*/
    }

}
