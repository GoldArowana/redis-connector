package com.king.redis.protocol;


import com.king.redis.exception.RedisConnectorException;
import com.king.redis.io.RedisInputStream;
import com.king.redis.io.RedisOutputStream;

import java.io.IOException;

import static com.king.redis.io.RedisOutputStream.CHARSET;


public final class Protocol {

    public void sendCommand(RedisOutputStream os, String name, String... args) {
        try {
            os.write('*');
            os.writeIntCrLf(args.length + 1);
            os.write('$');
            os.writeIntCrLf(name.length());
            os.writeAsciiCrLf(name);

            for (String str : args) {
                os.write('$');
                final int size = RedisOutputStream.utf8Length(str);
                os.writeIntCrLf(size);
                if (size == str.length())
                    os.writeAsciiCrLf(str);
                else {
                    os.writeUtf8CrLf(str);
                }
            }
            os.flush();
        } catch (IOException e) {
            throw new RedisConnectorException(e);
        }
    }

    private Object process(RedisInputStream is) {
        try {
            byte b = is.readByte();
            if (b == '$') {
                return processBulkReply(is);
            } else if (b == '+') {
                return processStatusCodeReply(is);
            } else {
                throw new RedisConnectorException("Unknown reply: " + (char) b);
            }
        } catch (IOException e) {
            throw new RedisConnectorException(e);
        }
    }

    private String processStatusCodeReply(RedisInputStream is) {
        return is.readLine();
    }

    private String processBulkReply(RedisInputStream is) {
        int len = Integer.parseInt(is.readLine());
        if (len == -1) {
            return null;
        }
        byte[] read = new byte[len];
        int offset = 0;
        try {
            while (offset < len) {
                offset += is.read(read, offset, (len - offset));
            }
            // read 2 more bytes for the command delimiter
            is.readByte();
            is.readByte();
        } catch (IOException e) {
            throw new RedisConnectorException(e);
        }

        return new String(read, CHARSET);
    }

    public Object read(RedisInputStream is) {
        return process(is);
    }
}