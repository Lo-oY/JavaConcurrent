package com.ly.NIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Lo__oY on 2017/4/3.
 */
public class TestChannel {

    public static void main(String args[]){

        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            aFile = new RandomAccessFile("data/nio-data.txt","rw");
            fc = aFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(48);
            int byteRead = fc.read(buffer);
            while (byteRead != -1) {
                System.out.println("Read: " + byteRead);
                buffer.flip();

                while (buffer.hasRemaining()) {
                    System.out.print
                            ((char) buffer.get());
                }
                buffer.clear();

                byteRead = fc.read(buffer);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                aFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
