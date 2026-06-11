package com.gpy.algorithmdemo.sort;

/**
 * @ClassName InsertionSort
 * @Description 插入排序
 * @Author guopy
 * @Date 2026/4/3 15:58
 */
public class InsertionSort {
    public static void main(String[] args) {
        // 初始版本，可以优化
        int[] arr = {6, 1, 8, 7, 2, 5, 3, 4, 1};
        for(int i = 1; i< arr.length;i++){
            while (i > 0 && arr[i] < arr[i - 1]){
                int temp = arr[i];
                arr[i] = arr[i - 1];
                arr[i-1] = temp;
                i--;
            }
        }
        System.out.println(java.util.Arrays.toString(arr));
    }
}
