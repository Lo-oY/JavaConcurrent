package com.ly;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lo__oY on 2017/3/30.
 * Java 提供2种形式的 join() 方法:

 join (long milliseconds)
 join (long milliseconds, long nanos)
 第一种join() 方法, 这方法让调用线程等待特定的毫秒数。例如，如果thread1对象使用代码thread2.join(1000),
 那么线程 thread1暂停运行，直到以下其中一个条件发生：

 thread2 结束运行
 1000 毫秒过去了
 当其中一个条件为真时，join() 方法返回。

 第二个版本的 join() 方法和第一个很像，只不过它接收一个毫秒数和一个纳秒数作为参数。
 */
public class TestJoin {

    public static void main(String args[]){

        Thread thread1 = new Thread(new DataSourceLoader(),"DataSourceLoader");
        Thread thread2 = new Thread(new NetworkConnectionLoader(),"NetworkConnectionLoader");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Main: Configuration has been loaded: %s\n",new Date());

    }
}

class DataSourceLoader implements Runnable{

    @Override
    public void run() {

        System.out.println("Begining source Loader" + new Date());

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End source Loader" + new Date());

    }
}


class NetworkConnectionLoader implements Runnable{

    @Override
    public void run() {

        System.out.println("Begining Connection Loader" + new Date());

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End Connection Loader" + new Date());
    }
}