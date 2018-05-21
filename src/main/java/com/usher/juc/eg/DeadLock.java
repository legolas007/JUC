package com.usher.juc.eg;

/**
 * @Author: Usher
 * @Description:
 * 现在有线程1和线程2。线程1执行过程中，
 * 先锁定了对象a，然后需要再锁定b才能继续执行代码；
 * 而线程2正巧相反，先锁定了b，需要再锁定a才能继续执行代码。
 * 这时，两个线程都等着对方解锁，才能继续执行，
 * 这时，两个线程就进入等待状态，
 * 最终不会有线程执行。这就变成了死锁。
 */
class DeadLock implements Runnable{

    boolean lockFormer;

    static Object object1 = new Object();
    static Object object2 = new Object();

    DeadLock(boolean lockFormer){
        this.lockFormer = lockFormer;
    }


    @Override
    public void run() {
        if (this.lockFormer){
            synchronized (object1){
                System.out.println("t1 locked object1 require to lock object2");
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (object2){
                    System.out.println("t1 locked object2");
                }
            }
        }else {
            synchronized (object2){
                System.out.println("t2 locked object2 require to lock object1");
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                synchronized (object1){
                    System.out.println("t2 locked object1");
                }
            }
        }
    }
    public static void main(String[] args) {
        Thread t1 = new Thread(
                new DeadLock(true));
        Thread t2= new Thread(
                new DeadLock(false));
        t1.start();
        t2.start();
    }
}