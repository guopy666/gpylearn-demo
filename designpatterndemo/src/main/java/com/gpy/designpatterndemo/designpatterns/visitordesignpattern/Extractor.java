package com.gpy.designpatterndemo.designpatterns.visitordesignpattern;

/**
 * @ClassName Extractor
 * @Description
 * @Author guopy
 * @Date 2021/7/29 14:38
 */
public class Extractor implements Visitor{
    @Override
    public void visit(PDFFile pdfFile) {
        System.out.println("this is pdf extract");
    }

    @Override
    public void visit(WordFile wordFile) {
        System.out.println("this is word extract");
    }

    @Override
    public void visit(PPTFile pptFile) {
        System.out.println("this is ppt extract");
    }
}
