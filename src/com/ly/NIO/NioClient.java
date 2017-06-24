package com.ly.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Lo__oY on 2017/6/24.
 */
public class NioClient {

    public static void main(String args[]){

        try {
            ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );

            SocketChannel sc = SocketChannel.open();
            Selector selector = Selector.open();
            sc.configureBlocking(false);
           if(sc.connect(new InetSocketAddress("127.0.0.1",9999))){
               sc.register(selector, SelectionKey.OP_READ);
               doWrite(sc);


           }else{
               sc.register(selector, SelectionKey.OP_CONNECT);

           }


            while(true){

                selector.select();

                Set selectedKeys = selector.selectedKeys();

                Iterator it = selectedKeys.iterator();

                while(it.hasNext()){

                    SelectionKey key = (SelectionKey) it.next();

                    if((key.readyOps() & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT){

                        SocketChannel temp = (SocketChannel) key.channel();
                      if(temp.finishConnect()) {
                            key.channel().register(selector, SelectionKey.OP_READ);

                            doWrite((SocketChannel) key.channel());
                        }

                    }else if((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){

                        SocketChannel socketChannel = (SocketChannel)key.channel();
                        int bytesEchoed = 0;
                        while(true){

                            echoBuffer.clear();
                            int r = socketChannel.read(echoBuffer);
                            if(r<=0){
                                break;
                            }

                            echoBuffer.flip();
                            sc.write(echoBuffer);
                            bytesEchoed += r;
                        }

                        System.out.println("Echoed" + bytesEchoed +"from"+socketChannel);

                        it.remove();

                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void doWrite(SocketChannel sc) throws IOException {

        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if(!writeBuffer.hasRemaining()){
            System.out.println("Send order 2 server succeed.    ");
        }
    }
}
