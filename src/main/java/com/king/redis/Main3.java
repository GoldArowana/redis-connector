package com.king.redis;


import java.io.*;
import java.net.Socket;

/**
 * @author 金龙
 * @date 2018/4/30 at 下午3:37
 */
public class Main3 {
    public static void main(String[] args) throws Exception {
        // socket
        Socket socket = new Socket("140.143.135.207", 6379);

        // oi流
        OutputStream os = socket.getOutputStream();
        // 为了解析'\r\n'方便, 我就用改为字符流了
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // 向redis服务器写
        os.write("get hello\r\n".getBytes());

        // 缓冲数组
        char[] chars = new char[1024];

        //从redis服务器读,到bytes中
        if (br.read() == '$') {
            System.out.println("这是一个批量回复哦! 怎么知道的呢? `$` 号就表示 '批量回复' 了");
            System.out.println("$ 后面会跟一个数字, 来表示正文内容的大小");
            // readLine直接能判断'\r' '\n'
            int len = Integer.parseInt(br.readLine());
            System.out.println("$后面跟着的数字是: " + len + ", 表示正文是" + len + "个字节, 接下来只要读取" + len + "个字节就好了");

            // 接下来只读取len个字符就ok了  (其实单位应该是字节, 但是我中途为了readLine省事, 改用了字符流, 个数是不变的)
            br.read(chars, 0, len);
            System.out.println("get到的结果是: " + new String(chars, 0, len) + ", 数一数真的是" + len + "个字符");
        }
    }
}
