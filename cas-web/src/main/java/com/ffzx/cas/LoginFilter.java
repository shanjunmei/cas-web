package com.ffzx.cas;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ffzx.cas.support.PropertiesLoader;
import com.ffzx.cas.support.StringUtils;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(description = "登录过滤", urlPatterns = { "/*" })
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        System.out.println(getClass());
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		if(request instanceof HttpServletRequest&&response instanceof HttpServletResponse){
			HttpServletRequest httpRequest=(HttpServletRequest)request;
			HttpServletResponse httpResponse=(HttpServletResponse)response;
			
			String sessionId=getSessionId(httpRequest, httpResponse, false);
			SessionManager sessionManager=ApplicationContextHelper.getBean(SessionManager.class);
			String ticket=request.getParameter("ticket");
			if(StringUtils.isBlank(ticket)){
				ticket=sessionManager.retrieveFromSession(SessionManager.CLIENT_SESSION_KEY_PREFIX+sessionId);
			}
			if(StringUtils.isNotBlank(ticket)){//server  login
				String auth=sessionManager.retrieveFromSession(SessionManager.SERVER_SESSION_KEY_PREFIX+ticket);
			}else{
				String casBase=PropertiesLoader.getProperty("cas.base")+"/login.jsp";
				String webBase=PropertiesLoader.getProperty("web.base");
				casBase=urlParameterAdd(casBase, webBase);
				httpResponse.sendRedirect(casBase);
				return;
			}
			
			
			
		}
	
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

    private String getSessionId(HttpServletRequest request, HttpServletResponse response,boolean requireNew) {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookName = cookie.getName();
                String cookValue = cookie.getValue();
                if(SessionManager.CLIENT_SESSION_KEY.equals(cookName)){
                    sessionId=cookValue;
                }
                System.out.println(cookName + " = " + cookValue);
            }
        }
        if(requireNew|StringUtils.isBlank(sessionId)){
            sessionId= UUID.randomUUID().toString().replace("-","");
            Cookie cookie=new Cookie(SessionManager.CLIENT_SESSION_KEY,sessionId);

            response.addCookie(cookie);
        }
        return sessionId;
    }
    
    private String urlParameterAdd(String url,String service) {
    	url=url+"?service="+service;
        return url;
    }
}
