package com.gpy.datastructure;

/**
 * @ClassName MyCircularQueue 循环队列
 * @Description
 * @Author guopy
 * @Date 2021/7/8 10:36
 */
public class MyCircularQueue {

    private String[] items;
    private int n = 0;
    private int head = 0;
    private int tail = 0;

    public MyCircularQueue(int capacity){
        this.items = new String[capacity];
        this.n = capacity;
    }

    //入队操作
    public Boolean enqueue(String item){
        //队列判断是否已满
        if ((tail + 1) % n == head){
            return false;
        }
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    //出队操作
    public String dequeue(){
        //表示没有元素
        if (head == tail){
            return null;
        }
        String result = items[head];
        head = (head + 1) % n;
        return result;
    }

    public int getHead(){
        return this.head;
    }

    public int getTail(){
        return this.tail;
    }

    public String[] getItems(){
        return this.items;
    }
}
