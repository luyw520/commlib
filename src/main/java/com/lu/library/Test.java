package com.lu.library;

import com.lu.library.util.file.FileUtil;

public class Test {
    public static void main(String[] args){
        System.out.println(FileUtil.read("d:/data1.txt"));
        System.out.println("---------------------------------");
        System.out.println(FileUtil.read("d:/data1.txt",""));
    }
}