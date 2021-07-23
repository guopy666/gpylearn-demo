package com.gpy.designpatterns.compositdesignpattern;

import java.io.File;

/**
 * @ClassName MyFile
 * @Description
 * @Author guopy
 * @Date 2021/7/23 16:26
 */
public class MyFile extends NewFileSystemNode{

    public MyFile(String path){
        super(path);
    }

    @Override
    public Integer countNumOfFiles() {
        return 1;
    }

    @Override
    public Long countSizeOfFiles() {
        File file = new File(path);
        if (!file.exists()){
            return 0L;
        }
        return file.length();
    }
}
