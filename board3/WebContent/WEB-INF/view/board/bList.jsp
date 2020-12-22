<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div>
	<div>
		<!-- '/'를 안 붙이면, 이동 전 주소 끝에 있는 주소값만 변경,	'/'뺏을 경우 /board/board -->
		<a href="regmod?typ=${typ }">
			<button>글쓰기</button>
		</a>
	</div>
	
	<table>
		<thead>
			<tr>
				<th width="70">번호</th>
				<th width="500">제목</th>
				<th width="120">조회수</th>
				<th width="100">작성일</th>
				<th width="100">작성자</th>
			</tr>
		</thead>
		<!-- list는 BoardService의 selBoardList()의 list이다. -->
		<c:forEach items="${list }" var="item">
			<tbody>
				<tr class="pointer" onclick="clkArticle(${item.i_board })">
					<td align="center">${item.seq }</td>
					<td align="center">${item.title }</td>
					<td align="center">${item.hits }</td>
					<td align="center">${item.r_dt }</td>
					<td align="center">${item.nm }</td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
	
	<div class="pageContainer">
		<%-- 1부터 ${pageCnt }까지 자연수를 순차적으로 출력함. --%>
		<c:forEach begin="1" end="${pageCnt }" var="i">
			<span>
				<a href="/list?typ=${typ }&page=${i }">${i }</a>
			</span>
		</c:forEach>
	</div>
</div>