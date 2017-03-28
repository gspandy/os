package com.wise.comet.operation;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wise.bean.Constants;
import com.wise.bean.Proto;
import com.wise.service.chat.MsgService;

/**
 * 消息操作
 */
@Component
public class MessageOperation extends AbstractOperation {

    private final Logger logger = LoggerFactory.getLogger(MessageOperation.class);

    @Autowired
    private MsgService messageService;

    @Override
    public Integer op() {
        return Constants.OP_MESSAGE;
    }

    @Override
    public void action(Channel ch, Proto proto) throws Exception {
        checkAuth(ch);

        // receive a message
        messageService.receive(proto);

        // write message reply
        proto.setOperation(Constants.OP_MESSAGE_REPLY);
        proto.setBody(null);
        ch.writeAndFlush(proto);
    }

}
