package com.wise.chat.comet.operation;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import com.wise.chat.core.bean.Constants;
import com.wise.chat.core.bean.Proto;

/**
 * 心跳
 */
@Component
public class HeartbeatOperation  extends AbstractOperation {

    @Override
    public Integer op() {
        return Constants.OP_HEARTBEAT;
    }

    @Override
    public void action(Channel ch, Proto proto) throws Exception {
        // write heartbeat reply
        proto.setOperation(Constants.OP_HEARTBEAT_REPLY);
        proto.setBody(null);
        ch.writeAndFlush(proto);
    }
}
