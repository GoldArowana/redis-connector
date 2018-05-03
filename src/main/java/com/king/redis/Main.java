package com.king.redis;

import com.king.redis.connector.Client;

/**
 * @author 金龙
 * @date 2018/4/29 at 下午3:33
 */
public class Main {
    public static void main(String[] args) {
        Client client = new Client("140.143.135.207");
        String back = client.set("java", "python");
        System.out.println(back);
        String value = client.get("java");
        System.out.println(value);
    }
}
