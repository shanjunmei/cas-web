package com.ffzx.cas;

import com.ffzx.cas.support.PropertiesLoader;
import com.ffzx.cas.support.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public UserLoginServlet() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String sessionId = getSessionId(request, response,false);

           // String clientSessionId = request.getParameter("ticket");
            String service = request.getParameter("service");
            if(StringUtils.isBlank(service)){
                //FIXME
                service= PropertiesLoader.getProperty("cas.base")+"/login";
            }else{
               String uri= request.getRequestURI();
                StringBuffer url=request.getRequestURL();
                System.out.println("uri ="+uri);
                System.out.println("url ="+url);
            }
            String uri= request.getRequestURI();
            StringBuffer url=request.getRequestURL();
            System.out.println("uri ="+uri);
            System.out.println("url ="+url);

            String userName = request.getParameter("userName");
            String password = request.getParameter("password");


            //根据sessionId去redis去取对应的key
            //查询不到则执行登录流程
            if (!StringUtils.isBlank(sessionId)) {
                //登录后当前的系统访问
                SessionManager sessionManager = ApplicationContextHelper.getBean(SessionManager.class);
                String auth = sessionManager.retrieveFromSession(SessionManager.SERVER_SESSION_KEY_PREFIX + sessionId);
                if (!StringUtils.isBlank(auth)) {
                    //上一次登录有效期还未过
                    System.out.println("last login has't expire");
                    service = urlParameterAdd(sessionId, service);
                   // response.sendRedirect(service);
                    System.out.println("redirect to:"+service);
                    return;
                } else {
                    //上一次登录有效期已过
                    System.out.println("last login had expire");
                }
            }

          /*  if (clientSessionId!=null&&!"".equals(clientSessionId)){
                //登录后另外系统访问
            }*/

            if (!StringUtils.isBlank(userName)) {
                //login
                AuthManager authManager = ApplicationContextHelper.getBean(AuthManager.class);
                if (authManager.validate(userName, password)) {
                   // sessionId=getSessionId(request,response,true);
                    SessionManager sessionManager = ApplicationContextHelper.getBean(SessionManager.class);
                    sessionManager.putSession(SessionManager.SERVER_SESSION_KEY_PREFIX + sessionId, userName);
                   /* if (clientSessionId != null) {
                        sessionManager.putSession(SessionManager.CLIENT_SESSION_KEY_PREFIX + clientSessionId, userName);
                    }*/
                    System.out.println("login success");
                    //redirect to service;
                } else {
                    System.out.println("login fail");
                    //redirect current page
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().append("Served at:").append(request.getContextPath());
    }

    private String urlParameterAdd(String sessionId, String service) {
        service=service+"?ticket="+sessionId;
        return service;
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

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
