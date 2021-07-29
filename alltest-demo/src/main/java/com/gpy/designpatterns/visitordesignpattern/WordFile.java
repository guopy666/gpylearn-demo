package com.gpy.designpatterns.visitordesignpattern;

/**
 * @ClassName WordFile
 * @Description
 * @Author guopy
 * @Date 2021/7/29 14:33
 */
public class WordFile extends ResourceFile{

    public WordFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
