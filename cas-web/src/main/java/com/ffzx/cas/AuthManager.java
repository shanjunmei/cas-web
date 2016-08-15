package com.ffzx.cas;

/**
 * Created by vincent on 2016/8/13.
 */
public interface AuthManager {

    public boolean validate(String userName, String password);

}
