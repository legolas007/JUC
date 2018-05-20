package com.usher.juc.ThreadInAction;

/**
 * @Author: Usher
 * @Description:
 *
 * 有一个抽奖池,该抽奖池中存放了奖励的金额,该抽奖池用一个数组int[] arr = {10,5,20,50,100,200,500,800,2,80,300};
创建两个抽奖箱(线程)设置线程名称分别为“抽奖箱1”，“抽奖箱2”，随机从arr数组中获取奖项元素并打印在控制台上,格式如下:

抽奖箱1 又产生了一个 10 元大奖
抽奖箱2 又产生了一个 100 元大奖
//.....
 */
public class Solution1 {

    public static void main(String[] args) {
        Price price = new Price();
        Thread t1 = new Thread(price,"抽奖1");
        Thread t2 = new Thread(price,"抽奖2");
        t1.start();
        t2.start();
    }

}

class Price implements Runnable{
    int[] arr = { 10, 5, 20, 50, 100, 200, 500, 800, 2, 80, 300 };
    int num = arr.length;
    boolean[] flag = new boolean[arr.length];

    @Override
    public void run() {
        while (true){
            synchronized (this){
                    if (num >0){
                    int index = (int)(Math.random() * arr.length);
                    int get = arr[index];
                    if (flag[index] != true){
                        num--;
                        flag[index] = true;
                        System.out.println(Thread.currentThread().getName()+" " + get);
                    }
                }else break;
            }
        }

    }
}