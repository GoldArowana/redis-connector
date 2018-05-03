package com.king.redis.exception;

import java.io.IOException;

public class RedisConnectorException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -2946266495682282677L;

    public RedisConnectorException(String message) {
	super(message);
    }

    public RedisConnectorException(IOException e) {
	super(e);
    }

	public RedisConnectorException(String message, Throwable cause) {
		super(message, cause);
	}
}
