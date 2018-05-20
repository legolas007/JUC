package com.usher.juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author: Usher
 * @Description:
 * AQS简核心是通过一个共享变量来同步状态，
 * 变量的状态由子类去维护，而AQS框架做的是：
    线程阻塞队列的维护
    线程阻塞和唤醒
 * 会发现等待的线程是按照阻塞时的顺序依次获取到锁的
 * 这是因为AQS是基于一个叫CLH lock queue的一个变种来实现线程阻塞队列的
 *
 */
class SimpleLock extends AbstractQueuedSynchronizer {
    private static final long serialVersionUID = -7316320116933187634L;

    public SimpleLock() {

    }

    protected boolean tryAcquire(int unused) {
        if (compareAndSetState(0, 1)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    protected boolean tryRelease(int unused) {
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    public void lock() {
        acquire(1);
    }

    public boolean tryLock() {
        return tryAcquire(1);
    }

    public void unlock() {
        release(1);
    }

    public boolean isLocked() {
        return isHeldExclusively();
    }
}
