package com.gpy.designpatterns.visitordesignpattern;

/**
 * @ClassName Compressor
 * @Description
 * @Author guopy
 * @Date 2021/7/29 14:40
 */
public class Compressor implements Visitor{
    @Override
    public void visit(PDFFile pdfFile) {
        System.out.println("this is pdf compress");
    }

    @Override
    public void visit(WordFile wordFile) {
        System.out.println("this is word compress");
    }

    @Override
    public void visit(PPTFile pptFile) {
        System.out.println("this is ppt compress");
    }
}
