package com.usher.juc.eg;

public class DrawThread extends Thread{
    private Account account;
    private double drawAmount;

    public DrawThread(String name, Account account, double drawAmount) {
        super(name);
        this.account = account;
        this.drawAmount = drawAmount;
    }

    @Override
    public void run() {
        /**
         * 加锁-修改-释放锁
         */
        synchronized (account){
            if(account.getBalance() >= drawAmount){
                System.out.println(getName() + "取钱成功" + drawAmount);
                try{
                    Thread.sleep(1);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                account.setBalance(account.getBalance() - drawAmount);
                System.out.println("\t余额为： " + account.getBalance());
            }
            else {
                System.out.println(getName() + "取钱失败");
            }
        }
    }

    public static void main(String[] args){
        Account account = new Account("123456789",1000);
        new DrawThread("A",account,800).start();
        new DrawThread("B",account,800).start();
    }
}
