package com.lu.library;

import com.lu.library.util.file.FileUtils;

public class Test {
    public static void main(String[] args){
        System.out.println(FileUtils.read("d:/data1.txt"));
        System.out.println("---------------------------------");
        System.out.println(FileUtils.read("d:/data1.txt",""));
    }
}