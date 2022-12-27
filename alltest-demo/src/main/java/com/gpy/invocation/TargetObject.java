package com.gpy.invocation;

/**
 * @ClassName TargetObject
 * @Description
 * @Author guopy
 * @Date 2022/2/16 15:21
 */
public class TargetObject {
    private String value = "initvalue";

    public TargetObject(String value) {
        this.value = value;
    }

    public TargetObject() {
    }

    public void publicMethod(String str){
        System.out.println("Public Method, " + str);
    }

    public void privateMethod(){
        System.out.println("Private Method, " + value);
    }
}
