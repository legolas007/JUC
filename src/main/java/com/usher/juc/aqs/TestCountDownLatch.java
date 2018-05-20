package com.usher.juc.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Usher
 * @Description:
 * 闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 */
public class TestCountDownLatch {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(4);
        LatchDemo latchDemo = new LatchDemo(latch);
        Long start = System.currentTimeMillis();
        int sum = 0;
        for (int i=0;i < 4;i++){
            new Thread(latchDemo).start();
        }
        try {
            //当前线程等待，释放当前锁
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - start));
    }
}

class LatchDemo implements Runnable{

    private CountDownLatch latch;
    public LatchDemo(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this){
            try {
                for (int i =0;i < 100;i++){

                    System.out.println(Thread.currentThread().getName() + ":"+ i);
                }
            } finally {
                latch.countDown();
            }
        }
    }
}