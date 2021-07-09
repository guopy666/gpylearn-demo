package com.gpy.algorithm.sort;

/**
 * @ClassName BubbleSort 冒泡算法
 * @Description
 * @Author guopy
 * @Date 2021/7/8 14:35
 */
public class BubbleSort {

    public static int[] bubbleSort(int[] array) {
        int length = array.length;
        if (length <= 1) {
            return array;
        }
        //标记本次循环是否发生了数据交换，如果没有发生数据交换说明已经排序完成，可以直接终止循环
        Boolean flag = false;
        for (int i = 1; i < length - 1; i++) {
            for (int j = 0; j < length - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
        return array;
    }
}
