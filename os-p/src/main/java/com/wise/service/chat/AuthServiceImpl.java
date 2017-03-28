package com.wise.service.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wise.bean.Proto;
import com.wise.exception.ConnectionAuthException;
import com.wise.pb.Auth;
import com.wise.service.chat.AuthService;
import com.wise.service.config.CachingConfig;

/**
 * 受权
 * <p>
 * Created by Tony on 4/14/16.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Override
    public String auth(int serverId, Proto proto) throws ConnectionAuthException {
        Auth.AuthReq req;
        try {
            req = Auth.AuthReq.parseFrom(proto.getBody());
        } catch (InvalidProtocolBufferException e) {
            logger.error("invalid proto {} {}", proto, e.getMessage());
            throw new ConnectionAuthException("invalid proto", e);
        }
        cacheManager.getCache(CachingConfig.ONLINE).put(req.getUid(), serverId);

        logger.debug("auth serverId={}, userId={}, token={}", serverId, req.getUid(), req.getToken());
        return encodeKey(req.getUid());
    }

    @Override
    public boolean quit(int serverId, String key) {
        logger.debug("client quit uid={}, key={}", serverId, key);
        return true;
    }


    private String encodeKey(int uid) {
        return uid + "";
    }

}
