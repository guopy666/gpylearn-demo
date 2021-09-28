package com.gpy.designpatterndemo.designpatterns.statemachine;

//所有状态类的接口
public interface IMario {

    State getName();

    //定义的事件
    void obtainMushRoom();
    void obtainCape();
    void obtainFireFlower();
    void meetMonster();

}
