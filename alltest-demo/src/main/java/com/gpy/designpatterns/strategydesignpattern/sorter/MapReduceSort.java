package com.gpy.designpatterns.strategydesignpattern.sorter;

/**
 * @ClassName MapReduceSort
 * @Description
 * @Author guopy
 * @Date 2021/7/27 17:12
 */
public class MapReduceSort implements ISorterAlg {

    @Override
    public void sort(String filePath) {
        System.out.println("this is mapreduceSort " + filePath);
    }
}
