package com.ffzx.cas;

/**
 * Created by vincent on 2016/8/13.
 */
public interface SessionManager {
	public final static String SESSION_KEY_PREFIX = "_auth";
	public final static String SERVER_SESSION_KEY_PREFIX = SESSION_KEY_PREFIX + ":" + "server:";
	public final static String CLIENT_SESSION_KEY_PREFIX = SESSION_KEY_PREFIX + ":" + "client:";
	public final static String CLIENT_SESSION_KEY = "server_session";

	public final static Integer SESSION_TIME_OUT = 1800;

	public String retrieveFromSession(String sessionKey);

	public void putSession(String key, String value);

	public void remove(String key);
}
