package com.usher.juc.eg;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: Usher
 * @Description:
 * Callable需要FutureTask实现类支持，用于接受运算结果。FutureTask是Future接口的实现类
 */
public class CallableTest {
    public static void main(String[] args) {
        //ThreadDemo threadDemo = new ThreadDemo();

        final CountDownLatch latch = new CountDownLatch(4);
        LatchDemo latchDemo = new LatchDemo(latch);
        FutureTask<Integer> res = new FutureTask<>(latchDemo);
        Integer sum = 0;
        for (int i=0;i < 4;i++){
            new Thread(res).start();
            //接受线程运算的结果
            try{
                //latch.await();
                sum += res.get();
                System.out.println(sum);
            }catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
        }
        System.out.println(sum);
    }
}

class LatchDemo implements Callable<Integer>{

    private CountDownLatch latch;
    public LatchDemo(CountDownLatch latch){
        this.latch = latch;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        synchronized (this){
            try {
                for (int i =0;i < 100;i++) {
                    sum += i;
                }
            } finally {
                latch.countDown();
            }
        }
        return sum;
    }
}