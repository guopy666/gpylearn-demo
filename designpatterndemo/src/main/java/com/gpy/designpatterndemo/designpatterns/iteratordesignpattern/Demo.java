package com.gpy.designpatterndemo.designpatterns.iteratordesignpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Demo
 * @Description
 * @Author guopy
 * @Date 2021/7/29 9:36
 */
public class Demo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add("123");
        list.add("456");
        list.add("789");
        list.add("10j");
        list.add("qkA");

        ArrayIterator iterator = new ArrayIterator<>(list);
        while (iterator.hasnext()){
            System.out.println(iterator.currentItem());
            iterator.next();
        }
    }

}
