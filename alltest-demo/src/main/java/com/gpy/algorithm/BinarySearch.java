package com.gpy.algorithm;

/**
 * @ClassName BinarySearch 二分查找法(从一个数组中查找是否存在某个元素)
 * @Description 二分查找法的局限性：必须是数组，并且元素是有序的
 * @Author guopy
 * @Date 2021/7/9 14:42
 */
public class BinarySearch {

    public static int binarySearch(int[] arr, int value) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            // 如果low 和 high 都很大时，可能会溢出
            // int mid = (low + high) / 2;
            int mid = low + (high - low) / 2;
            if (arr[mid] == value) {
                return mid;
            } else if (arr[mid] < value) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

}
