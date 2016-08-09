<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/bookstore/commons/includeHead.jsp" %>
<title>书本详细信息</title>
<script type="text/javascript" src="scripts/jquery-1.7.2.js"></script>
<%@ include file="/bookstore/commons/queryCondition.jsp" %>
</head>
<body>

	<center>
	
	<h4>书本详细信息</h4>
			
		<c:if test="${empty book }">
			<br><br>
			<font color="red">图书信息不存在</font>
			<br><br>
		</c:if>
		<c:if test="${!empty book }">
			<br><br>
			书名: ${book.title }
			<br><br>
			作者: ${book.author }
			<br><br>
			价格: ${book.price }
			<br><br>
			出版日期: ${book.publishingDate }
			<br><br>
			备注: ${book.remark }
			<br><br>
		</c:if>
		
		<a href="servlet/bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
		
		
	</center>

</body>
</html>