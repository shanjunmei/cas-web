package com.ffzx.cas.support;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import javax.annotation.Resource;

/**
 * Created by vincent on 2016/8/13.
 */
@Component
public class RedisManagerImpl implements RedisManager {
    @Resource
    private Pool<Jedis> jedisPool;

    @Override
    public void set(String key, String value, Integer timeout) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        if (timeout != null) {
            jedis.expire(key, timeout);
        }
        jedis.close();
    }

    @Override
    public void remove(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    @Override
    public void expire(String key, Integer timeout) {
        Jedis jedis = jedisPool.getResource();
        jedis.expire(key,timeout);
        jedis.close();
    }
}
