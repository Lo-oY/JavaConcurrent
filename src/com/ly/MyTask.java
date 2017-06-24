package com.ly;

import java.util.concurrent.ForkJoinPool;

/**
 * Created by Lo__oY on 2017/6/13.
 */
public class MyTask extends MyWorkerTask {

    private int start;
    private int end;
    //10. 声明一个私有 int 值 array，名为 array。
    private int array[];

    //11. 实现类的构造函数，初始化它的属性值。
    public MyTask(String name, int array[], int start, int end) {
        super(name);
        this.array = array;
        this.start = start;
        this.end = end;
    }

    //12. 实现 compute() 方法。此方法通过 start 和 end 属性来决定增加array的元素块。如果元素块的元素超过100个，把它分成2部分，并创建2个Task对象来处理各个部分。再使用 invokeAll() 方法把这些任务发送给池。
    protected void compute() {
        if (end - start > 100) {
            int mid = (end + start) / 2;
            MyTask task1 = new MyTask(this.getName() + "1", array, start, mid);
            MyTask task2 = new MyTask(this.getName() + "2", array, mid, end);
            invokeAll(task1, task2);

//13.如果元素块的元素少于100，使用for循环增加全部的元素。
        } else {
            for (int i = start; i < end; i++) {
                array[i]++;
            }

//14. 最后，让正在执行任务的线程进入休眠50毫秒。
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception {

//16. 创建一个10，000元素的 int array。
        int array[] = new int[10000];

//17.  创建一个 ForkJoinPool 对象，名为 pool。
        ForkJoinPool pool = new ForkJoinPool();

//18. Create a 创建一个 Task 对象来增加array的全部元素。构造函数的参数是：任务的名字 Task，array对象，和0 和10000来向这个任务表示要处整个array.
        MyTask task = new MyTask("Task", array, 0, array.length);

//19.  使用 execute() 方法发送任务给池。
        pool.invoke(task);

//20. 使用 shutdown() 方法关闭池。
        pool.shutdown();

//21. 在操控台写个信息表明程序结束。
        System.out.printf("Main: End of the program.\n");
    }

}