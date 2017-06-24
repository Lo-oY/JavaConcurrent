package com.ly;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lo__oY on 2017/3/30.
 *
 * 测试Thread中断
 */
public class FileClock implements Runnable {



    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {

            System.out.println(new Date());

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {

                System.out.println("the thread has been interrupted");
            }
        }
    }

    public static void main(String args[]){

        FileClock fc = new FileClock();
        Thread thread = new Thread(fc);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
