package com.wise.exception;

/**
 * 连接身份验证异常类
 */
public class ConnectionAuthException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 连接身份验证异常
     *
     * @param message 异常消息
     * @param cause   完整异常
     */
    public ConnectionAuthException(String message, Throwable cause) {
        super(message, cause);
    }

}
