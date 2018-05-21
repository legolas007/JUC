package com.usher.juc.eg;

import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
    private BlockingQueue<String> bq;

    public Producer(BlockingQueue<String> bq) {
        this.bq = bq;
    }

    @Override
    public void run() {
        try {
            String string =Thread.currentThread().getName() + "'s product." ;
            System.out.println(Thread.currentThread().getName() + ": I have made a product:" );
            bq.put(string);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
