package com.gpy.datastructuredemo.datastructure;

import java.util.Arrays;

/**
 * @ClassName MyStack
 * @Description 基于数组实现的栈结构
 * @Author guopy
 * @Date 2026/4/2 10:54
 */
public class MyStack {
    private int[] myStack;  // 栈
    private int count;  // 栈中元素个数
    private int capacity; // 栈容量
    public static final int GROW_FACTOR = 2; // 扩容因子

    //  带初始容量的构造函数
    public void MyStack(int capacity) {
        this.capacity = capacity;
        myStack = new int[capacity];
        count = 0;
    }
    // 不带容量的构造函数
    public void MyStack(){
        this.capacity = capacity;
        myStack = new int[capacity];
        count = 0;
    }

    // 入栈
    public void push(int value) {
        if (count >= capacity) {
            ensureCapacity();
        }
        myStack[count ++] = value;
    }

    //确保容量大小
    private void ensureCapacity() {
        int newCapacity = capacity * GROW_FACTOR;
        myStack = Arrays.copyOf(myStack, newCapacity);
        capacity = newCapacity;
    }

    // 出栈
    public int pop() {
        if (count <= 0) {
            System.out.println("栈为空");
            return -1;
        }
        count--;
        return myStack[count];
    }

    // 获取栈顶元素(不出栈)
    public int peek() {
        if (count <= 0) {
            System.out.println("栈为空");
            return -1;
        }
        return myStack[count - 1];
    }
}
