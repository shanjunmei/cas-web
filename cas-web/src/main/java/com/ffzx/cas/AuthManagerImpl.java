package com.ffzx.cas;

import com.ffzx.cas.support.DatabaseManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by vincent on 2016/8/13.
 */
@Component
public class AuthManagerImpl implements AuthManager {
    @Resource
    private DatabaseManager databaseManager;

    public AuthManagerImpl(){
        System.out.println(getClass());
    }

    @Override
    public boolean validate(String userName, String password) {
        int count = databaseManager.count("select count(1) from sys_user where login_name='" + userName + "' and password='" + password + "'");
        return count > 0;
    }
}
