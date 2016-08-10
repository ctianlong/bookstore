<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/bookstore/commons/includeHead.jsp" %>
<title>书店</title>
</head>
<body>

	<center>
	<a href="servlet/bookServlet?method=getBooks">前台书店购物</a>
	<br><br>
	<a href="bookstore/users.jsp">后台记录管理</a>
	</center>

</body>
</html>