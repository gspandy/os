package com.wise.comet;

/**
 * 聊天服务接口
 */
public interface ChatServer {

    void start() throws Exception;

    void restart() throws Exception;

    void shutdown();

}
