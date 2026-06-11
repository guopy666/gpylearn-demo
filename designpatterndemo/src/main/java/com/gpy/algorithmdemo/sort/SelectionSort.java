package com.gpy.algorithmdemo.sort;

/**
 * @ClassName SelectionSort
 * @Description 选择排序
 * @Author guopy
 * @Date 2026/4/3 14:48
 */
public class SelectionSort{
    public static void main(String[] args) {
        int[] arr = {6, 1, 8, 7, 2, 5, 3, 4, 1};
        for (int i = 0; i < arr.length; i++){
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++){
                if (arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }
            if (i != minIndex){
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
        System.out.println(java.util.Arrays.toString(arr));
    }
}
