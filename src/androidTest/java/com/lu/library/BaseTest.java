package com.lu.library;


/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro
 * @description: ${TODO}{ 类注释}
 * @date: 2018/10/25 0025
 */
public abstract class BaseTest extends BaseUiAbrary {
     protected String modelName="";
     public BaseTest(){

     }

     public void testMain(){
          //获取注解
          Class<KeepTest> clazz=KeepTest.class;
          KeepTest annotation=getClass().getAnnotation(clazz);
          if (annotation!=null){
              boolean isTest=annotation.isTest();
              if (!isTest){
                   output("*************"+modelName+"模块 不测试!!!!!");
                   return;
              }else{
                   output("*************"+modelName+"模块 需要测试!!!!!");
              }
          }

          deleteScreenShotByDir();
          long start=System.currentTimeMillis();
          outputStart(2,"测试"+modelName+"模块");
          realTestMain();
          outputEnd(2,"测试"+modelName+"模块");
          long end=System.currentTimeMillis();
          output("*************测试"+modelName+"模块耗时:"+formatTime(end-start)+"*************************");
     };
     public abstract void realTestMain();
}
