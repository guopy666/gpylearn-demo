package com.gpy.designpatterns.compositdesignpattern;

/**
 * @ClassName DemoClass
 * @Description
 * @Author guopy
 * @Date 2021/7/23 16:46
 */
public class DemoClass {

    public static void main(String[] args) {
        MyDirectory directory = new MyDirectory("/");
        MyDirectory myDirectory = new MyDirectory("/tester");
        MyDirectory myDirectory1 = new MyDirectory("/testsub1");
        MyFile myFile1 = new MyFile("/a.txt");
        MyFile myFile2 = new MyFile("/b.txt");
        MyFile myFile3 = new MyFile("/c.txt");
        MyFile myFile4 = new MyFile("/d.txt");
        directory.addSubNode(myDirectory);
        directory.addSubNode(myDirectory1);
        myDirectory1.addSubNode(myFile2);
        myDirectory1.addSubNode(myFile3);
        myDirectory1.addSubNode(myFile4);

    }
}
