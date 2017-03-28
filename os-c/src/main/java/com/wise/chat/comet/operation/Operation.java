package com.wise.chat.comet.operation;

import com.wise.chat.core.bean.Proto;

import io.netty.channel.Channel;

/**
 * 操作类
 */
public interface Operation {

    Integer op();

    void action(Channel ch, Proto proto) throws Exception;

}
