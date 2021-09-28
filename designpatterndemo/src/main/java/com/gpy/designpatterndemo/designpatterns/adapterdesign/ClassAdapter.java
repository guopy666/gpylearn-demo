package com.gpy.designpatterndemo.designpatterns.adapterdesign;

/**
 * @ClassName ClassAdapter 基于类实现的适配器模式（基于继承）
 * @Description
 * @Author guopy
 * @Date 2021/7/20 17:40
 */
public class ClassAdapter extends Adaptee implements ITarget{

    @Override
    public void f1() {
        super.fa();
    }

    @Override
    public void f2() {
        super.fb();
    }

    //fc() 不需要实现，直接继承父类的，这个是和对象适配器的不同之处
}
