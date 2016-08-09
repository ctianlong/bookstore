<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(function(){
		$("a").each(function(){			
			var serializeVal = $(":hidden").serialize();
			var href = this.href + "&" + serializeVal;
			this.href = href;
		});
	});
</script>

<input type="hidden" name="minPrice" value="${param.minPrice }"/>
<input type="hidden" name="maxPrice" value="${param.maxPrice }"/>

