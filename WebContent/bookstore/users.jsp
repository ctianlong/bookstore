<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/bookstore/commons/includeHead.jsp" %>
<title>用户交易查询</title>
</head>
<body>

	<center>
		<h4>用户交易查询</h4>
		<form action="servlet/userServlet?method=getUser" method="post">
			用户名：<input type="text" name="username" value="${param.username }"/>
			<input type="submit" value="查询"/>		
		</form>
		<hr>	
		
		<c:if test="${not empty requestScope.message }">
			<font color="red">${message }</font>
		</c:if>
		
		<c:if test="${not empty requestScope.user }">
				用户名：${user.username }<br><br>
		
			<table border="1" cellpadding="10" cellspacing="0">
				<tr>
					<td>书名</td>
					<td>单价（元）</td>
					<td>数量</td>
					<td>总价（元）</td>
					<td>交易操作</td>
				</tr>
				<c:if test="${empty requestScope.user.trades }">
					<tr><td colspan="5">该用户没有交易记录</td></tr>
				</c:if>
				<c:if test="${not empty requestScope.user.trades }">
					<c:forEach items="${user.trades }" var="trade">
						<tr><td colspan="5">交易时间：${trade.time }</td></tr>
						<c:set value="${fn:length(trade.items) }" var="count"></c:set>
						<c:forEach items="${trade.items }" var="item" varStatus="state">
							<tr>
								<td>${item.book.title }</td>
								<td>${item.book.price }</td>
								<td>${item.quantity }</td>
								<c:if test="${state.first }">
								<td rowspan="${count }">${trade.tradeMoney }</td>
								<td rowspan="${count }"></td>
								</c:if>
							</tr>
						</c:forEach>
									
					</c:forEach>
				</c:if>
			
			</table>
			
		</c:if>
		
		
	</center>

</body>
</html>