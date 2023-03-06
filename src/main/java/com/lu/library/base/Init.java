package com.lu.library.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.SOURCE)
@interface Init {
    String value() default "";
}