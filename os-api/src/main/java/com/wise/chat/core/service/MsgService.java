package com.wise.chat.core.service;

import com.wise.chat.core.bean.Proto;

/**
 * 消息处理接口
 */
public interface MsgService {

    /**
     * 接收消息
     *
     * @param proto 协议
     * @return 是否处理成功
     */
    boolean receive(Proto proto);
}
