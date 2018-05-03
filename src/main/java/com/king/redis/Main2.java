package com.king.redis;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 金龙
 * @date 2018/4/30 at 下午2:55
 */
public class Main2 {
    public static void main(String[] args) throws Exception {
        // socket
        Socket socket = new Socket("140.143.135.207", 5000);

        // oi流
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        // 向redis服务器写
        os.write("PING\r\n".getBytes());

        //从redis服务器读,到bytes中
        byte[] bytes = new byte[1024];
        if(is.read()=='+'){
            // to string 输出一下
            int len = is.read(bytes);
            System.out.println(new String(bytes,0,len));
        }
        // else if $
        // else if *
        // else
    }
}
