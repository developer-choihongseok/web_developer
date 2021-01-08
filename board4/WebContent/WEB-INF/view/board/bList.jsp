<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div>
	<c:if test="${loginUser != null }">
		<div>
			<a href="/board/reg.korea?typ=${param.typ == null ? 1 : param.typ} ">
				<button>글쓰기</button>
			</a>
		</div>
	</c:if>
	
	<c:choose>
		<c:when test="${fn:length(list) == 0 }">
			<div>게시글이 없습니다.</div>
		</c:when>
		<c:otherwise>
			<table border="1">
				<thead>
					<tr>
						<th width="70">번호</th>
						<th width="500">제목</th>
						<th width="100">조회수</th>
						<th width="180">작성일</th>
						<th width="100">작성자</th>
						<th width="100">좋아요</th>
					</tr>
				</thead>
				<!-- list는 BoardService의 selBoardList()의 list -->
				<c:forEach items="${list }" var="item">
					<tr class="pointer" onclick="clkArticle(${item.i_board })">
						<td align="center">${item.seq }</td>
						<td align="center">${item.title }</td>
						<td align="center">${item.hits }</td>
						<td align="center">${item.r_dt }</td>
						<td align="center">${item.writer_nm }</td>
						<td align="center">${item.favorite_cnt }</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	
	<!-- 페이징 -->
	<div class="pageContainer">
		<%-- 1부터 ${pageCnt }까지 자연수를 순차적으로 출력함. --%>
		<c:forEach begin="1" end="${pageCnt }" var="i">
			<span>
				<a href="/list?typ=${typ }&page=${i }">${i }</a>
			</span>
		</c:forEach>
	</div>
</div>
