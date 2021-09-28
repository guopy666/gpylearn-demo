package com.gpy.designpatterndemo.designpatterns.iteratordesignpattern.snapshotiterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @ClassName ArrayList
 * @Description
 * @Author guopy
 * @Date 2021/7/29 11:06
 */
public class ArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private int actualSize;//不包含被删除的元素
    private int totalSize;//包含删除的元素

    private Object[] elements;
    private long[] addTimeStamp;
    private long[] delTimeStamp;

    public ArrayList() {
        this.actualSize = 0;
        this.totalSize = 0;
        this.elements = new Object[DEFAULT_CAPACITY];
        this.addTimeStamp = new long[DEFAULT_CAPACITY];
        this.delTimeStamp = new long[DEFAULT_CAPACITY];
    }

    @Override
    public boolean add(E e) {
        elements[totalSize] = e;
        addTimeStamp[totalSize] = System.currentTimeMillis();
        delTimeStamp[totalSize] = Long.MAX_VALUE;
        totalSize ++;
        actualSize ++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < totalSize; i++) {
            if (elements[i].equals(o)){
                delTimeStamp[i] = System.currentTimeMillis();
                actualSize --;
            }
        }
        return true;
    }

    @Override
    public E get(int index) {
        if (index >= totalSize){
            throw new IndexOutOfBoundsException();
        }
        return (E) elements[index];
    }

    public int totalSize(){
        return this.totalSize;
    }

    public int actualSize(){
        return this.actualSize;
    }

    public long getAddTimeStamp(int index){
        if (index >= totalSize){
            throw new IndexOutOfBoundsException();
        }
        return addTimeStamp[index];
    }

    public long getDelTimeStamp(int index){
        if (index >= totalSize){
            throw new IndexOutOfBoundsException();
        }
        return delTimeStamp[index];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

}
