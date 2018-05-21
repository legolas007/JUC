package com.usher.juc.eg;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Usher
 * @Description:
 * 线程交替，编写一个程序，开启3个线程，ID分别为A，B,C，每个线程将自己的ID在屏幕上打印10遍，
 * 要求输出的结果必须按顺序显示,
 * 如：ABCABCABC...依次递归
 */
public class ThreadAlternate {
    public static void main(String[] args) {
        TestThreadAlternate testThreadAlternate = new TestThreadAlternate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i <= 20;i++){
                    testThreadAlternate.loopA(i);
                }
            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i <= 20;i++){
                    testThreadAlternate.loopB(i);
                }
            }
        },"B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i <= 20;i++){
                    testThreadAlternate.loopC(i);
                }

            }
        },"C").start();
    }
}

class TestThreadAlternate{

    private int number = 1;//正在执行的线程标记

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void loopA(int totalLoop){
        lock.lock();

        try {
            if (number != 1){
                condition1.await();
            }

            for (int i=1;i <= 1;i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" +totalLoop);
            }

            number = 2;
            //唤醒2
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void loopB(int totalLoop){
        lock.lock();

        try {
            if (number != 2){
                condition2.await();
            }

            for (int i=1;i <= 1;i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" +totalLoop);
            }

            number = 3;
            //唤醒3
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void loopC(int totalLoop){
        lock.lock();

        try {
            if (number != 3){
                condition3.await();
            }

            for (int i=1;i <= 1;i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" +totalLoop);
            }

            number = 1;
            //唤醒1
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}