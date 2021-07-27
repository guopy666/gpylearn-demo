package com.gpy.designpatterns.strategydesignpattern.sorter;

/**
 * @ClassName ConcurrentExternalSort
 * @Description
 * @Author guopy
 * @Date 2021/7/27 17:15
 */
public class ConcurrentExternalSort implements ISorterAlg {

    @Override
    public void sort(String filePath) {
        System.out.println("this is concurrentExternalSort " + filePath);
    }
}
