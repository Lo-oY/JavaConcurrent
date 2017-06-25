package com.ly;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by Lo__oY on 2017/3/30.
 */
public class TestInterrupt {

    public static void main(String args[]){


        Runnable runnable = new Runnable() {
            private final Object object = new Object();
            @Override
            public void run() {
                synchronized (object) {
                    System.out.println("thread " + Thread.currentThread().getName() + " is running");
                    try {

                        object.wait();
                    } catch (InterruptedException e) {
                        System.out.println("interrupt");
                        //    e.printStackTrace();

                    }

                }
            }
        };
        Thread t = new Thread(runnable);

        t.start();
        t.interrupt();
}
}
