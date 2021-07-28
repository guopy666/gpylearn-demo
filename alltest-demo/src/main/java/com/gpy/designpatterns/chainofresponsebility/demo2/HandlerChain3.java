package com.gpy.designpatterns.chainofresponsebility.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HandlerChain3
 * @Description
 * @Author guopy
 * @Date 2021/7/28 10:50
 */
public class HandlerChain3 {
    private List<IHandler> handlers = new ArrayList<>();

    public void addHandler(IHandler handler) {
        handlers.add(handler);
    }

    public void handle() {
        for (IHandler iHandler : handlers) {
            boolean handle = iHandler.handle();
            if (handle) {
                break;
            }
        }
    }
}
