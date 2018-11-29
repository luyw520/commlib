package com.lu.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro
 * @description: ${TODO}{ 注解该模式是否测试}
 * @date: 2018/10/26 0026
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KeepTest {
    boolean isTest() default true;
}
