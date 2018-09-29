package com.lu.library;

import com.lu.library.util.file.FileUtil;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.lu.library
 * @description: ${TODO}{ 类注释}
 * @date: 2018/9/29 0029
 */
public class Test {
    public static void main(String[] args){
        System.out.println(FileUtil.read("d:/data1.txt"));
        System.out.println("---------------------------------");
        System.out.println(FileUtil.read("d:/data1.txt",""));
    }
}
