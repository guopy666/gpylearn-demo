package com.gpy.datastructure;

/**
 * @ClassName MyArrayQueue 基于数组实现的队列
 * @Description
 * @Author guopy
 * @Date 2021/7/8 9:36
 */
public class MyArrayQueue {
    private String[] items;
    //队列长度
    private int n = 0;
    //头部指针
    private int head = 0;
    //尾部指针
    private int tail = 0;

    public MyArrayQueue(int capacity){
        this.items = new String[capacity];
        this.n = capacity;
    }

    //这种写法会导致元素出队后，其实队列有位置但是不能执行入队操作
    /*//入队操作
    public Boolean enqueue(String item){
        if (tail == n){
            return false;
        }
        items[tail] = item;
        ++tail;
        return true;
    }*/

    public Boolean enqueue(String item){
        if (tail == n){
            if (head == 0){//确实没有位置
                return false;
            }
            for (int i = head; i < tail; i++) {
                items[i - head] = items[i];
            }
            tail -= head;
            head = 0;
        }
        //执行入队操作
        items[tail] = item;
        ++tail;
        return true;
    }

    //出队操作
    public String dequeue(){
        //表示没有元素
        if (head == tail){
            return null;
        }
        String result = items[head];
        ++head;
        return result;
    }

    public int getHead(){
        return this.head;
    }

    public int getTail(){
        return this.tail;
    }

    public int count(){
        return tail - head;
    }
}
