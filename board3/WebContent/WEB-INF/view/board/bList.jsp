<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
	<div>
		<!-- '/'를 안 붙이면, 이동 전 주소 끝에 있는 주소값만 변경
			ex) '/'뺏을 경우 /board/board -->
		<a href="bRegmod?typ=${typ }">
			<button>글쓰기</button>
		</a>
	</div>
	
	<table>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>조회수</td>
			<td>작성일</td>
			<td>작성자</td>
		</tr>
			
		<c:forEach items="${list }" var="item">
			<tr class="pointer" onclick="clkArticle(${item.i_board })">
				<td>${item.seq }</td>
				<td>${item.title }</td>
				<td>${item.hits }</td>
				<td>${item.r_dt }</td>
				<td>${item.nm }</td>
			</tr>
		</c:forEach>
	</table>
	
	<div class="pageContainer">
		<%-- 1부터 ${pageCnt }까지 자연수를 순차적으로 출력함. --%>
		<c:forEach begin="1" end="${pageCnt }" var="i">
			<span>
				<a href="/bList?typ=${typ }&page=${i }">${i }</a>
			</span>
		</c:forEach>
	</div>
</div>