package com.ly.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Lo__oY on 2017/6/22.
 */
public class NioServer {

    public static void main(String args[]){


        try {

           ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );

            Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            InetSocketAddress socketAddress = new InetSocketAddress(9999);
            ssc.socket().bind(socketAddress);
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while(true){

                selector.select();

                Set selectedKeys = selector.selectedKeys();

                Iterator it = selectedKeys.iterator();

                while(it.hasNext()){

                    SelectionKey key = (SelectionKey) it.next();

                    if((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){

                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
                        SocketChannel sc = serverSocketChannel.accept();
                        System.out.println("连接成功");
                        sc.configureBlocking(false);
                        sc.register(selector,SelectionKey.OP_READ);
                        it.remove();
                    }else if((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){

                        SocketChannel sc = (SocketChannel)key.channel();
                        int bytesEchoed = 0;
                        while(true){

                            echoBuffer.clear();
                            int r = sc.read(echoBuffer);
                            if(r<=0){
                                break;
                            }

                            echoBuffer.flip();
                            sc.write(echoBuffer);
                            bytesEchoed += r;
                        }

                        System.out.println("Echoed" + bytesEchoed +"from"+sc);

                        it.remove();

                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
