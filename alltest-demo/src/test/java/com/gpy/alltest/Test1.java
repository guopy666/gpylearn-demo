package com.gpy.alltest;

import com.gpy.algorithm.sort.BinarySearch;
import com.gpy.algorithm.sort.BubbleSort;
import com.gpy.algorithm.sort.InsertionSort;
import com.gpy.datastructure.MyArrayQueue;
import com.gpy.datastructure.MyArrayStack;
import com.gpy.datastructure.MyCircularQueue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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

}
