<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/bookstore/commons/includeHead.jsp" %>
<title>购物车</title>
<script type="text/javascript" src="scripts/jquery-1.7.2.js"></script>
<%@ include file="/bookstore/commons/queryCondition.jsp" %>
<script type="text/javascript">

	$(function(){
		$(".delete").click(function(){
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			var flag = confirm("确定要删除" + title + "的信息吗?");
			return flag;
		});
		
		$(".clear").click(function(){
			var flag = confirm("确定要清空购物车吗?");
			return flag;
		});
		
		$(".cash").click(function(){
			var flag = confirm("确定要结账吗?");
			return flag;
		});
		
		$(":text").change(function(){
			var quantityVal = $.trim(this.value);
			var flag = false;
			var reg = /^\d+$/g;
			if(reg.test(quantityVal)){
				quantityVal = parseInt(quantityVal);
				flag = true;				
			}
			
			if(!flag){
				alert("输入的数量不合法");
				$(this).val($(this).attr("oldQuantity"));
				return;
			}
			
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			
			if(quantityVal == 0){
				flag = confirm("确定要删除" + title + "的信息吗?");
				if(flag){
					var $a = $tr.find("td:last a:first");
					window.location.href = $a.attr("href");
				}else{
					$(this).val($(this).attr("oldQuantity"));
				}
				return;
			}
			
			flag = confirm("确定要修改" + title + "的数量吗?");
			
			if(!flag){
				$(this).val($(this).attr("oldQuantity"));
				return;
			}
			
			var url = "BookServlet";
			var idVal = $.trim(this.name);
			var args = {"method":"updateItemQuantity", "id":idVal, "quantity":quantityVal, "time":new Date()};
			$.post(url, args, function(data){
				var bookNumber = data.bookNumber;
				var totalMoney = data.totalMoney;
				$("#bookNumber").html(bookNumber);
				$("#totalMoney").html("¥&nbsp;" + totalMoney);
			},"JSON");
			
		});
		
	});

</script>
</head>
<body>

	<center>
	
		<h4>购物车</h4>
		<c:if test="${(empty sessionScope.ShoppingCart) or ((not empty sessionScope.ShoppingCart) and sessionScope.ShoppingCart.noBook) }">
			<font color="red">购物车为空</font>
		</c:if>
		<c:if test="${(not empty sessionScope.ShoppingCart) and (not sessionScope.ShoppingCart.noBook) }">
			<table cellpadding="10">
				<tr>
					<td>书名</td>
					<td>数量</td>
					<td>价格</td>
					<td>&nbsp;</td>
				</tr>
				<c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
					<tr>
						<td>${item.book.title }</td>
						<td><input type="text" name="${item.book.id }" size="1" value="${item.quantity }" oldQuantity="${item.quantity }"/></td>
						<td>¥&nbsp;${item.book.price }</td>
						<td><a href="servlet/bookServlet?method=delete&pageNo=${param.pageNo }&id=${item.book.id }" class="delete">删除</a></td>
					</tr>
				</c:forEach>
				
				<tr>
					<td>总计</td>
					<td id="bookNumber">${sessionScope.ShoppingCart.bookNumber }</td>
					<td id="totalMoney">¥&nbsp;${sessionScope.ShoppingCart.totalMoney }</td>
					<td>
						<a href="servlet/bookServlet?method=forwardPage&page=cash.jsp" class="cash">结账</a>/
						<a href="servlet/bookServlet?method=clear&pageNo=${param.pageNo }" class="clear">清空</a>
					</td>
				</tr>
			</table>
		</c:if>
		<br><br>
		<a href="servlet/bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
		
	</center>

</body>
</html>