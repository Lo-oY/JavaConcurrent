package com.ly;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Lo__oY on 2017/3/30.
 */
public class FileSearch implements Runnable{

    private String initPath;
    private String fileName;
    public FileSearch(String initPath,String fileName){

        this.initPath = initPath;
        this.fileName = fileName;
    }


    @Override
    public void run() {
        System.out.println("thrad -----------run()");
        File file = new File(initPath);
        if(file.isDirectory()){

            try{
                directoryProcess(file);
            }catch (InterruptedException ex){
                System.out.println("thread has been interrupted");
         //       ex.printStackTrace();
            }
        }
    }

    private void directoryProcess(File file) throws InterruptedException {

        File list[] = file.listFiles();

        if(list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                }
            }
        }


            throw new InterruptedException();


    }

    private void fileProcess(File file) throws InterruptedException {

        System.out.println(file.getAbsolutePath());
        if(file.getName().equals(fileName)){
            System.out.printf("%s : %s\n",Thread.currentThread().getName(),file.getAbsolutePath());
        }

        if(Thread.interrupted()){
            System.out.println("throw InterruptedException");
            throw new InterruptedException();
        }
    }


    public static void main(String args[]){

        FileSearch fs = new FileSearch("F:\\School\\it\\EnglishText","englishdemo.db");
        Thread thread = new Thread(fs);
        thread.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
