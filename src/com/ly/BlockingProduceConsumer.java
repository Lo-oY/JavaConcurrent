package com.ly;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Lo__oY on 2017/6/26.
 */
public class BlockingProduceConsumer {

    public static void main(String ags[]) {

        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);
        // BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        //不设置的话，LinkedBlockingQueue默认大小为Integer.MAX_VALUE

        // BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);

        for (int i = 0; i < 5; i++) {
            new BlockProducer(queue, "Producer" + (i + 1)).start();

            new BlockConsumer(queue, "Consumer" + (i + 1)).start();
        }
    }
}


class BlockProducer extends Thread{

    private BlockingQueue<String> queue;

    public BlockProducer(BlockingQueue<String> queue,String name){
        super(name);
        this.queue = queue;
    }
    @Override
    public void run() {

        String temp = "A product,生产线程" + Thread.currentThread().getName();

        System.out.println("I have made a product" + Thread.currentThread().getName());
        try {
            queue.put(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BlockConsumer extends Thread{

    private BlockingQueue<String> queue;

    public BlockConsumer(BlockingQueue<String> queue,String name){
        super(name);
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
           String temp = queue.take();
            System.out.println(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
