package com.gpy.designpatterndemo.designpatterns.visitordesignpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Demo
 * @Description
 * @Author guopy
 * @Date 2021/7/29 14:41
 */
public class Demo {
    public static void main(String[] args) {
        Extractor extractor = new Extractor();
        List<ResourceFile> allResourceFiles = getAllResourceFiles("");
        for (ResourceFile allResourceFile : allResourceFiles) {
            allResourceFile.accept(extractor);
        }

        Compressor compressor = new Compressor();
        List<ResourceFile> allResourceFiles1 = getAllResourceFiles(null);
        for (ResourceFile resourceFile : allResourceFiles1) {
            resourceFile.accept(compressor);
        }
    }

    private static List<ResourceFile> getAllResourceFiles(String fileDirectory){
        List<ResourceFile> list = new ArrayList<>();
        list.add(new PDFFile("a.pdf"));
        list.add(new PPTFile("b.ppt"));
        list.add(new WordFile("c.word"));
        return list;
    }
}
