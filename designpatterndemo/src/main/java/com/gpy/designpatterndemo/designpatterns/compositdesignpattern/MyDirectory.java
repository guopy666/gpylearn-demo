package com.gpy.designpatterndemo.designpatterns.compositdesignpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyDirectory
 * @Description
 * @Author guopy
 * @Date 2021/7/23 16:29
 */
public class MyDirectory extends NewFileSystemNode{

    private List<NewFileSystemNode> subNodes = new ArrayList<>();

    public MyDirectory(String path){
        super(path);
    }

    public void addSubNode(NewFileSystemNode node){
        subNodes.add(node);
    }

    public void removeSubNode(NewFileSystemNode node){
        int i = 0;
        int size = subNodes.size();
        for (; i < size; i++) {
            if (subNodes.get(i).getPath().equalsIgnoreCase(node.getPath())){
                break;
            }
        }
        if (i < size){
            subNodes.remove(i);
        }
    }

    @Override
    public Integer countNumOfFiles() {
        int fileNum = 0;
        for (NewFileSystemNode subNode : subNodes) {
            fileNum += subNode.countNumOfFiles();
        }
        return fileNum;
    }

    @Override
    public Long countSizeOfFiles() {
        Long fileCount = 0L;
        for (NewFileSystemNode subNode : subNodes) {
            fileCount += subNode.countSizeOfFiles();
        }
        return fileCount;
    }
}
