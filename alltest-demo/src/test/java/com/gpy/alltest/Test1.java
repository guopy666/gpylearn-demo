package com.gpy.alltest;

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
import com.transinfo.utils.sm4.SM4Utils;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName Test1
 * @Description
 * @Author guopy
 * @Date 2021/7/7 17:35
 */
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
    public void testSec() {
        String str = "{msg: \"\",code: \"100\",data: {list: [{},{},{}],goodsclasslist: []},success: true,now: 1626746658721}";
        String encryStr = SM4Utils.encryptData_ECB(str, "52RFFHBWQRUCGUCE");


        String mi = "eyIxIjoiNTIwNjI4MDAxMjEwMDAzNjgxMDc0MCIsIjIiOiI1MjA2MjgwMDEiLCIzIjoiMDAwMDAwMDAwIiwiNCI6IiIsIjUiOiIxIiwiNiI6IjI1LjAiLCI3IjoiMjAyMTA3MjAiLCI4IjoiMTQ1MDAwIiwiOSI6IjEyIiwiMTAiOiLlvKDkvJrokI0iLCIxMSI6IjEiLCIxMiI6IjUyMjIyOTE5OTgxMTExNTQyNSJ9";

        String s = SM4Utils.decryptData_ECB(mi, "52RFFHBWQRUCGUCE");

        System.out.println(encryStr);
        System.out.println(s);

        byte[] decode = Base64.getDecoder().decode(mi.getBytes(StandardCharsets.UTF_8));
        String s1 = new String(decode);
        System.out.println(s1);

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
    public void testStopWatch(){

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
    public void tetJoiner(){
        ArrayList<String> strings = new ArrayList<>();
        String join = Joiner.on(',').join(strings);
        System.out.println(join);
    }

    @Test
    public void testBigdecimal(){
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
        map.put("goodsInventoryDetail","[{\"qiang_price\":\"9888\",\"price\":\"9999\",\"supplyprice\":\"9590\",\"count\":\"1\",\"id\":\"254717_254719_\",\"lowestBuy\":1},{\"qiang_price\":\"10699\",\"price\":\"10999\",\"supplyprice\":\"10370\",\"count\":\"5\",\"id\":\"254718_254719_\",\"lowestBuy\":1}]");
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
    public void testss(){

        String aliAccount = "123456789";
        String lastString = "";
        if (aliAccount.contains("@")){
            lastString += aliAccount.substring(aliAccount.indexOf("@"));
        } else {
            lastString += aliAccount.substring(aliAccount.length() -3);
        }
        System.out.println(( aliAccount.substring(0,3) + "****" + lastString));

    }


    public static void isNotNull(Object arg, String message) {
        if (arg == null) {
            error(message);
        }
    }

    public static void error(String message){
        throw new IllegalArgumentException(message);
    }

    @Test
    public void testDe(){
        BigDecimal decimal = new BigDecimal(0.3);
        int i = decimal.compareTo(new BigDecimal(0.119));
        System.out.println(i);
    }

}
