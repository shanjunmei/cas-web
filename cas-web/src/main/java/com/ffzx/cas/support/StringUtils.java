package com.ffzx.cas.support;

/**
 * Created by vincent on 2016/8/14.
 */
public class StringUtils {
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
