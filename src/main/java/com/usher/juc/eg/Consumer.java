package com.usher.juc.eg;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {
    private BlockingQueue<String> bq;

    public Consumer(BlockingQueue<String> bq) {
        this.bq = bq;
    }

    @Override
    public void run() {
            try {
                String string = bq.take();
                System.out.println("准备消费：" + string);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
}
