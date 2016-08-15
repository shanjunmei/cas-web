package com.ffzx.cas.support;

/**
 * Created by vincent on 2016/8/13.
 */
public interface RedisManager {

    public void set(String key, String value, Integer timeout);

    public void remove(String key);

    public String get(String key);

    public void expire(String key,Integer timeout);

}
