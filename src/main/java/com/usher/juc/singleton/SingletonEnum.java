package com.usher.juc.singleton;

/**
 * @Author: Usher
 * @Description:
 * 枚举实现单例
 */
public class SingletonEnum {

    private SingletonEnum() {
    }

    public static SingletonEnum getInstance(){
        return Singleton.INSTANCE.getSingletonEnum();
    }

    private enum Singleton{
        INSTANCE;

        private SingletonEnum singletonEnum;
        // JVM保证这个方法绝对只调用一次
        Singleton(){
            singletonEnum = new SingletonEnum();
        }

        public SingletonEnum getSingletonEnum() {
            return singletonEnum;
        }
    }
}
