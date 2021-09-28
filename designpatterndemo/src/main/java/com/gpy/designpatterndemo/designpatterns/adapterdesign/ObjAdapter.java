package com.gpy.designpatterndemo.designpatterns.adapterdesign;

/**
 * @ClassName ObjAdapter 基于对象实现的适配器，基于组合
 * @Description
 * @Author guopy
 * @Date 2021/7/23 10:41
 */
public class ObjAdapter {
    private Adaptee adaptee;

    public ObjAdapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }

    public void f1(){
        adaptee.fa();//委托给Adaptee
    }

    public void f2(){
        //可以委托，可以重新实现
    }

    public void fc(){
        adaptee.fc();
    }


}
