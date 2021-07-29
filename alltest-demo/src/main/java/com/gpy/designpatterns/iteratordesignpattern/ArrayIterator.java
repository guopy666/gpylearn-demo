package com.gpy.designpatterns.iteratordesignpattern;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @ClassName ArrayIterator
 * @Description
 * @Author guopy
 * @Date 2021/7/29 9:32
 */
public class ArrayIterator<E> implements Iterator<E> {
    private int cursor;
    private List<E> arrayList;

    public ArrayIterator(List<E> arrayList) {
        this.cursor = 0;
        this.arrayList = arrayList;
    }

    @Override
    public boolean hasnext() {
        //cursor 指向最后一个元素时，hasnext() 仍然是 true
        return cursor != arrayList.size();
    }

    @Override
    public E currentItem() {
        if (cursor >= arrayList.size()) {
            throw new NoSuchElementException();
        }
        return arrayList.get(cursor);
    }

    @Override
    public void next() {
        cursor++;
    }
}
