package com.gpy.designpatterns.visitordesignpattern;

/**
 * @ClassName PDFFile
 * @Description
 * @Author guopy
 * @Date 2021/7/29 14:31
 */
public class PDFFile extends ResourceFile{

    public PDFFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
