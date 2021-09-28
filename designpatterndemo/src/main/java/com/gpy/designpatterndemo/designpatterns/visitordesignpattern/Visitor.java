package com.gpy.designpatterndemo.designpatterns.visitordesignpattern;

/**
 * @ClassName Visitor
 * @Description
 * @Author guopy
 * @Date 2021/7/29 14:26
 */
public interface Visitor {

    void visit(PDFFile pdfFile);
    void visit(WordFile wordFile);
    void visit(PPTFile pptFile);
}
