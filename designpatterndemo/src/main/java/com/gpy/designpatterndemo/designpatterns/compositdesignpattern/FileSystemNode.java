package com.gpy.designpatterndemo.designpatterns.compositdesignpattern;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FileSystemNode
 * @Description
 * @Author guopy
 * @Date 2021/7/23 15:19
 */
public class FileSystemNode {
    private String path;
    private Boolean isFile;
    private List<FileSystemNode> subNodes = new ArrayList<>();

    public FileSystemNode(String path, boolean isFile){
        this.path = path;
        this.isFile = isFile;
    }

    // 统计文件数量
    public Integer countNumOfFiles(){

        if (isFile){
            return 1;
        }
        int countFileNum = 0;
        for (FileSystemNode subNode : subNodes) {
            countFileNum += subNode.countNumOfFiles();
        }
        return countFileNum ;
    }

    //统计文件大小
    public Long countSizeOfFiles(){
        if (isFile){
            File file = new File(path);
            if (!file.exists()){
                return 0L;
            }
            return file.length();
        }
        Long fileLength = 0L;
        for (FileSystemNode subNode : subNodes) {
            fileLength += subNode.countSizeOfFiles();
        }
        return fileLength;
    }

    public String getPath(){
        return path;
    }

    public void addSubNode(FileSystemNode fileOrDir){
        subNodes.add(fileOrDir);
    }

    public void removeSubNode(FileSystemNode fileOrDir){
        int size = subNodes.size();
        int i = 0;
        for (; i < size; i++) {
            if (subNodes.get(i).getPath().equalsIgnoreCase(fileOrDir.getPath())){
                break;
            }
        }
        if (i < size){
            subNodes.remove(i);
        }
    }
}
