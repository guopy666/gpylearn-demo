package com.gpy.algorithmdemo.sort;

/**
 * @ClassName BubbleSort
 * @Description 冒泡排序
 * @Author guopy
 * @Date 2026/4/3 10:04
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {7, 2, 1, 4, 6, 5, 8, 2, 1};
        int length = arr.length;
        for (int i = 1; i < length; i++){
            Boolean flag = true;
            for (int j = 0; j < length -i; j++){
                if (arr[j] < arr[j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = false;
                }
            }
            if (flag){
                break;
            }
        }
    }
}
