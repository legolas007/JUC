package com.usher.juc.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Author: Usher
 * @Description:
 * 一个或多个线程等待其他线程完成，再执行,
 * 闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 *
 */
@Slf4j
public class CountDownLatchTest1 {

    private final static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i =0;i < threadCount;i++){
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        log.info("finish");
        executorService.shutdown();
    }
    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("{}", threadNum);
        Thread.sleep(100);
    }
}
