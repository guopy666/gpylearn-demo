package com.gpy.datastructure;

/**
 * @ClassName MyArrayStack 基于数组实现栈结构
 * @Description
 * @Author guopy
 * @Date 2021/5/31 16:47
 */
public class MyArrayStack {
    private int count;//元素数量
    private int size;//容量
    private String[] items;

    /**
     * @Description  初始化一个大小为 n 的栈结构
     * @Author guopy
     * @Date 2021/7/7 17:37
     * @param n
     * @Return
     **/
    public MyArrayStack(int n){
        this.items = new String[n];
        this.size = n;
        this.count = 0;
    }

    public Boolean push(String item){
        //如果空间不够，入栈失败
        if (count == size){
            return false;
        }
        //在count位置放置元素 item， 然后count+1
        items[count] = item;
        ++count;
        return true;
    }

    /**
     * @Description  跳栈
     * @Author guopy
     * @Date 2021/7/7 17:36
     * @param
     * @Return java.lang.String
     **/
    public String pop(){
        //如果没有元素，直接返回null
        if (count == 0){
            return null;
        }
        //返回下标为count-1的元素，并修改count
        String temp = items[count-1];
        --count;
        return temp;
    }

    public int getSize(){
        return this.size;
    }

    public int getCount(){
        return this.count;
    }

}
