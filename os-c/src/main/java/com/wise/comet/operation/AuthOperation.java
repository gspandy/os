package com.wise.comet.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wise.bean.Constants;
import com.wise.bean.Proto;
import com.wise.service.chat.IAuthService;

import io.netty.channel.Channel;

/**
 * 认证操作
 */
@Component
public class AuthOperation extends AbstractOperation {

    private final Logger logger = LoggerFactory.getLogger(AuthOperation.class);

    @Value("${server.id}")
    private int serverId;
    @Autowired
    private IAuthService authService;

    @Override
    public Integer op() {
        return Constants.OP_AUTH;
    }

    @Override
    public void action(Channel ch, Proto proto) throws Exception {
        // connection auth
        setKey(ch, authService.auth(serverId, proto));
        
        // write reply
        proto.setOperation(Constants.OP_AUTH_REPLY);
        proto.setBody(null);
        ch.writeAndFlush(proto);

        logger.debug("auth ok");  
    }

}
