package com.usher.juc.cas;

/**
 * @Author: Usher
 * @Description:
 * 模拟CAS
 */
public class TestCompareAndSwap {
    public static void main(String[] args) {
        final CompareAndSwap compareAndSwap = new CompareAndSwap();
        for(int i = 0;i < 10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = compareAndSwap.getValue();
                    boolean b = compareAndSwap.compareAndSet(expectedValue,(int)(Math.random()*101));
                    System.out.println(b);
                }
            }).start();
        }
    }
}

class CompareAndSwap{
    private int value;
    //内存值
    public synchronized int getValue(){
        return value;
    }
    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;
        if (oldValue == expectedValue){
            this.value = newValue;
        }
        return oldValue;
    }
    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue,newValue);
    }
}