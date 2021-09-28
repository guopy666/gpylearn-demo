package com.gpy.designpatterndemo.designpatterns.visitordesignpattern;

/**
 * @ClassName ResourceFile
 * @Description 各种类型文件的抽象父类
 * @Author guopy
 * @Date 2021/7/29 14:25
 */
public abstract class ResourceFile {
    protected String filePath;

    public ResourceFile(String filePath){
        this.filePath = filePath;
    }

    public abstract void accept(Visitor visitor);
}
