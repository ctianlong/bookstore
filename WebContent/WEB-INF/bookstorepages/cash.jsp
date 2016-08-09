<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/bookstore/commons/includeHead.jsp" %>
<title>Insert title here</title>
</head>
<body>

	<center>
		<br><br>
		您一共买了 ${sessionScope.ShoppingCart.bookNumber } 本书
		<br>
		应付： ¥&nbsp; ${ sessionScope.ShoppingCart.totalMoney }
		<br><br>
		
		<c:if test="${not empty requestScope.errors }">
			<font color="red">${requestScope.errors }</font>
		</c:if>
		
		<form action="servlet/bookServlet?method=cash" method="post">
		
			<table cellpadding="10"> 
				<tr>
					<td>用户姓名:</td>
					<td><input type="text" name="username" value="${param.username }"/></td>
				</tr>
				<tr>
					<td>用户账号:</td>
					<td><input type="text" name="accountId" value="${param.accountId }"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="提交"/>&nbsp;&nbsp;
						<button type="button" onclick="window.location.href='${pageContext.request.contextPath }/servlet/bookServlet?method=forwardPage&page=cart.jsp'">取消</button>
					</td>
				</tr>
			</table>
		
		</form>
		
	
	</center>

</body>
</html>