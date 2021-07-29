package com.gpy.designpatterns.iteratordesignpattern.snapshotiterator;

import com.gpy.designpatterns.iteratordesignpattern.Iterator;

/**
 * @ClassName SnapShotIteratorTwo
 * 通过时间戳标记出快照，容器的元素添加时有addTimeStamp，同时删除时间戳置delTimeStamp为Long的最大值，
 * 如果元素被删除，更新删除时间戳为当前时间
 * 迭代器创建时以当前时间为创建时间戳，createTimeStamp，
 * 只有符合 addTimeStamp < createTimeStamp < delTimeStamp 的条件才是迭代器的快照
 * @Description
 * @Author guopy
 * @Date 2021/7/29 10:57
 */
public class SnapShotIteratorTwo<E> implements Iterator<E> {

    private long snapShotTimeStamp;
    private int cursorInAll;//在整个容器中的下标，而非快照的下标
    private int leftConut;//快照中还有几个元素未被遍历
    private ArrayList<E> arrayList;

    public SnapShotIteratorTwo(ArrayList<E> arrayList) {
        this.snapShotTimeStamp = System.currentTimeMillis();
        this.cursorInAll = 0;
        this.leftConut = arrayList.actualSize();
        this.arrayList = arrayList;

        jumpNext();//跳到迭代器的第一个元素
    }

    private void jumpNext() {
        while (cursorInAll < arrayList.totalSize()) {
            long addTimestamp = arrayList.getAddTimeStamp(cursorInAll);
            long delTimestamp = arrayList.getDelTimeStamp(cursorInAll);
            if (snapShotTimeStamp > addTimestamp && snapShotTimeStamp < delTimestamp) {
                leftConut--;
                break;
            }
            cursorInAll++;
        }
    }

    @Override
    public boolean hasnext() {
        return this.leftConut >= 0;
    }

    @Override
    public E currentItem() {
        return null;
    }

    @Override
    public void next() {
        E currentItem = arrayList.get(cursorInAll);
        jumpNext();
        // return currentItem;
    }
}
