<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
  
<h1>${data == null ? '글 등록' : '글 수정' }</h1>

<form action="bRegmod" method="post" id="frm">
	<input type="hidden" name="typ" value="${typ }">
	<input type="hidden" name="i_board" value="${data.i_board }">
	
	<div>
		제목: <input type="text" name="title" value="${data.title }" required="required">
	</div>
	<div>
		내용: <textarea name="ctnt" rows="5" cols="50" required="required">${data.ctnt }</textarea>
	</div>
	<div>
		<input type="submit" value="${data == null ? '글 등록' : '글 수정' }">
	</div>
</form>

<script>
	<c:if test="${err != null }">
		alert('${err }');
	</c:if>
</script>