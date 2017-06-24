package com.ly;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Lo__oY on 2017/6/11.
 */
public class Counter {

    public volatile static int count = 0;
    public static final AtomicInteger atmoicinteger = new AtomicInteger(0);
    public static void inc() {

        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        count++;
    }

    public static void main(String[] args) {

        //同时启动1000个线程，去进行i++计算，看看实际结果

//        for (int i = 0; i < 1000; i++) {
//         //   new Thread(Counter::inc).start();
//            new Thread(()-> {
//                while(true){
//                int j = Counter.atmoicinteger.get();
//                if (Counter.atmoicinteger.compareAndSet(j, j + 1)) {
//                    try {
//                        Thread.sleep(100);
//                        break;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    System.out.println("failed compareAndSet");
//                }
//            }}).start();
//        }

        new Thread(()->{

            for (int i = 0; i < 1000; i++) {

                atmoicinteger.getAndIncrement();
                Counter.inc();
            }
        }).start();


//        try {
//            Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + Counter.count);
        System.out.println("运行结果:Counter.atmoicinteger=" + Counter.atmoicinteger.get());
    }
}