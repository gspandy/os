package com.wise.service.chat;

import com.wise.bean.Proto;

/**
 * 消息处理接口
 */
public interface IMsgService {

    /**
     * 接收消息
     *
     * @param proto 协议
     * @return 是否处理成功
     */
    boolean receive(Proto proto);
}
