package com.gpy.designpatterns.strategydesignpattern.sorter;

/**
 * @ClassName QuickSort
 * @Description
 * @Author guopy
 * @Date 2021/7/27 17:11
 */
public class QuickSort implements ISorterAlg {
    @Override
    public void sort(String filePath) {
        System.out.println("this is quickSort " + filePath);
    }
}
