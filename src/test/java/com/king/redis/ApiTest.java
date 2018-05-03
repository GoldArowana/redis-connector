package com.king.redis;

import com.king.redis.connector.Client;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 金龙
 * @date 2018/4/30 at 上午11:06
 */
public class ApiTest {
    private static int CYCLE = 100;
    @Test
    public void con() {
        long start = System.currentTimeMillis();
        int counter = 0;
        for (int i = 0; i < CYCLE; i++) {
            Client client = new Client("140.143.135.207");
            String in = "bar"+i;
            String back = client.set("foo", in);
            String value = client.get("foo");
            if(in.equals(value)){
                System.out.println(counter++);
            }
        }

        System.out.println(System.currentTimeMillis() - start);
        System.out.println(counter);
    }
}
