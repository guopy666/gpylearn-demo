package com.gpy.designpatterns.compositdesignpattern;

import java.nio.file.Path;

/**
 * @ClassName MyFile
 * @Description
 * @Author guopy
 * @Date 2021/7/23 16:23
 */
public abstract class NewFileSystemNode  {

    protected String path;

    public NewFileSystemNode(String path){
        this.path = path;
    }

    public abstract Integer countNumOfFiles();

    public abstract Long countSizeOfFiles();

    public String getPath(){
        return path;
    }

}
