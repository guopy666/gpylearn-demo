package com.gpy.designpatterndemo.designpatterns.strategydesignpattern.sorter;

/**
 * @ClassName ExternSort
 * @Description
 * @Author guopy
 * @Date 2021/7/27 17:11
 */
public class ExternalSort implements ISorterAlg {

    @Override
    public void sort(String filePath) {
        System.out.println(" this is externalSort " + filePath);
    }
}
