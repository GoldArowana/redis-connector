package com.king.redis.connector;

import com.king.redis.exception.RedisConnectorException;
import com.king.redis.protocol.Protocol;
import com.king.redis.io.RedisInputStream;
import com.king.redis.io.RedisOutputStream;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author 金龙
 * @date 2018/4/29 at 下午3:36
 */
public class BaseConnector {
    private String host;
    private int port = 5000;
    private Socket socket;
    private Protocol protocol = new Protocol();
    private RedisOutputStream outputStream;
    private RedisInputStream inputStream;
    private int pipelinedCommands = 0;
    private int timeout = 2000;

    public BaseConnector(String host) {
        super();
        this.host = host;
    }

    protected BaseConnector sendCommand(String name, String... args) {
        try {
            connect();
        } catch (UnknownHostException e) {
            throw new RedisConnectorException("Could not connect to redis-server", e);
        } catch (IOException e) {
            throw new RedisConnectorException("Could not connect to redis-server", e);
        }
        protocol.sendCommand(outputStream, name, args);
        pipelinedCommands++;
        return this;
    }

    public void connect() throws IOException {
        if (!isConnected()) {
            System.out.println("正在建立连接");
            socket = new Socket(host, port);
            socket.setSoTimeout(timeout);
            outputStream = new RedisOutputStream(socket.getOutputStream());
            inputStream = new RedisInputStream(socket.getInputStream());
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isBound() && !socket.isClosed()
                && socket.isConnected() && !socket.isInputShutdown()
                && !socket.isOutputShutdown();
    }

    protected String getStatusCodeReply() {
        pipelinedCommands--;
        return (String) protocol.read(inputStream);
    }

    public String getBulkReply() {
        pipelinedCommands--;
        return (String) protocol.read(inputStream);
    }
}