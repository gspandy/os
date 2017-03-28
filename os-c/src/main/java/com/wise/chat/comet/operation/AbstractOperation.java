package com.wise.chat.comet.operation;

import com.wise.chat.comet.bean.Constants;
import com.wise.chat.comet.exception.NotAuthException;

import io.netty.channel.Channel;

/**
 * 操作类抽象方法
 */
public abstract class AbstractOperation implements Operation {

    protected String getKey(Channel ch) {
        return ch.attr(Constants.KEY_USER_ID).get();
    }

    protected void setKey(Channel ch, String key) {
        ch.attr(Constants.KEY_USER_ID).set(key);
    }

    protected void checkAuth(Channel ch) throws NotAuthException {
        if (!ch.hasAttr(Constants.KEY_USER_ID)) {
            throw new NotAuthException();
        }
    }

}
