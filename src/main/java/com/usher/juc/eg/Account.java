package com.usher.juc.eg;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    /**
     * 可重入锁
     */
    private final ReentrantLock lock = new ReentrantLock();
    //获得指定lock对象的Condition
    private final Condition condition = lock.newCondition();
    private String accountNo;
    private double balance;
    private boolean flag = false;
    public Account(){}

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    //线程安全的draw方法
    public void draw(double drawAmount){
        //lock
        lock.lock();
        /*
        try{
            if(balance >= drawAmount){
                System.out.println(Thread.currentThread().getName() + "取钱成功" + drawAmount);
                try{
                    Thread.sleep(1);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                balance -= drawAmount;
                System.out.println("\t余额为： " + balance);
            }
            else {
                System.out.println(Thread.currentThread().getName() + "取钱失败");
            }
        }finally {
            lock.unlock();
        }*/
        try{
            if(!flag){
                condition.await();
            }else {
                System.out.println(Thread.currentThread().getName() + "取钱：" + drawAmount);
                balance -= drawAmount;
                System.out.println("余额为：" + balance);
                flag = false;
                condition.signalAll();
            }
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    @Override
    public boolean equals(Object o) {
        /*if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNo, account.accountNo);*/
        if(this == o){
            return true;
        }
        if (o != null && o.getClass() == Account.class){
            Account target = (Account)o;
            return target.getAccountNo().equals(accountNo);
        }
        return false;
    }

    @Override
    public int hashCode() {

        //return Objects.hash(accountNo);
        return accountNo.hashCode();
    }

}
