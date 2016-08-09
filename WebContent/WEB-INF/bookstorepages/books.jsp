<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/bookstore/commons/includeHead.jsp" %>
<title>书本列表</title>
<script type="text/javascript" src="scripts/jquery-1.7.2.js"></script>
<%@ include file="/bookstore/commons/queryCondition.jsp" %>
<script type="text/javascript">
	
	$(function(){
		$(":submit[name='queryByPrice']").click(function(){
			var $minPrice = $(":text[name='minPrice']");
			minPrice = $.trim($minPrice.val());
			$minPrice.val(minPrice);
			var $maxPrice = $(":text[name='maxPrice']");
			maxPrice = $.trim($maxPrice.val());
			$maxPrice.val(maxPrice);
			
			var exp=/^[+]{0,1}(\d*)$|^[+]{0,1}(\d+\.\d+)$/;//匹配空或者零或者正实数
			var msg = "<font color='red'>输入错误，请重新输入</font>";
			var flag = false;
			
			if(exp.test(minPrice) && exp.test(maxPrice)){
				flag = true;
				if(minPrice != "" && maxPrice != ""){
					minPrice = parseFloat(minPrice);
					maxPrice = parseFloat(maxPrice);
					if(minPrice > maxPrice){
						flag = false;
					}
				}
			}
			
			if(!flag){
				$("#message").html(msg);
				return false;				
			}
			
		});
		
		$("#turnPage").click(function(){
			var pageNo = $(":text[name='pageNo']").val();
			pageNo = $.trim(pageNo);
			var exp = /^0*[1-9][0-9]*$/;
			if(!exp.test(pageNo)){
				var msg = "<font color='red'>页码格式错误</font>";
				$("#wrong").html(msg);
				return false;
			}
			var href = this.href + "&pageNo=" + pageNo;
			this.href = href;
		});
		
	});

</script>
</head>
<body>
	<center>
	<h4>书本列表</h4>
	<c:if test="${!empty requestScope.message }">
	<font color="red">${requestScope.message }</font>	
	</c:if>
	<br><br>
	
	<c:if test="${!empty sessionScope.ShoppingCart.books }">
		您的购物车中有 ${sessionScope.ShoppingCart.bookNumber } 本书， <a href="servlet/bookServlet?method=forwardPage&page=cart.jsp&pageNo=${bookpage.pageNo }">查看购物车</a>
	</c:if>
	<br><br>
	
	<form action="servlet/bookServlet?method=getBooks" method="post">
		价格区间：<input type="text" size="1" name="minPrice" value="${param.minPrice }"/>&nbsp;—&nbsp;
		<input type="text" size="1" name="maxPrice" value="${param.maxPrice }"/>
		<input type="submit" name="queryByPrice" value="查找"/>
	</form>
	<div id="message" style="height: 20px;"></div>
	<br>
	
	<c:if test="${empty bookpage }">
		<br><br>
		<font color="red">${msg }</font>
	</c:if>
	
	<c:if test="${!empty bookpage }">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>书名</th>
				<th>作者</th>
				<th>价格</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${bookpage.list }" var="book">
				<tr>
					<td><a href="servlet/bookServlet?method=getBook&pageNo=${bookpage.pageNo }&id=${book.id }">${book.title }</a></td>
					<td>${book.author }</td>
					<td>${book.price }</td>
					<td ><a href="servlet/bookServlet?method=addToCart&pageNo=${bookpage.pageNo }&id=${book.id }">加入购物车</a></td>
				</tr>
			</c:forEach>
		</table>
		
		<br><br>
		
		第${bookpage.pageNo }页&nbsp;/&nbsp;共${bookpage.totalPageNumber }页
		<br><br>
		
		<c:if test="${bookpage.hasPrev }">
			<a href="servlet/bookServlet?method=getBooks&pageNo=1">首页</a>&nbsp;
			<a href="servlet/bookServlet?method=getBooks&pageNo=${bookpage.prePage }">上一页</a>&nbsp;
		</c:if>
		
		<c:if test="${bookpage.hasNext }">
			<a href="servlet/bookServlet?method=getBooks&pageNo=${bookpage.nextPage }">下一页</a>&nbsp;
			<a href="servlet/bookServlet?method=getBooks&pageNo=${bookpage.totalPageNumber }">末页</a>&nbsp;
		</c:if>
		
		<c:if test="${bookpage.hasPrev or bookpage.hasNext }">
			第&nbsp;<input type="text" name="pageNo" size="1"/>&nbsp;页&nbsp;
			<a id="turnPage" href="servlet/bookServlet?method=getBooks">转到</a>&nbsp;<span id="wrong"></span>
		</c:if>	
	</c:if>
	
	</center>
</body>
</html>