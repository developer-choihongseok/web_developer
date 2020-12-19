<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
  
<h1>${data == null ? '글 등록' : '글 수정' }</h1>

<form action="/bRegmod" method="post" id="frm" onsubmit="return chk();">
	<input type="hidden" name="typ" value="${typ }">
	<input type="hidden" name="i_board" value="${data.i_board }">
	
	<div>
		제목: <input type="text" name="title" value="${data.title }">
	</div>
	<div>
		내용: <textarea name="ctnt" rows="5" cols="50">${data.ctnt }</textarea>
	</div>
	<div>
		<input type="submit" value="${data == null ? '글 등록' : '글 수정' }">
	</div>
</form>

<!-- submit버튼을 누르면 onsubmit이 실행되어 chk()함수가 실행 -->
<script>
	function chk() {
		if(chkEmptyEle(frm.title, '제목') || chkEmptyEle(frm.ctnt, '내용')){
			return false;
		}
	}
	// /bRegmod의 doPost() -> err
	<c:if test="${err != null }">
		alert('${err }');
	</c:if>
</script>