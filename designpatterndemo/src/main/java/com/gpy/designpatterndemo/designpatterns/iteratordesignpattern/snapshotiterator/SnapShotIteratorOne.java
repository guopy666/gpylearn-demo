package com.gpy.designpatterndemo.designpatterns.iteratordesignpattern.snapshotiterator;

import com.gpy.designpatterndemo.designpatterns.iteratordesignpattern.Iterator;

import java.util.ArrayList;

/**
 * @ClassName SnapShotIteratorOne
 * 每次创建迭代器时都复制一份容器元素的快照，修改的话修改原有的容器数据，迭代器遍历这个副本
 * 简单粗暴，但是消耗内存
 * @Description
 * @Author guopy
 * @Date 2021/7/29 10:52
 */
public class SnapShotIteratorOne<E> implements Iterator<E> {

    private int cursor;
    private ArrayList<E> snapShot;

    public SnapShotIteratorOne(ArrayList<E> snapShot) {
        this.cursor = 0;
        this.snapShot = new ArrayList<>();
        this.snapShot.addAll(snapShot);
    }

    @Override
    public boolean hasnext() {
        return cursor < snapShot.size();
    }

    @Override
    public E currentItem() {
        E e = snapShot.get(cursor);
        cursor ++;
        return e;
    }

    @Override
    public void next() {

    }
}
