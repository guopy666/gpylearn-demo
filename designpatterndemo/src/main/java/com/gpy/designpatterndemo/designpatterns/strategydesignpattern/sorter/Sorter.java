package com.gpy.designpatterndemo.designpatterns.strategydesignpattern.sorter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Sorter
 * @Description
 * @Author guopy
 * @Date 2021/7/27 17:18
 */
public class Sorter {

    private static final long GB = 1024 * 1024 * 1024;
    private static final List<AlgRange> algs = new ArrayList<>();

    static {
        algs.add(new AlgRange(0L, 6 * GB, SortAlgFactory.getSortAlg("QuickSort")));
        algs.add(new AlgRange(6 * GB, 10 * GB, SortAlgFactory.getSortAlg("ExternalSort")));
        algs.add(new AlgRange(10 * GB, 100 * GB, SortAlgFactory.getSortAlg("ConcurrentExternalSort")));
        algs.add(new AlgRange(100 * GB, Long.MAX_VALUE, SortAlgFactory.getSortAlg("MapReduceSort")));
    }

    public void sortFile(String filePath) {
        File file = new File(filePath);
        long size = file.length();
        ISorterAlg sorterAlg = null;
        for (AlgRange alg : algs) {
            if (alg.inRange(size)) {
                sorterAlg = alg.getAlg();
                break;
            }
        }
        sorterAlg.sort(filePath);
    }

    private static class AlgRange {
        private Long start;
        private Long end;
        private ISorterAlg alg;

        public AlgRange(Long start, Long end, ISorterAlg alg) {
            this.start = start;
            this.end = end;
            this.alg = alg;
        }

        public ISorterAlg getAlg() {
            return alg;
        }

        public boolean inRange(Long size) {
            return size < end && size >= start;
        }
    }

}
