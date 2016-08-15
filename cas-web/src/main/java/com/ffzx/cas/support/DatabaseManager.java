package com.ffzx.cas.support;

import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 2016/8/13.
 */
public interface DatabaseManager {
    public List<Map> excute(String sql);

    public int count(String sql);
}
