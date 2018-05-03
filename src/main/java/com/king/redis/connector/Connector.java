package com.king.redis.connector;

/**
 * @author 金龙
 * @date 2018/4/29 at 下午3:35
 */
public class Connector extends BaseConnector {

    public Connector(String host) {
        super(host);
    }
    public void set(String key, String value) {
        sendCommand("SET", key, value);
    }
}
