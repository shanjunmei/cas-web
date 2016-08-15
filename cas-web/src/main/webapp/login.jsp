<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Login Page</title>
</head>
<body>
	<form action="login" method="post">
		<input type="hidden" name="service" value="<%=request.getParameter("service")%>">
		User:<input name="userName" /> 
		Password:<input name="password" /> 
		<input type="submit" value="登录" />
	</form>
</body>
</html>