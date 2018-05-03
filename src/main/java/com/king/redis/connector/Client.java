package com.king.redis.connector;

/**
 * @author 金龙
 * @date 2018/4/29 at 下午3:33
 */
public class Client {
    private Connector connector = null;

    public Client(String host) {
        connector = new Connector(host);
    }

    public String set(String key, String value) {
        connector.set(key, value);
        return connector.getStatusCodeReply();
    }

    public String get(String key) {
        connector.sendCommand("GET", key);
        return connector.getBulkReply();
    }
}
