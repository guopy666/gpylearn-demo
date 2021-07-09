package com.gpy.algorithm.sort;

/**
 * @ClassName InsertionSort 插入排序 从已经排序的区间找到一个合适的位置插入元素，插入后移动之前的元素
 * @Description
 * @Author guopy
 * @Date 2021/7/8 15:00
 */
public class InsertionSort {

    public static int[] insertSort(int[] arr) {
        int length = arr.length;
        if (length <= 1) {
            return arr;
        }

        for (int i = 1; i < length; i++) {
            int value = arr[i];
            int j = i - 1;
            //找到插入的位置
            for (; j >= 0; j--) {
                if (arr[j] > value) {
                    arr[j + 1] = arr[j];//移动数据
                } else {
                    break;
                }
            }
            //插入数据
            arr[j + 1] = value;
        }
        return arr;
    }
}
