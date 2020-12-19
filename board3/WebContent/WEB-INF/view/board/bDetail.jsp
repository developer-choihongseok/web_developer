<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<a href="list?typ=${data.typ }">돌아가기</a>
	
	<c:if test="${data.i_user == loginUser.i_user }">
		<button onclick="clkDel(${data.i_board}, ${data.typ });">삭제</button>
		
		<a href="bRegmod?i_board=${data.i_board }">
			<button>수정</button>
		</a>
	</c:if>
	
	<div style="margin-top: 20px;">
		<div>번호 : ${data.seq }</div>
		<div>조회수 : ${data.hits }</div>
		<div>작성자: ${data.nm }</div>
		<div>제목 : ${data.title }</div>
		<div>작성일 : ${data.r_dt }</div>
		<div>내용: ${data.ctnt }</div>
	</div>
	
	<div style="margin-top: 20px;">
		<%-- 댓글 쓰는 부분 --%>
		<div>
			<form action="/cmt" method="post">
				<input type="hidden" name="typ" value="${data.typ }">
				<input type="hidden" name="i_board" value="${data.i_board }">
				댓글: <input type="text" name="cmt_ctnt">
				<input type="submit" value="댓글쓰기">
			</form>
		</div>
		<%-- 댓글 리스트 --%>
		<div style="margin-top: 10px;">
			<table>
				<tr>					
					<th>댓글 목록</th>					
				</tr>
				<c:forEach items="${cmtList }" var="item">
					<tr>
						<td>${item.ctnt }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>

