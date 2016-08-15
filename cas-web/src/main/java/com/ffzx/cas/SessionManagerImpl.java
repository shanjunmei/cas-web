package com.ffzx.cas;

import com.ffzx.cas.support.RedisManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vincent on 2016/8/13.
 */
@Component
public class SessionManagerImpl implements SessionManager {

    @Resource
    private RedisManager redisManager;

    @Override
    public String retrieveFromSession(String sessionKey) {
        redisManager.expire(sessionKey,SESSION_TIME_OUT);
        return redisManager.get(sessionKey);
    }

    @Override
    public void putSession(String key, String value) {
        redisManager.set(key,value,SESSION_TIME_OUT);
    }

    @Override
    public void remove(String key) {
        redisManager.remove(key);
    }
}
