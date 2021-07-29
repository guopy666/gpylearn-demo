package com.gpy.designpatterns.visitordesignpattern;

/**
 * @ClassName PPTFile
 * @Description
 * @Author guopy
 * @Date 2021/7/29 14:34
 */
public class PPTFile extends ResourceFile{
    public PPTFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
