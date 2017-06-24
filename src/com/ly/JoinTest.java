package com.ly;

/**
 * Created by Lo__oY on 2017/6/24.
 */
public class JoinTest {


    public static void main(String args[]) throws InterruptedException {

        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("t1");
            }
        });

        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2");
            }
        });


        final Thread t3= new Thread(() -> {

            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3");
        });

        t1.start();
        Thread.sleep(1000);
        t2.start();
        t3.start();

    }
}
