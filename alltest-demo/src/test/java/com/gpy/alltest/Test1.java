package com.gpy.alltest;

import cn.hutool.core.util.PinyinUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.gpy.algorithm.BinarySearch;
import com.gpy.algorithm.sort.BubbleSort;
import com.gpy.algorithm.sort.InsertionSort;
import com.gpy.common.Person;
import com.gpy.datastructure.MyArrayQueue;
import com.gpy.datastructure.MyArrayStack;
import com.gpy.datastructure.MyCircularQueue;
import com.gpy.test.QYUtils;
import com.gpy.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName Test1
 * @Description
 * @Author guopy
 * @Date 2021/7/7 17:35
 */
@Slf4j
public class Test1 {

    @Test
    public void test1() {
        MyArrayStack myArrayStack = new MyArrayStack(10);
        Boolean aaa = myArrayStack.push("aaa");
        System.out.println(myArrayStack.getCount());
        System.out.println(myArrayStack.getSize());
        System.out.println(myArrayStack.pop());
        System.out.println(myArrayStack.getCount());
        System.out.println(myArrayStack.getSize());
    }

    @Test

    public void testMyQueue() {
        MyArrayQueue queue = new MyArrayQueue(5);
        Boolean aBoolean = queue.enqueue("1111");
        System.out.println(aBoolean);
        queue.enqueue("2222");
        queue.enqueue("3333");
        queue.enqueue("4444");
        queue.enqueue("5555");
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        Boolean aBoolean2 = queue.enqueue("6666");
        System.out.println(aBoolean2);

        System.out.println(queue.getHead());
        System.out.println(queue.getTail());
        System.out.println(queue.count());
    }

    @Test
    public void testMyCircularQueue() {
        MyCircularQueue queue = new MyCircularQueue(5);
        queue.enqueue("1111");
        queue.enqueue("2222");
        queue.enqueue("3333");
        queue.dequeue();
        queue.dequeue();
        Boolean enqueue2 = queue.enqueue("4444");
        System.out.println(enqueue2);
        Boolean enqueue = queue.enqueue("5555");
        System.out.println(enqueue);
        queue.enqueue("6666");
        queue.enqueue("7777");
        queue.enqueue("8888");
        queue.dequeue();
        queue.dequeue();
        queue.enqueue("4544");
        queue.enqueue("9999");
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.enqueue("4544");
        queue.enqueue("9999");
        queue.dequeue();
        queue.enqueue("4544");
        queue.enqueue("9999");
        queue.dequeue();
        queue.enqueue("4544");
        queue.enqueue("9999");
        queue.enqueue("1010");

        System.out.println("head----" + queue.getHead());
        System.out.println("tail----" + queue.getTail());
        System.out.println(Arrays.toString(queue.getItems()));
    }

    @Test
    public void testSort() {
        int[] array = {1, 3, 5, 32, 4, 7, 12, 43, 2, 7};
        int[] sort = BubbleSort.bubbleSort(array);
        System.out.println(Arrays.toString(sort));
        int[] insertSort = InsertionSort.insertSort(array);
        System.out.println(Arrays.toString(insertSort));
    }

    @Test
    public void testBinarySearch() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int i = BinarySearch.binarySearch(arr, 4);
        System.out.println(i);
    }




    @Test
    public void testShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new ShutDownHook());
    }

    private static class ShutDownHook extends Thread {
        public void run() {
            System.out.println("i am callbacked!");
        }
    }


    @Test
    public void testStream() {
        List<Person> list = Lists.newArrayList();
        List<Integer> collect = list.stream().map(Person::getAge).collect(Collectors.toList());

        Person person = new Person();
        person.setAge(20);
        Person person1 = new Person();
        person1.setAge(22);
        Person person2 = new Person();
        person2.setAge(16);
        list.add(person);
        list.add(person1);
        list.add(person2);
        List<Integer> collect1 = list.stream().filter(ft -> ft.getAge() > 18).map(Person::getAge).collect(Collectors.toList());
        System.out.println(collect1);
    }

    @Test
    public void testIter() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("3");
        list.add("5");
        list.add("4");
        list.add("6");
        list.add("7");


        Iterator<String> iterator = list.iterator();
        iterator.next();
        iterator.remove();
        iterator.remove();
    }

    @Test
    public void testComparator() {
        List<Person> people = new ArrayList<>();
        Person person1 = new Person();
        person1.setId(111).setName("zhagnsan").setAge(20).setSex(1);
        Person person2 = new Person();
        person2.setId(222).setName("lisi").setAge(23).setSex(0);
        Person person3 = new Person();
        person3.setId(333).setName("wangwu").setAge(18).setSex(1);
        System.out.println(person2);
        people.add(person1);
        people.add(person2);
        people.add(person3);
        Collections.sort(people, new AgeComparator());
        System.out.println(people);
    }


    class AgeComparator implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getAge() - o2.getAge();
        }
    }

    class NameComparator implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    @Test
    public void testLambda() {
        Optional<Integer> result = Stream.of("abc", "d", "efg", "hijk", "l")
                .filter(l -> l.length() <= 3)
                .map(str -> str.length())
                .max((o1, o2) -> o1 - o2);
        System.out.println(result.get());
    }

    @Test
    public void testStopWatch() {

        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            Thread.sleep(2000L);
            stopwatch.stop();
            System.out.println(stopwatch);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tetJoiner() {
        ArrayList<String> strings = new ArrayList<>();
        String join = Joiner.on(',').join(strings);
        System.out.println(join);
    }

    @Test
    public void testBigdecimal() {
        BigDecimal allUserTotalMoney = new BigDecimal(0.6);
        BigDecimal money = new BigDecimal(0.3);
        BigDecimal allUserTotalMoneyConig = new BigDecimal(0.8);
        System.out.println(allUserTotalMoney.add(money));
        System.out.println(allUserTotalMoney.add(money).compareTo(allUserTotalMoneyConig));

        Boolean b = allUserTotalMoney.add(money).compareTo(allUserTotalMoneyConig) == 1;
        System.out.println(b);



    }


    @Test
    public void testTagPrice() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsInventoryDetail", "[{\"qiang_price\":\"9888\",\"price\":\"9999\",\"supplyprice\":\"9590\",\"count\":\"1\",\"id\":\"254717_254719_\",\"lowestBuy\":1},{\"qiang_price\":\"10699\",\"price\":\"10999\",\"supplyprice\":\"10370\",\"count\":\"5\",\"id\":\"254718_254719_\",\"lowestBuy\":1}]");
        String goodsInventoryDetail = QYUtils.toString(map.get("goodsInventoryDetail"));
        BigDecimal goodsSupplyPrice = QYUtils.toDecimal(225, BigDecimal.ZERO);
        BigDecimal goodsStorePrice = QYUtils.toDecimal(275, BigDecimal.ZERO);
        if (StringUtils.isNotBlank(goodsInventoryDetail) && !"[]".equals(goodsInventoryDetail)) {
            //多规格
            List<Map<String, Object>> inventoryDetails = JSONObject.parseObject(goodsInventoryDetail,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
            BigDecimal allSupplyPrice = goodsSupplyPrice; // 供价
            JSONObject jsonObject;
            JSONArray jsonarray = new JSONArray();
            for (Map<String, Object> detail : inventoryDetails) {
                // id
                String id = QYUtils.toString(detail.get("id"));
                // 供价
                BigDecimal supplyprice = QYUtils.toDecimal(detail.get("supplyprice"));
                // 店铺价
                BigDecimal storePrice = QYUtils.toDecimal(detail.get("price"));

                supplyprice = QYUtils.isNull0(supplyprice) ? allSupplyPrice : supplyprice;
                //计算内购价
                BigDecimal tagPrice = QYUtils.mulBD(supplyprice, QYUtils.addBD(QYUtils.toDecimal(1), QYUtils.div(1, 100, 8)));
                tagPrice = getStaffPrice(storePrice, tagPrice);

                jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("price", tagPrice);
                jsonarray.add(jsonObject);
            }
            System.out.println(jsonarray);
        }
    }

    private BigDecimal getStaffPrice(BigDecimal storePrice, BigDecimal staffPrice) {
        //员工价大于等于店铺价 取店铺价
        storePrice = QYUtils.toDecimal(storePrice, BigDecimal.ZERO);
        staffPrice = staffPrice.compareTo(storePrice) >= 0 ? storePrice : staffPrice;

        return staffPrice;
    }

    @Test
    public void testss() {

        String aliAccount = "123456789";
        String lastString = "";
        if (aliAccount.contains("@")) {
            lastString += aliAccount.substring(aliAccount.indexOf("@"));
        } else {
            lastString += aliAccount.substring(aliAccount.length() - 3);
        }
        System.out.println((aliAccount.substring(0, 3) + "****" + lastString));

    }


    public static void isNotNull(Object arg, String message) {
        if (arg == null) {
            error(message);
        }
    }

    public static void error(String message) {
        throw new IllegalArgumentException(message);
    }

    @Test
    public void testDe() {
        BigDecimal decimal = new BigDecimal(0.3);
        int i = decimal.compareTo(new BigDecimal(0.119));
        System.out.println(i);
    }

    @Test
    public void testLong() {
        List<Long> objects = Lists.newArrayList();
        objects.add(1L);
        objects.add(2L);
        Long[] ss = {};
        objects.toArray(ss);
        System.out.println(ss);
    }

    @Test
    public void testDate() {
        String date = "20211031";
        String s = DateUtil.addInteger(date, Calendar.DATE, 1);
        System.out.println(s);

    }

    @Test
    public void testSub() {
        String str = "爱新觉罗";
        System.out.println(str.length());
        System.out.println(str.substring(str.length() - 1));
    }


    public String testttt(Integer orderStatus) {
        switch (orderStatus) {
            case 10:
                return "待付款";
            case 20:
                return "已付款";
            case 30:
                return "已发货";
            case 40:
                return "已收货";
            case 50:
                return "已完成";
            case 0:
                return "已关闭（取消）";
            default:
                return "其他";
        }
    }

    @Test
    public void teeee() {
        String fileName = "测试测试ceshi.xls";
        System.out.println(fileName.substring(fileName.lastIndexOf(".")));
        System.out.println(!".xlsx".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf("."))));
        System.out.println(!".xls".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf("."))));
        if (fileName.lastIndexOf(".") == -1 || (!".xlsx".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".")))
                && !".xls".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf("."))))) {
            System.out.println("baocuo");
        }
        try {
            System.out.println(URLEncoder.encode(fileName, "UTF-8"));
            System.out.println(new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testtttttt() {
        ArrayList<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal("1.2"));
        list.add(new BigDecimal("1.2"));
        list.add(new BigDecimal("1.2"));
        list.add(new BigDecimal("1.2"));
        list.add(new BigDecimal("1.2"));

        BigDecimal decimal = new BigDecimal("0.00");
        for (BigDecimal bigDecimal : list) {
            decimal = addBD(decimal, bigDecimal);
        }
        System.out.println(decimal.toString());
    }

    public static BigDecimal addBD(Object d1, Object d2) {
        BigDecimal b1 = getDecimal(d1);
        BigDecimal b2 = getDecimal(d2);
        return b1.add(b2);
    }

    public static BigDecimal getDecimal(Object d) {
        if (d == null) {
            return null;
        } else {
            return d instanceof BigDecimal ? (BigDecimal) d : new BigDecimal(String.valueOf(d));
        }
    }

    @Test
    public void tessssssssss() {

        String str = "{\n" +
                "\t\"1464079478393356289\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"addrDetail\": \"{\\\"areaId\\\":\\\"1401797451546886538\\\",\\\"areaInfo\\\":\\\"人保大厦\\\",\\\"cardNum\\\":\\\"\\\",\\\"creatorTime\\\":1637659980000,\\\"creatorUserId\\\":\\\"1463051088093728769\\\",\\\"deleteMark\\\":0,\\\"details\\\":\\\"河南省 郑州市 金水区 \\\",\\\"id\\\":\\\"1463078117161820161\\\",\\\"isdefault\\\":\\\"1\\\",\\\"mobile\\\":\\\"15538707686\\\",\\\"trueName\\\":\\\"郭先生\\\",\\\"userId\\\":\\\"1463051088093728769\\\"}\",\n" +
                "\t\t\t\"creatorTime\": \"2021-11-26 11:52:04\",\n" +
                "\t\t\t\"goodsCount\": 1,\n" +
                "\t\t\t\"goodsInfoRsList\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"goodsCartId\": \"1464079470263668736\",\n" +
                "\t\t\t\t\t\"goodsCount\": 1,\n" +
                "\t\t\t\t\t\"goodsId\": \"1463703874233942017\",\n" +
                "\t\t\t\t\t\"goodsImg\": \"https://oss.jianyejia.cn/SAAS_DEFAULT_TENANT/pics/2021/11/25/105821.e6e3539f58b74468ac3626ab3e4ae854.jpeg\",\n" +
                "\t\t\t\t\t\"goodsName\": \"老干妈牌小布丁（多规格）\",\n" +
                "\t\t\t\t\t\"goodsPrice\": 0.2,\n" +
                "\t\t\t\t\t\"goodsSpec\": \"{\\\"数量\\\":\\\"2个\\\"}\",\n" +
                "\t\t\t\t\t\"isApply\": false\n" +
                "\t\t\t\t}\n" +
                "\t\t\t],\n" +
                "\t\t\t\"id\": \"1464079479169302529\",\n" +
                "\t\t\t\"orderNo\": \"J202111261152035408\",\n" +
                "\t\t\t\"orderStatus\": 10,\n" +
                "\t\t\t\"payOrder\": \"1464079478393356289\",\n" +
                "\t\t\t\"payPrice\": 0.2,\n" +
                "\t\t\t\"shipPrice\": 0.0,\n" +
                "\t\t\t\"storeId\": \"1462987620460204034\",\n" +
                "\t\t\t\"storeName\": \"郭晓娜测试店铺\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"1463780635604905985\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"addrDetail\": \"{\\\"areaId\\\":\\\"1401797451546886538\\\",\\\"areaInfo\\\":\\\"人保大厦\\\",\\\"cardNum\\\":\\\"\\\",\\\"creatorTime\\\":1637659980000,\\\"creatorUserId\\\":\\\"1463051088093728769\\\",\\\"deleteMark\\\":0,\\\"details\\\":\\\"河南省 郑州市 金水区 \\\",\\\"id\\\":\\\"1463078117161820161\\\",\\\"isdefault\\\":\\\"1\\\",\\\"mobile\\\":\\\"15538707686\\\",\\\"trueName\\\":\\\"郭先生\\\",\\\"userId\\\":\\\"1463051088093728769\\\"}\",\n" +
                "\t\t\t\"creatorTime\": \"2021-11-25 16:04:35\",\n" +
                "\t\t\t\"goodsCount\": 0,\n" +
                "\t\t\t\"goodsInfoRsList\": [],\n" +
                "\t\t\t\"id\": \"1463780638834520066\",\n" +
                "\t\t\t\"orderNo\": \"J202111251604338714\",\n" +
                "\t\t\t\"orderStatus\": 10,\n" +
                "\t\t\t\"payOrder\": \"1463780635604905985\",\n" +
                "\t\t\t\"payPrice\": 0.04,\n" +
                "\t\t\t\"shipPrice\": 0.01,\n" +
                "\t\t\t\"storeId\": \"1463018020075147265\",\n" +
                "\t\t\t\"storeName\": \"燕青店铺\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"addrDetail\": \"{\\\"areaId\\\":\\\"1401797451546886538\\\",\\\"areaInfo\\\":\\\"人保大厦\\\",\\\"cardNum\\\":\\\"\\\",\\\"creatorTime\\\":1637659980000,\\\"creatorUserId\\\":\\\"1463051088093728769\\\",\\\"deleteMark\\\":0,\\\"details\\\":\\\"河南省 郑州市 金水区 \\\",\\\"id\\\":\\\"1463078117161820161\\\",\\\"isdefault\\\":\\\"1\\\",\\\"mobile\\\":\\\"15538707686\\\",\\\"trueName\\\":\\\"郭先生\\\",\\\"userId\\\":\\\"1463051088093728769\\\"}\",\n" +
                "\t\t\t\"creatorTime\": \"2021-11-25 16:04:34\",\n" +
                "\t\t\t\"goodsCount\": 0,\n" +
                "\t\t\t\"goodsInfoRsList\": [],\n" +
                "\t\t\t\"id\": \"1463780635823009793\",\n" +
                "\t\t\t\"orderNo\": \"J202111251604338719\",\n" +
                "\t\t\t\"orderStatus\": 10,\n" +
                "\t\t\t\"payOrder\": \"1463780635604905985\",\n" +
                "\t\t\t\"payPrice\": 0.01,\n" +
                "\t\t\t\"shipPrice\": 0.0,\n" +
                "\t\t\t\"storeId\": \"1462987620460204034\",\n" +
                "\t\t\t\"storeName\": \"郭晓娜测试店铺\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        Map<String, List<AppMyOrderInfoVo>> listMap = (Map<String, List<AppMyOrderInfoVo>>) JSON.parse(str);

        AppUnPayOrderInfoVo appUnPayOrderInfoVo;
        List<AppUnPayOrderInfoVo> result = new ArrayList<>();
        for (Map.Entry<String, List<AppMyOrderInfoVo>> listEntry : listMap.entrySet()) {
            appUnPayOrderInfoVo = new AppUnPayOrderInfoVo();
            String payOrder = listEntry.getKey();
            List<AppMyOrderInfoVo> value = listEntry.getValue();
            appUnPayOrderInfoVo.setPayOrder(payOrder)
                    .setOrderInfoVoList(value);
            result.add(appUnPayOrderInfoVo);
        }

        System.out.println(JSON.toJSONString(result));

        // 按照时间排序
        List<AppUnPayOrderInfoVo> collect = result.stream().sorted(Comparator.comparing(AppUnPayOrderInfoVo::getPayOrder).reversed()).collect(Collectors.toList());

        System.out.println(collect);

    }


    public static String getDateBefore(Date d, int day) {
        Calendar no = Calendar.getInstance();
        no.setTime(d);
        no.set(5, no.get(5) - day);
        String date = parseDate(no.getTime(), "yyyy-MM-dd");
        return date;
    }

    public static Date parseDate(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time + " 00:00:00");
            return date;
        } catch (Exception var3) {
            return null;
        }
    }

    public static String parseDate(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        String dateString = formater.format(date);
        return dateString;
    }

    @Test
    public void tessttssts() {
        String riHuanBi = "";
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        // 新增用户日环比
        Integer newUserBeforeYesterday = 30;
        Integer newUserBeforeToday = 20;
        int abs = Math.abs(newUserBeforeYesterday - newUserBeforeToday);
        String format = numberFormat.format((float) abs / (float) newUserBeforeYesterday * 100);
        if (newUserBeforeToday < newUserBeforeYesterday) {
            riHuanBi = "-" + format + "%";
        } else {
            riHuanBi = format + "%";
        }
        System.out.println(riHuanBi);

    }

    @Test
    public void tessdfadfs() {
        String date = "201-11-11,201-21";
        String[] dateStr = date.split(",");
        List<String> dateArrays = Arrays.asList(dateStr);
        String beginDate = "";
        String endDate = "";

        // 是否按照周期统计，false 统计具体的自然日
        Boolean flag = false;
        if (dateArrays.size() == 2) {
            flag = true;
            beginDate += dateArrays.get(0);
            endDate += dateArrays.get(1);
        }
        System.out.println(beginDate);
        System.out.println(endDate);
    }

    @Test
    public void testttsdfs() {
        String fileName = "测试";
        try {
            String finalFileName = new String((fileName + ".xlsx").getBytes("utf-8"), "utf-8");
            System.out.println(finalFileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println();

    }

    @Test
    public void stsdf() {
        Integer realSaleNumSort = null;
        Integer virtualSaleNumSort = 1;
        System.out.println((null == realSaleNumSort && null == virtualSaleNumSort));
        // System.out.println((null == realSaleNumSort && null == virtualSaleNumSort)?0:realSaleNumSort);

        Person person = new Person();
        if ((null == realSaleNumSort && null == virtualSaleNumSort)) {
            person.setAge(0);
        } else {
            person.setAge(realSaleNumSort);
        }


        System.out.println(person);

        HashMap<Object, Object> map = new HashMap<>();

        Integer totalUserCount = 19;
        double div = QYUtils.div(totalUserCount, 33, 4);
        System.out.println(div);
        map.put("rate", div * 100);

        System.out.println(map);

    }

    @Test
    public void tesrsdfa() {


        AppMyOrderInfoVo vo = new AppMyOrderInfoVo();
        AppMyOrderInfoVo vo2 = new AppMyOrderInfoVo();
        AppMyOrderInfoVo vo3 = new AppMyOrderInfoVo();
        vo.setGoodsCount(1);
        vo2.setGoodsCount(1);
        vo3.setGoodsCount(1);
        vo.setPayPrice(new BigDecimal("2.1"));
        vo2.setPayPrice(new BigDecimal("2.1"));
        vo3.setPayPrice(new BigDecimal("2.1"));

        ArrayList<AppMyOrderInfoVo> infoVos = new ArrayList<>();
        infoVos.add(vo);
        infoVos.add(vo2);
        infoVos.add(vo3);


        // 计算总金额和总商品数量
        Integer goodsTotalCount = infoVos.stream().collect(Collectors.summingInt(AppMyOrderInfoVo::getGoodsCount));
        BigDecimal totalFee = infoVos.stream().map(AppMyOrderInfoVo::getPayPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(goodsTotalCount);
        System.out.println(totalFee);

    }


    @Test
    public void testremove() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        long noRefundCount = list.stream().filter(e -> e == 1).count();
        System.out.println(noRefundCount);


        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list1.add(5);

        for (Integer integer : list) {
            Integer size = list1.size();
            for (int i = 0; i < size; i++) {
                if (list1.get(i) == integer) {
                    list1.remove(i);
                    break;
                }
            }
        }

        System.out.println(list);
        System.out.println(list1);

    }


    @Test
    public void testsdfdsfs() {

        String sumStr = "[{\"countMember\":1,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"1\",\"wuYeOrgName\":\"小区1\"},{\"countMember\":13,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"100\",\"wuYeOrgName\":\"郑州天筑\"},{\"countMember\":2,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"101\",\"wuYeOrgName\":\"城市花园\"},{\"countMember\":3,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"102\",\"wuYeOrgName\":\"香槟圣园\"},{\"countMember\":1,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"110\",\"wuYeOrgName\":\"城市花园\"}," +
                "{\"countMember\":1,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"111\",\"wuYeOrgName\":\"香槟圣园\"},{\"countMember\":1,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"139\",\"wuYeOrgName\":\"郑州枫林上院\"},{\"countMember\":2,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"142\",\"wuYeOrgName\":\"郑州嵩云\"},{\"countMember\":3,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"152\",\"wuYeOrgName\":\"奥体大唐\"},{\"countMember\":1,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"177\",\"wuYeOrgName\":\"郑州天筑\"},{\"countMember\":1,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"2\",\"wuYeOrgName\":\"小区2\"},{\"countMember\":1,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"82\",\"wuYeOrgName\":\"测试小区1\"},{\"countMember\":3,\"countTime\":\"2021-12-13\",\"wuYeOrgId\":\"84\",\"wuYeOrgName\":\"测试哇哈哈001\"}]";

        // date如果为空 idm 端默认获取前一天的数据，不为空为传入日期数据
        List<MemberOrgCountVO> memberOrgCount = new ArrayList<>();
        // 获取累计的用户数量
        List<MemberOrgCountVO> memberOrgSum = JSONArray.parseArray(sumStr, MemberOrgCountVO.class);


        WxBackSynthesisDataEntity dataEntity;
        List<WxBackSynthesisDataEntity> dataEntityList = new ArrayList<>();

        String yesterday = "";

        if (!CollectionUtils.isEmpty(memberOrgSum)) {
            for (MemberOrgCountVO sumVo : memberOrgSum) {
                dataEntity = new WxBackSynthesisDataEntity();
                dataEntity.setOrgName(sumVo.getWuYeOrgName())
                        .setOrgId(sumVo.getWuYeOrgId())
                        .setCountDate("2021-12-14")
                        .setTotalUserCount(sumVo.getCountMember());

                //定义是否匹配的标记
                Boolean hasSet = false;
                if (!CollectionUtils.isEmpty(memberOrgCount)) {
                    int size = memberOrgCount.size();
                    for (int i = 0; i < size; i++) {
                        if (sumVo.getWuYeOrgId().equals(memberOrgCount.get(i).getWuYeOrgId())) {
                            dataEntity.setNewUserCount(QYUtils.toInteger(memberOrgCount.get(i).getCountMember(), 0));
                            hasSet = true;
                            break;
                        }
                    }
                }
                if (!hasSet) {
                    dataEntity.setNewUserCount(0);
                }

                dataEntityList.add(dataEntity);


            }

        }

        //此方法统计每个小区所有下单用户数量
        String tomorrow = "[{\"result\":16},{\"result\":13,\"orgId\":\"100\"},{\"result\":2,\"orgId\":\"101\"},{\"result\":2,\"orgId\":\"102\"},{\"result\":4,\"orgId\":\"110\"},{\"result\":1,\"orgId\":\"111\"},{\"result\":1,\"orgId\":\"139\"},{\"result\":3,\"orgId\":\"142\"},{\"result\":2,\"orgId\":\"152\"},{\"result\":1,\"orgId\":\"2\"},{\"result\":4,\"orgId\":\"82\"},{\"result\":8,\"orgId\":\"84\"},{\"result\":3,\"orgId\":\"85\"},{\"result\":1,\"orgId\":\"88\"},{\"result\":2,\"orgId\":\"98\"},{\"result\":4,\"orgId\":\"99\"}]";

        List<Map> maps = JSONArray.parseArray(tomorrow, Map.class);
        for (WxBackSynthesisDataEntity wxBackSynthesisDataEntity : dataEntityList) {
            //定义是否匹配的标记
            Boolean hasSet = false;
            for (Map map : maps) {
                String orgId = QYUtils.toString(map.get("orgId"), "000000");
                if (orgId.equals(wxBackSynthesisDataEntity.getOrgId())) {
                    wxBackSynthesisDataEntity.setTotalPayUserCount(QYUtils.toInteger(map.get("result"), 0));
                    hasSet = true;
                    break;
                }
            }
            if (!hasSet) {
                wxBackSynthesisDataEntity.setTotalPayUserCount(0);
                wxBackSynthesisDataEntity.setOrgId("000");
                if (org.springframework.util.StringUtils.isEmpty(wxBackSynthesisDataEntity.getOrgName())) {
                    wxBackSynthesisDataEntity.setOrgName("未知");
                }
            }
        }

        System.out.println(JSON.toJSONString(dataEntityList));


    }

    @Test
    public void testrihu() {
        String riHuanBi;
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        // 新增用户
        Integer newUserCount = 10;
        // 新增用户日环比
        Integer newUserBeforeDate = 0;
        if (null == newUserBeforeDate || 0 == newUserBeforeDate) {
            riHuanBi = "100%";
        } else if (null == newUserCount) {
            riHuanBi = "0%";
        } else {
            int abs = Math.abs(newUserBeforeDate - newUserCount);
            String format = numberFormat.format((float) abs / (float) newUserBeforeDate * 100);

            if (newUserCount < newUserBeforeDate) {
                riHuanBi = "-" + format + "%";
            } else {
                riHuanBi = format + "%";
            }
        }
        System.out.println(riHuanBi);
    }

    @Test
    public void testsdfsaf() {
        String order = "[{\"addrDetail\":\"{\\\"areaId\\\":\\\"1401797451525914787\\\",\\\"areaInfo\\\":\\\"人保大厦\\\",\\\"cardNum\\\":\\\"\\\",\\\"creatorTime\\\":1639448396000,\\\"creatorUserId\\\":\\\"1470578149825921025\\\",\\\"deleteMark\\\":0,\\\"details\\\":\\\"辽宁省 大连市 庄河市 \\\",\\\"id\\\":\\\"1470579274824880130\\\",\\\"isdefault\\\":\\\"1\\\",\\\"mobile\\\":\\\"18000000006\\\",\\\"orgId\\\":\\\"142\\\",\\\"trueName\\\":\\\"郭晓娜\\\",\\\"userId\\\":\\\"1470578149825921025\\\"}\",\"channel\":\"wxapp\",\"creatorTime\":1639548081000,\"creatorUserId\":\"1470578149825921025\",\"deleteMark\":0,\"goodsAmount\":0.05,\"id\":\"1470997384849645570\",\"lastModifyManagerId\":\"1466220949355610113\",\"lastModifyTime\":1639548122000,\"lastModifyUserId\":\"1466220949355610113\",\"orderName\":\"卖小孩x1,卖小孩x1,\",\"orderNo\":\"J202112151401209557\",\"orderPrestatus\":30,\"orderStatus\":40,\"orgId\":\"142\",\"orgName\":\"郑州嵩云\",\"paidTime\":\"2021-12-15T14:01:26\",\"payOrder\":\"1470997384748982273\",\"payPrice\":0.05,\"payRequestTime\":\"2021-12-15T14:01:21\",\"quickParam\":\"wxapp\",\"shipCode\":\"\",\"shipName\":\"\",\"shipNameCh\":\"\",\"shipPrice\":0.00,\"showOrder\":0,\"srcFrom\":\"wxapp\",\"storeId\":\"1466220949207367682\",\"storeName\":\"娜娜子的店铺\",\"totalPrice\":0.05,\"userId\":\"1470578149825921025\",\"userMobile\":\"18000000006\"},{\"addrDetail\":\"{\\\"areaId\\\":\\\"1401797451525914787\\\",\\\"areaInfo\\\":\\\"人保大厦\\\",\\\"cardNum\\\":\\\"\\\",\\\"creatorTime\\\":1639448396000,\\\"creatorUserId\\\":\\\"1470578149825921025\\\",\\\"deleteMark\\\":0,\\\"details\\\":\\\"辽宁省 大连市 庄河市 \\\",\\\"id\\\":\\\"1470579274824880130\\\",\\\"isdefault\\\":\\\"1\\\",\\\"mobile\\\":\\\"18000000006\\\",\\\"orgId\\\":\\\"142\\\",\\\"trueName\\\":\\\"郭晓娜\\\",\\\"userId\\\":\\\"1470578149825921025\\\"}\",\"channel\":\"wxapp\",\"creatorTime\":1639453122000,\"creatorUserId\":\"1470578149825921025\",\"deleteMark\":0,\"goodsAmount\":0.02,\"id\":\"1470599097042669569\",\"lastModifyManagerId\":\"1466220949355610113\",\"lastModifyTime\":1639454946000,\"lastModifyUserId\":\"1466220949355610113\",\"orderName\":\"豆浆机x1,\",\"orderNo\":\"J202112141138410477\",\"orderPrestatus\":30,\"orderStatus\":40,\"orgId\":\"142\",\"orgName\":\"郑州嵩云\",\"paidTime\":\"2021-12-14T11:38:47\",\"payOrder\":\"1470599093989216257\",\"payPrice\":0.02,\"payRequestTime\":\"2021-12-14T11:38:42\",\"quickParam\":\"wxapp\",\"shipCode\":\"\",\"shipName\":\"\",\"shipNameCh\":\"\",\"shipPrice\":0.00,\"showOrder\":0,\"srcFrom\":\"wxapp\",\"storeId\":\"1466220949207367682\",\"storeName\":\"娜娜子的店铺\",\"totalPrice\":0.02,\"userId\":\"1470578149825921025\",\"userMobile\":\"18000000006\"}]";
        String cart = "[{\"afterSaleStatus\":7,\"canApply\":0,\"canEvaluation\":1,\"cartGoodsStatus\":0,\"cartStatus\":1,\"count\":1,\"creatorTime\":1639453111000,\"creatorUserId\":\"1470578149825921025\",\"deleteMark\":0,\"display\":1,\"evaluation\":0,\"freight\":0.00,\"goodsDetails\":\"<p>测试</p>\",\"goodsImage\":\"https://oss.jianyejia.cn/SAAS_DEFAULT_TENANT/pics/2021/12/08/144935.2735523328f74b11ba0b496f92892c83.jpeg\",\"goodsName\":\"豆浆机\",\"id\":\"1470599049915797504\",\"isApply\":1,\"isEnableSpec\":1,\"lastModifyTime\":1639454410000,\"lastModifyUserId\":\"1470578149825921025\",\"orderId\":\"1470599097042669569\",\"payPrice\":0.02,\"price\":0.02,\"refuseTimes\":0,\"skuId\":\"1468473311884042241\",\"sourceFrom\":1,\"sourceFromId\":0,\"specDetails\":\"{\\\"型号\\\":\\\"001\\\"}\",\"spuId\":\"1468473311829516290\",\"storeId\":\"1466220949207367682\",\"storeName\":\"娜娜子的店铺\",\"supplyPrice\":0.01,\"userId\":\"1470578149825921025\",\"weight\":0},{\"canApply\":1,\"canEvaluation\":1,\"cartGoodsStatus\":0,\"cartStatus\":1,\"count\":1,\"creatorTime\":1639548046000,\"creatorUserId\":\"1470578149825921025\",\"deleteMark\":0,\"display\":1,\"evaluation\":0,\"freight\":0.00,\"goodsDetails\":\"<p>点点滴滴</p>\",\"goodsImage\":\"https://oss.jianyejia.cn/SAAS_DEFAULT_TENANT/pics/2021/12/02/160455.ebd40a9745e740f0af561d57196fa43d.jpeg\",\"goodsName\":\"卖小孩\",\"id\":\"1470997237575323648\",\"isApply\":0,\"isEnableSpec\":1,\"lastModifyTime\":1639548087000,\"lastModifyUserId\":\"1470578149825921025\",\"orderId\":\"1470997384849645570\",\"payPrice\":0.02,\"price\":0.02,\"refuseTimes\":0,\"skuId\":\"1466317903585161218\",\"sourceFrom\":1,\"sourceFromId\":0,\"specDetails\":\"{\\\"类型\\\":\\\"吃人的小孩\\\",\\\"年龄\\\":\\\"1岁\\\"}\",\"spuId\":\"1466317903539023874\",\"storeId\":\"1466220949207367682\",\"storeName\":\"娜娜子的店铺\",\"supplyPrice\":0.01,\"userId\":\"1470578149825921025\",\"weight\":0},{\"afterSaleStatus\":5,\"canApply\":0,\"canEvaluation\":1,\"cartGoodsStatus\":0,\"cartStatus\":1,\"count\":1,\"creatorTime\":1639548051000,\"creatorUserId\":\"1470578149825921025\",\"deleteMark\":0,\"display\":1,\"evaluation\":0,\"freight\":0.00,\"goodsDetails\":\"<p>点点滴滴</p>\",\"goodsImage\":\"https://oss.jianyejia.cn/SAAS_DEFAULT_TENANT/pics/2021/12/02/160455.ebd40a9745e740f0af561d57196fa43d.jpeg\",\"goodsName\":\"卖小孩\",\"id\":\"1470997258873999360\",\"isApply\":1,\"isEnableSpec\":1,\"lastModifyTime\":1639548292000,\"orderId\":\"1470997384849645570\",\"payPrice\":0.03,\"price\":0.03,\"refuseTimes\":0,\"skuId\":\"1466317903593549825\",\"sourceFrom\":1,\"sourceFromId\":0,\"specDetails\":\"{\\\"类型\\\":\\\"吃人的小孩\\\",\\\"年龄\\\":\\\"2岁\\\"}\",\"spuId\":\"1466317903539023874\",\"storeId\":\"1466220949207367682\",\"storeName\":\"娜娜子的店铺\",\"supplyPrice\":0.01,\"userId\":\"1470578149825921025\",\"weight\":0}]";

        List<OrderInfoEntity> orderInfoEntities = JSONArray.parseArray(order, OrderInfoEntity.class);
        List<GoodsCartEntity> goodsCartEntities = JSONArray.parseArray(cart, GoodsCartEntity.class);

        AppMyOrderInfoVo appMyOrderInfoVo;
        AppMyOrderInfoGoodsVo goodsInfoRs;

        List<AppMyOrderInfoVo> result = Lists.newArrayList();
        try {
            for (OrderInfoEntity orderInfoEntity : orderInfoEntities) {
                Integer orderStatus = orderInfoEntity.getOrderStatus();
                appMyOrderInfoVo = new AppMyOrderInfoVo();
                // 赋值
                appMyOrderInfoVo.setOrderNo(orderInfoEntity.getOrderNo())
                        .setAddrDetail(orderInfoEntity.getAddrDetail())
                        .setPayOrder(orderInfoEntity.getPayOrder())
                        .setStoreId(orderInfoEntity.getStoreId())
                        .setPayPrice(orderInfoEntity.getPayPrice())
                        .setShipPrice(orderInfoEntity.getShipPrice())
                        .setGoodsAmount(orderInfoEntity.getGoodsAmount())
                        .setOrderStatus(orderStatus)
                        .setStoreId(orderInfoEntity.getStoreId())
                        .setMsg(orderInfoEntity.getMsg())
                        .setId(orderInfoEntity.getId());


                List<AppMyOrderInfoGoodsVo> goodsInfoRsList = Lists.newArrayList();
                Integer goodsCount = 0;

                // 组装 goodsInfo 数据
                for (GoodsCartEntity cartEntity : goodsCartEntities) {
                    if (cartEntity.getOrderId().equals(orderInfoEntity.getId())) {
                        System.out.println(orderInfoEntity.getOrderStatus());
                        System.out.println(cartEntity.getCartStatus());
                        if (orderInfoEntity.getOrderStatus().equals(40) && cartEntity.getAfterSaleStatus().equals(5)) {
                            continue;
                        } else {
                            goodsInfoRs = new AppMyOrderInfoGoodsVo();
                            goodsInfoRs.setGoodsCartId(cartEntity.getId())
                                    .setAfterSaleStatus(cartEntity.getAfterSaleStatus())
                                    .setGoodsId(cartEntity.getSpuId())
                                    .setGoodsName(cartEntity.getGoodsName())
                                    .setGoodsPrice(cartEntity.getPrice())
                                    .setGoodsSpec(cartEntity.getSpecDetails())
                                    .setGoodsCount(cartEntity.getCount())
                                    .setGoodsImg(cartEntity.getGoodsImage());

                            goodsInfoRsList.add(goodsInfoRs);
                            goodsCount += cartEntity.getCount();
                        }
                    }
                }

                appMyOrderInfoVo.setGoodsInfoRsList(goodsInfoRsList)
                        .setGoodsCount(goodsCount);
                result.add(appMyOrderInfoVo);
                System.out.println(JSON.toJSONString(appMyOrderInfoVo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void testdsf() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("adb", "sdfa");
        jsonObject.put("tesk", "isklf");
        System.out.println(JSON.toJSONString(jsonObject));
    }

    @Test
    public void tessdfs() {
        String prefix = "S";
        SimpleDateFormat dataformat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        String nowStr = dataformat.format(now);

        StringBuffer orderNo = new StringBuffer();
        orderNo.append(prefix);//类型的前缀
        orderNo.append(nowStr);//日期时分

        String millis = "" + System.currentTimeMillis();
        millis = millis.substring(millis.length() - 3, millis.length());
        orderNo.append(millis);
        orderNo.append((int) (Math.random() * 10));

        System.out.println(orderNo.toString());
    }

    @Test
    public void tesdfsafs() {
        int i = 100;
        System.out.println((~(i - 1)));
        System.out.println(~i);

        String s = "b1pFeGQ1YU5ka2dmLVRXMHV0U185RVdmdkZ0OA==";
        byte[] decode = Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(decode));
    }

    @Test
    public void testaaa() {
        Set<Short> set = new HashSet<>();
        for (short i = 0; i < 5; i++) {
            set.add(i);
            set.remove((short) (i - 1));
        }
        System.out.println(set.size());

    }

    @Test
    public void testam() {
        boolean isSelf = true;
        HashMap<String, Object> result = new HashMap<>();
        Integer settlementId = 9;
        if (settlementId != null && settlementId == 10) {
            result.put("settlementId", 9);
        } else if (!isSelf) {
            // 下级推荐的，报备就是推荐，到访不展示
            if ((settlementId != null && settlementId == 7) || (settlementId != null && settlementId == 8)) {
                result.put("settlementId", 12);
            }
        } else {
            result.put("settlementId", settlementId);
        }
        System.out.println(result);
    }


    @Test
    public void maopao(){

    }

    @Test
    public void testpin() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "安阳建业负");
        list.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("id", 2);
        map1.put("name", "喝阳小河");
        list.add(map1);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("id", 3);
        map2.put("name", "安阳小河");
        list.add(map2);

        System.out.println(list.stream().map(v -> QYUtils.toString(v.get("id"))).collect(Collectors.joining(",")));


        List<JSONObject> objectList = new ArrayList<>();

        // map转为jsonObject
        for (Map<String, Object> mapp : list) {
            JSONObject jsonObject = new JSONObject();
            Iterator<String> iterator = mapp.keySet().iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                jsonObject.put(next, mapp.get(next));
            }
            objectList.add(jsonObject);
        }

        System.out.println(objectList);


        System.out.println("------------");

        try {
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            // 输出26个字母
            for (int i = 1; i <= 26; i++) {
                Map<String, Object> treeMap = new HashMap<>();
                //首字母
                String word = String.valueOf((char) (96 + i)).toUpperCase();
                // 循环找出 首字母一样的数据
                List<JSONObject> letter = new ArrayList<>();
                Iterator<JSONObject> iterator = objectList.iterator();
                while (iterator.hasNext()) {
                    JSONObject jsonObject = iterator.next();
                    String s = ((String) jsonObject.get("name")).substring(0, 1);
                    String name = PinyinUtil.getPinYin(s).substring(0, 1).toUpperCase();
                    if (word.equals(name)) {
                        letter.add(jsonObject);
                        iterator.remove();
                    }
                }

                if (!CollectionUtils.isEmpty(letter)) {
                    treeMap.put("firstName", word);
                    treeMap.put("list", letter);
                    result.add(treeMap);
                }
            }
            System.out.println(JSON.toJSONString(result));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testesdfs() {
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(4);
        list2.add(6);
        list2.removeAll(list1);
        System.out.println(list2);


    }

    @Test
    public void testpage() {
        int pageNo = 2;
        int pageSize = 3;
        List<Integer> ids = new ArrayList();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(4);
        ids.add(5);
        ids.add(6);
        ids.add(7);
        ids.add(8);
        List<Integer> collect = ids.stream().skip((pageNo - 1) * pageSize).limit(pageSize).
                collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void testlambada() {
        List<Long> ids = new ArrayList();
        ids.add(1L);
        ids.add(2l);
        ids.add(3l);
        ids.add(4l);
        ids.add(5l);
        String collect = ids.stream().map(v -> QYUtils.toString(v)).collect(Collectors.joining(","));
        System.out.println(collect);
    }


    @Test
    public void testarra() {
        String s = "[12221,424575]";
        JSONArray jsonArray = QYUtils.toJsonArray(s);
        System.out.println(jsonArray.get(0));
        System.out.println(jsonArray.contains(12221));
    }

    @Test
    public void testif() {
        Date date = addDay(new Date(), -1);
        System.out.println(date);

    }

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

    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    @Test
    public void testi_() {
        int i = 1;
        int b;
        b = i++;
        System.out.println(i);
        System.out.println(b);
        int c;
        c = ++i;
        System.out.println(c);
        System.out.println(i);
    }


    @Test
    public void testdateutil(){
        ArrayList<Map<String, Object>> list = Lists.newArrayList();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("1","1111");
        list.add(map1);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("2","222");
        list.add(map2);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("3","333");
        list.add(map3);
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("4","444");
        list.add(map4);

        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()){
            Map<String, Object> next = iterator.next();
            if (next.get("1") != null && next.get("1").equals("1111")){
                iterator.remove();
            }
        }

        System.out.println(list.toString());

    }

    @Test
    public void testspilit(){
        String s = "漯河市—普通商品—单规格,,";
        String[] split = s.split(",");
        System.out.println(split.length);
        for (int i = 0; i < split.length; i++) {
            System.out.println( i + split[i]);
            if (i == 2){
                System.out.println(i + "测试是是是");
            }
        }

    }


    @Test
    public void testjson(){
        String s = "[{cityId: \"1\", areaIds: \"11,12,13\"}, {cityId: \"2\", aredIds: \"22,23,21\"}]";
        JSONArray jsonArray = JSON.parseArray(s);
        StringBuilder sb = new StringBuilder();
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String cityId = jsonObject.getString("cityId");
            list.add(cityId);
        }
        String join = Joiner.on(",").join(list);
        System.out.println(join);

    }

    @Test
    public void testmath(){
        BigDecimal price = new BigDecimal(1);
        Integer count = 1;
        String rewardAmount = "0.3";
        //固定金额的分佣方式
        BigDecimal totalProfit = new BigDecimal(count).multiply(new BigDecimal(rewardAmount));
        //平台7%手续费
        BigDecimal fee = totalProfit.multiply(new BigDecimal(7)).divide(new BigDecimal(100), 2, RoundingMode.HALF_DOWN);

        BigDecimal brokerProfit = totalProfit.subtract(fee);
        System.out.println(totalProfit + "--" + fee + "--" + brokerProfit);
    }


    @Test
    public void  testrand(){
        String orgIdsStr = ",2,3,3,";
        String substring = orgIdsStr.substring(1, orgIdsStr.length() - 1);
        System.out.println(substring);
    }

    @Test
    public void testr(){
        Random rand = new Random();
        String randStr = "";
        for (int i = 0; i < 10; i++) {
            randStr += rand.nextInt(10);
        }
        System.out.println("10位随机数：" + randStr);
    }

    @Test
    public void testtemp(){
        for (int i = 0; i < 10; i++) {
            System.out.println("===" + i);
            for (int j = 0; j < 3; j++) {
                if (j == 1){
                    continue;
                }
                System.out.println("----" + i + "---" + j);
            }
        }
    }


    @Test
    public void testde(){
        List<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("resId", 1L);
        map1.put("name", "test");
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("resId", 2L);
        map2.put("name", "222");
        list.add(map1);
        list.add(map2);
        List<Long> idList = list.stream().map(map -> QYUtils.toLong(map.get("resId"))).collect(Collectors.toList());
        System.out.println(idList);
    }


    public static BigDecimal toDecimal(Object value, int scale){
        if(value == null){
            return null;
        }
        if(value instanceof BigDecimal){
            BigDecimal result = ((BigDecimal) value).setScale(scale);
            return result;
        }
        return null;
    }


    @Test
    public void testLOngggg(){
            List<Long> l1 = new ArrayList<>();
            l1.add(1L);
            l1.add(12L);
            l1.add(14L);

            List<Integer> l2 = new ArrayList<>();
            l2.add(1);
            l2.add(12);
            l2.add(14);

            List<Long> collect = l1.stream()
                    .filter(l2::contains)
                    .collect(Collectors.toList());
            System.out.println(collect);
    }

    @Test
    public void testtime() throws ParseException {
        String storeIds = "11,22,33,44";
        List<Long> storeIdList = new ArrayList<>();
        storeIdList.add(11L);
        /*List<Long> storeIdsAll = Arrays.stream(storeIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        List<Long> searcStoreIds = storeIdsAll.stream()
                .filter(storeIdList::contains)
                .collect(Collectors.toList());
        System.out.println(searcStoreIds);*/



        List<Long> storeIdsAll11 = Arrays.stream(storeIds.split(","))
                .map(Long::valueOf).filter(storeIdList::contains)
                .collect(Collectors.toList());

        System.out.println(storeIdsAll11);


    }

    private static String removeBracketsAndContent(String input) {
        Pattern pattern = Pattern.compile("\\[.*?\\]");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }

    private static boolean isInTimeRange(String startTime, String endTime) throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
        return startDate.getTime() <= endDate.getTime();
    }


    @Test
    public void testdec(){

        List<Long> childrenIds = Arrays.stream("34134362".split(",")).map(childrenId -> Long.valueOf(childrenId)).collect(Collectors.toList());
        System.out.println(childrenIds);

    }

    @Test
    public void testi() {
        String ipMappings = "10.30.45.65-47.93.43.50";
        String metaServer = "10.30.45.65:8080";
        System.out.println("===> qmq transform:[metaServer=" + metaServer + ",ipMappings=" + ipMappings + "]");
        if (org.apache.commons.lang.StringUtils.isEmpty(ipMappings)) {
            System.out.println("111111111111===" + metaServer);
        }
        String[] ipAndPort = metaServer.split(":");
        if (ipAndPort == null || ipAndPort.length < 2) {
            System.out.println("222222222222===" + metaServer);
        }
        String[] ipMappingArray = ipMappings.split(",");
        if (ipMappingArray == null || ipMappings.length() < 1) {
            System.out.println("33333333333===" + metaServer);
        }
        for (String ipMapping : Arrays.asList(ipMappingArray)) {
            String oldIp = ipMapping.split("-")[0];
            String newIp = ipMapping.split("-")[1];
//                System.out.println("===> qmq transform:["+oldIp + " --- to ---> "+ newIp + "]");
            metaServer = metaServer.replace(oldIp, newIp);
        }
//            System.out.println("===> qmq transform:[result=" + metaServer + "]");
        System.out.println("4444444444444===" + metaServer);
    }

    @Test
    public void test3234(){
        String orderChildrenIds = "32782093";
        BigDecimal totalPriceAll = new BigDecimal(1224.63);//大订单支付金额
        totalPriceAll = QYUtils.subBD(totalPriceAll, BigDecimal.ZERO);//减去优惠券的金额

        BigDecimal pointsAmountAll= new BigDecimal(1.23);
        BigDecimal jycoinAll = BigDecimal.ZERO;
        totalPriceAll = QYUtils.subBD(totalPriceAll, pointsAmountAll);//减去积分抵扣金额
        if(!QYUtils.isBlank(orderChildrenIds)){ //大订单下有小订单
            List<Long> orderIds = QYUtils.getLongList(orderChildrenIds);
            int i = 0;
            long jycoinFenAdd = 0;
            long actualAmountFenAdd = 0;
            long actualPointFenAdd = 0;
            long actualBalanceFenAdd = 0;
            for(Long order_id : orderIds){
                i++;

                BigDecimal orderTotalPrice = new BigDecimal(1224.63);
                orderTotalPrice = QYUtils.subBD(orderTotalPrice,BigDecimal.ZERO);//减去优惠券的金额

                BigDecimal pointsAmount= new BigDecimal(1.23);
                orderTotalPrice = QYUtils.subBD(orderTotalPrice, pointsAmount);//减去积分抵扣金额


                //小订单通宝，按照支付金额比例分摊
                long jycoinFen = 0;
                if(i < orderIds.size()) {
                    jycoinFen = Math.round(QYUtils.div(QYUtils.mulBD(QYUtils.yuanToFen(orderTotalPrice), QYUtils.yuanToFen(BigDecimal.ZERO)), QYUtils.yuanToFen(totalPriceAll),8));
                    jycoinFenAdd = jycoinFenAdd + jycoinFen;
                }else {
                    jycoinFen = QYUtils.yuanToFen(BigDecimal.ZERO) - jycoinFenAdd;
                }

                orderTotalPrice = new BigDecimal(QYUtils.getDecimalStr(orderTotalPrice,2));

                //小订单实际支付金额，按照支付金额比例分摊
                long actualAmountFen = 0;
                long actualPointFen = 0;//积分
                long actualBalanceFen = 0;//本金

                //减掉通宝再进行比例换算
                long orderPayPrice = QYUtils.yuanToFen(orderTotalPrice) - jycoinFen;
                long orderPayPriceAll = QYUtils.yuanToFen(totalPriceAll) - QYUtils.yuanToFen(jycoinAll);


                BigDecimal actualAmount = new BigDecimal(1224.63);

                System.out.println("提前更新订单实付金额，orderPayPrice="+orderPayPrice+"，orderPayPriceAll="+orderPayPriceAll);
                if(i < orderIds.size()) {
                    System.out.println("121212121211");
                    actualAmountFen = Math.round(QYUtils.div(QYUtils.mulBD(orderPayPrice, QYUtils.yuanToFen(actualAmount)), orderPayPriceAll,8));
                    actualPointFen = Math.round(QYUtils.div(QYUtils.mulBD(orderPayPrice, QYUtils.yuanToFen(BigDecimal.ZERO)), orderPayPriceAll,8));
                    actualBalanceFen = Math.round(QYUtils.div(QYUtils.mulBD(orderPayPrice, QYUtils.yuanToFen(BigDecimal.ZERO)), orderPayPriceAll,8));

                    actualAmountFenAdd = actualAmountFenAdd + actualAmountFen;
                    actualPointFenAdd = actualPointFenAdd + actualPointFen;//积分
                    actualBalanceFenAdd = actualBalanceFenAdd + actualBalanceFen;//本金
                }else {
                    System.out.println("66776767676");
                    actualAmountFen = QYUtils.yuanToFen(actualAmount) - actualAmountFenAdd;
                    System.out.println("0000-----" + actualAmount + ",,,,actualfenadd--" + actualAmountFenAdd + "-----amount--" + actualAmountFen);
                    actualPointFen = QYUtils.yuanToFen(BigDecimal.ZERO) - actualPointFenAdd;//积分
                    actualBalanceFen = QYUtils.yuanToFen(BigDecimal.ZERO) - actualBalanceFenAdd;//本金
                }
                System.out.println("提前更新订单实付金额，orderNo="+"，actualAmountFen="+actualAmountFen);

            }
        }
    }


    @Test
    public void testnull(){
        Integer i = 10;
        Integer perPageSize = 10;
        Outer: while (true){
            //处理数据
            int startPosition = i * perPageSize; //开始位置
            StringBuffer limitSql = new StringBuffer("执行第" + i + "数据 limit " + startPosition + ", " + perPageSize);
            System.out.println(limitSql);

            // 线程数
            Integer threadNum = 10;
            //初始化线程池
            ExecutorService threadPool = Executors.newFixedThreadPool(20);
            for (int  j = 0; j < threadNum; j++) {
                final int fj = j;
                threadPool.execute(() -> {
                    System.out.println("jjjjjjjjjjjjjj----" + (startPosition + fj));
                });
            }
            threadPool.shutdown();
            Inner: while (true) {
                if (threadPool.isTerminated() ) {
                    System.out.println(i + "===iiiii==========");
                    break Inner;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

            i --;
            if (i < 0){
                System.out.println("线程终止iiii-->" + i);
                break Outer;
            }
        }
    }

    @Test
    public void testsa() throws Exception {

        // 创建一个新的Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建一个工作表
        XSSFSheet sheet = workbook.createSheet("Image Sheet");

        // 加载图片文件
        InputStream is = new FileInputStream("D:\\IdeaProjects\\gpylearn-demo\\alltest-demo\\0c64393d-7241-482b-9a96-896d3d89e252.png");
        byte[] bytes = IOUtils.toByteArray(is);
        is.close();

        // 将图片添加到工作簿中
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);

        // 创建图片的锚点
        CreationHelper createHelper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = createHelper.createClientAnchor();

        // 设置图片的位置
        anchor.setCol1(0); // 列号从0开始
        anchor.setRow1(0); // 行号从0开始

        // 创建图片对象并插入到工作表中
        drawing.createPicture(anchor, pictureIdx);

        // 写入文件
        FileOutputStream fileOut = new FileOutputStream("output.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // 关闭工作簿
        workbook.close();
    }

    @Test
    public void testdate(){
        Integer isBigmarket = null;
        Long brokerUserId = 111221L;
        Integer marketChannel = 0;
        String shareUserToken="weefkfaj809";
        Long shareUserId = 89998L;
        if(isBigmarket!=null && isBigmarket == 1){
            marketChannel = null;
        }else{
            if(null == brokerUserId){
                brokerUserId = shareUserId;
            }

            if(null == brokerUserId && QYUtils.isNotBlank(shareUserToken)){
                brokerUserId = 2222222L;
            }
        }
        if(null == brokerUserId && isBigmarket!=1){
            isBigmarket=1;
        }
        System.out.println(brokerUserId);
        System.out.println(isBigmarket);
    }

    @Test
    public void testLongg(){
        BigDecimal price = BigDecimal.valueOf(7.5);
        BigDecimal supplyPrice = BigDecimal.valueOf(2.5);
        Integer integralPayMinProfit = 4;
        // 是否可积分抵扣 0 不可以，1 可以
        BigDecimal rawBeneFits = BigDecimal.ZERO;
        rawBeneFits = (price.subtract(supplyPrice)).divide(price, 4,BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_UP);
        System.out.println("rawBenefits===========" + rawBeneFits);
        // 可抵扣利润
        BigDecimal creditDeductionProfit =  QYUtils.subBD(rawBeneFits, integralPayMinProfit);
        System.out.println("creditDeductionProfit---" + creditDeductionProfit);
        if (creditDeductionProfit.compareTo(BigDecimal.ZERO) > 0){
            BigDecimal bd = QYUtils.divBD(creditDeductionProfit, 100, 4);
            System.out.println("bd--->" + bd);
            BigDecimal creditDeductionMoney = QYUtils.mulBD(price, bd);
            System.out.println(",price->" + price + ",,supplyPrice->" + supplyPrice + ",,,creditDeductionMoney-->" + creditDeductionMoney );
        }
    }

    @Test
    public void testE(){

        List<Integer> rs = new ArrayList<>();
        rs.add(5);
        rs.add(7);


        int remaining = 20;

        for (Integer r : rs) {
            if (remaining <= 0) break;

            int pointsToUse = Math.min(r, remaining);
            r = (r - pointsToUse);
            remaining -= pointsToUse;

            if (r == 0) {
                System.out.println("r--------0");
            } else {
                r = (r - remaining);
            }
            System.out.println("r--->" + r);
        }
    }

}
