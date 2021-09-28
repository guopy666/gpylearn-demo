package com.gpy.designpatterndemo.designpatterns.strategydesignpattern.sorter;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SortAlgFactory
 * @Description
 * @Author guopy
 * @Date 2021/7/27 17:18
 */
public class SortAlgFactory {

    private static final Map<String, ISorterAlg> algs = new HashMap<>();

    static {
        algs.put("QuickSort", new QuickSort());
        algs.put("ExternalSort", new ExternalSort());
        algs.put("ConcurrentExternalSort", new ConcurrentExternalSort());
        algs.put("MapReduceSort", new MapReduceSort());
    }

    public static ISorterAlg getSortAlg(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        return algs.get(type);
    }

}
