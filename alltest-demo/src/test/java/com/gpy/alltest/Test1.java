package com.gpy.alltest;

import com.google.common.collect.Lists;
import com.gpy.algorithm.BinarySearch;
import com.gpy.algorithm.sort.BubbleSort;
import com.gpy.algorithm.sort.InsertionSort;
import com.gpy.common.Person;
import com.gpy.datastructure.MyArrayQueue;
import com.gpy.datastructure.MyArrayStack;
import com.gpy.datastructure.MyCircularQueue;
import com.gpy.designpatterns.iteratordesignpattern.ArrayIterator;
import com.transinfo.utils.SecurityUtils;
import com.transinfo.utils.sm4.SM4Utils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName Test1
 * @Description
 * @Author guopy
 * @Date 2021/7/7 17:35
 */
public class Test1 {

    @Test
    public void test1(){
        MyArrayStack myArrayStack = new MyArrayStack(10);
        Boolean aaa = myArrayStack.push("aaa");
        System.out.println(myArrayStack.getCount());
        System.out.println(myArrayStack.getSize());
        System.out.println(myArrayStack.pop());
        System.out.println(myArrayStack.getCount());
        System.out.println(myArrayStack.getSize());
    }

    @Test
    public void testMyQueue(){
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
    public void testMyCircularQueue(){
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
    public void testSort(){
        int[] array = {1,3,5,32,4,7,12,43,2,7};
        int[] sort = BubbleSort.bubbleSort(array);
        System.out.println(Arrays.toString(sort));
        int[] insertSort = InsertionSort.insertSort(array);
        System.out.println(Arrays.toString(insertSort));
    }

    @Test
    public void testBinarySearch(){
        int[] arr = {1,2,3,4,5,6,7,8,9};
        int i = BinarySearch.binarySearch(arr, 4);
        System.out.println(i);
    }


    @Test
    public void testSec(){
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
    public void testShutDownHook(){
        Runtime.getRuntime().addShutdownHook(new ShutDownHook());
    }

    private static class ShutDownHook extends Thread{
        public void run(){
            System.out.println("i am callbacked!");
        }
    }


    @Test
    public void testLambdy(){
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
    public void testIter(){
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
}
