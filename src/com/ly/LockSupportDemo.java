package com.ly;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by Lo__oY on 2017/6/26.
 */
public class LockSupportDemo {

    public static void main(String args[]) throws InterruptedException {


            Thread t = new Thread(new Runnable()
            {
                private int count = 0;

                @Override
                public void run()
                {
                    long start = System.currentTimeMillis();
                    long end = 0;

                    while ((end - start) <= 1000)
                    {
                        count++;
                        end = System.currentTimeMillis();
                    }

                    System.out.println("after 1 second.count=" + count);

                    //等待或许许可
                    LockSupport.park();
                    System.out.println("thread over." + Thread.currentThread().isInterrupted());

                }
            });

            t.start();

            Thread.sleep(2000);

            // 中断线程
            t.interrupt();



            System.out.println("main over");
        }

    }




