package com.usher.juc.ForkAndJoin;

import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @Author: Usher
 * @Description:
 * ForkJoinPool
 */
public class TestForkJoinPool {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        Instant instant = Instant.now();
        ForkJoinTask<Long> task = new ForkAndJoin(0L,10000000L);
        Long sum = pool.invoke(task);
        Instant end = Instant.now();
        System.out.println(sum + " 时间：" + Duration.between(instant,end).toMillis());
    }
    @Test
    public void test(){
        Instant instant = Instant.now();
        Long sum = LongStream.rangeClosed(0L,10000000L)
                .parallel()
                .reduce(0L,Long ::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("耗费时间：" + Duration.between(instant,end).toMillis());
    }
}

class ForkAndJoin extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long THURSHOLD = 0L;

    public ForkAndJoin(long start, long end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THURSHOLD){
            long sum = 0L;
            for(long i = start;i <= end;i++){
                sum += i;
            }
            return sum;
        }else {
            long middle = (start + end) / 2;
            ForkAndJoin left = new ForkAndJoin(start, middle);
            left.fork();//拆分，压入线程队列
            ForkAndJoin right = new ForkAndJoin(middle + 1,end);
            right.fork();

            return left.join() + right.join();
        }
    }
}