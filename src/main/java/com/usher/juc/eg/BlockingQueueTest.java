package com.usher.juc.eg;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingQueueTest {
    public static void main(String[] args){
        //默认大小Integer.MAX_VALUE
        BlockingDeque<String> bq = new LinkedBlockingDeque<>(3);

        Consumer consumer = new Consumer(bq);
        Producer producer = new Producer(bq);

        for (int i=0;i < 5;i++){
            new Thread(producer,"producer" + (i+1)).start();
            new Thread(consumer,"consumer" + (i+1)).start();
        }
    }
}
