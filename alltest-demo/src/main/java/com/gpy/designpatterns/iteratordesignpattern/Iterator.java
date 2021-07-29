package com.gpy.designpatterns.iteratordesignpattern;

/**
 * @Description 迭代器接口定义
 * @Author guopy
 * @Date 2021/7/29 9:32
 * @Return
 **/
public interface Iterator<E> {
    boolean hasnext();
    E currentItem();
    void next();
}
