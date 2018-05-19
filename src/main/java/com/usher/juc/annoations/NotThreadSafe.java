package com.usher.juc.annoations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Usher
 * @Description:
 * 自定义注解标记线程安全，方便标识
 */
@Target(ElementType.TYPE)//指定该策略的Annotation可以修饰类，接口或枚举定义
@Retention(RetentionPolicy.SOURCE)//注解生存期，Annotation值保留在源代码，编译器直接丢弃这种Annotation
public @interface NotThreadSafe {
    String value() default "";
}