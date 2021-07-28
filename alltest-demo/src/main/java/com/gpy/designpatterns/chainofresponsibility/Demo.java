package com.gpy.designpatterns.chainofresponsibility;

import com.gpy.designpatterns.chainofresponsibility.basedemo.HandlerA;
import com.gpy.designpatterns.chainofresponsibility.basedemo.HandlerB;
import com.gpy.designpatterns.chainofresponsibility.basedemo.HandlerChain;
import com.gpy.designpatterns.chainofresponsibility.demo1.NewHandlerA;
import com.gpy.designpatterns.chainofresponsibility.demo1.NewHandlerB;
import com.gpy.designpatterns.chainofresponsibility.demo1.NewHandlerChain;
import com.gpy.designpatterns.chainofresponsibility.demo2.HandlerChain3;
import com.gpy.designpatterns.chainofresponsibility.demo2.HandlerImplA;
import com.gpy.designpatterns.chainofresponsibility.demo2.HandlerImplB;
import com.gpy.designpatterns.chainofresponsibility.demo2.IHandler;

/**
 * @ClassName Demo
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:15
 */
public class Demo {

    public static void main(String[] args) {
        HandlerA handlerA = new HandlerA();
        HandlerB handlerB = new HandlerB();

        HandlerChain handlerChain = new HandlerChain();
        handlerChain.addHandler(handlerA);
        handlerChain.addHandler(handlerB);

        handlerChain.handle();

        System.out.println("--------------=============------------");

        NewHandlerA newHandlerA = new NewHandlerA();
        NewHandlerB newHandlerB = new NewHandlerB();
        NewHandlerChain handlerChain1 = new NewHandlerChain();
        handlerChain1.addNewHandler(newHandlerA);
        handlerChain1.addNewHandler(newHandlerB);

        handlerChain1.handle();

        System.out.println("--------------=============------------");

        IHandler iHandlerA = new HandlerImplA();
        IHandler iHandlerB = new HandlerImplB();

        HandlerChain3 handlerChain3 = new HandlerChain3();
        handlerChain3.addHandler(iHandlerA);
        handlerChain3.addHandler(iHandlerB);

        handlerChain3.handle();

    }


}
