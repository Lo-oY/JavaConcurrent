package com.ly;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Lo__oY on 2017/6/25.
 */
public class CountDownLatchDemo {

    public static void main(String args[]){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Worker worker1 = new Worker("Worker-1",countDownLatch);
        Worker worker2 = new Worker("Worker-2",countDownLatch);
        worker1.start();
        worker2.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("all worke done at"+ sdf.format(new Date()));
    }


}

class Worker extends Thread{

    private String name;
    private CountDownLatch countDownLatch;
    public Worker(String name, CountDownLatch countDownLatch){

        this.name = name;
        this.countDownLatch = countDownLatch;

    }
    @Override
    public void run() {

        System.out.printf("%s:%s is working\n",Thread.currentThread().getName(),name);
        try {
            Thread.sleep(1000);
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
