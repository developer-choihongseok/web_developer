<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<a href="/board/list.korea?typ=${data.typ }">게시판으로 돌아가기</a>
	
	<c:if test="${data.i_user == loginUser.i_user }">
		<button onclick="clkDel(${data.i_board}, ${data.typ });">
			삭제
		</button>
		<a href="regmod?typ=${data.typ }&i_board=${data.i_board }">
			<button>수정</button>
		</a>
	</c:if>
	
	<div style="margin-top: 20px;">
		<div>번호 : ${data.seq }</div>
		<div>조회수 : ${data.hits }</div>
		<div>
			<span class="profile-td-nm">작성자: </span>
			<c:if test="${data.profile_img == null }">
				<div class="circular--landscape circular--size40">
					<img id="profileImg" src="/res/img/basic_profile.jpg">
				</div>
			</c:if>
			<c:if test="${data.profile_img != null }">
				<div class="circular--landscape circular--size40">
					<img id="profileImg" src="/res/img/${data.i_user }/${data.profile_img}">
				</div>
			</c:if>
			${data.writer_nm }
		</div>
		<div>제목 : ${data.title }</div>
		<div>작성일 : ${data.r_dt }</div>
		<div>내용: ${data.ctnt }</div>
	</div>
	
	<div style="margin-top: 20px;">
		<c:if test="${loginUser != null }">
			<%-- 댓글 쓰는 부분 --%>
			<div>
				<form action="cmt/reg" method="post">
					<input type="hidden" name="i_board" value="${data.i_board }">
					댓글: <input type="text" name="ctnt">
					<input type="submit" value="댓글쓰기"><br><br>
				</form>
			</div>
		</c:if>
		
		<%-- 댓글 리스트 --%>
		<div style="margin-top: 10px;">
			<strong>댓글 목록</strong><br>
			
			<table border="1">
				<tr>
					<th width="170">댓글</th>
					<th width="100">작성자</th>
					<th width="170">작성일</th>
					<th width="120">비고</th>		
				</tr>
				<c:forEach items="${cmtList }" var="item">	<!-- BoardService.java의 cmtList -->
					<tr>
						<td align="center" bgcolor="#ffeaa7">${item.ctnt }</td>
						<td align="center" bgcolor="#3498db">${item.user_nm }</td>
						<td align="center" bgcolor="#95a5a6">${item.r_dt }</td>
						<td align="center" bgcolor="#e84393">
							<c:if test="${item.i_user == loginUser.i_user }">
								<button onclick="clkCmtDel(${item.i_cmt}, ${data.i_board });">삭제</button>
						<%--	<a href="cmt/del?i_cmt=${item.i_cmt }&i_board=${data.i_board}">
									<button>삭제</button>
								</a> 	--%> 
								<button onclick="clkCmtMod(${item.i_cmt});">수정</button>
							</c:if>
						</td>
					</tr>
					<c:if test="${item.i_user == loginUser.i_user }">
						<tr id="mod_${item.i_cmt }" class="cmd_mod_form">
							<td colspan="4">
								<form action="cmt/mod" method="post">
									<input type="hidden" name="i_board" value="${data.i_board }">
									<input type="hidden" name="i_cmt" value="${item.i_cmt }">
									<input type="text" name="ctnt" value="${item.ctnt }">
									<input type="submit" value="수정">
							<!--	<input type="button" value="닫기" onclick="clkCmtClose(${item.i_cmt});"> -->
									<!-- 버튼은 기본 타입이 submit이다. 따라서, button을 줘야한다. -->
									<button type="button" onclick="clkCmtClose(${item.i_cmt});">닫기</button>
								</form>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>
	
	<c:if test="${loginUser != null }">
		<div id="favoriteContainer" is_favorite="${data.is_favorite}" onclick="toggleFavorite(${data.i_board });">
			<%-- ${data.is_favorite} --%>
			<c:choose>
				<c:when test="${data.is_favorite == 1 }">
					<i class="fas fa-thumbs-up"></i>
				</c:when>
				<c:otherwise>
					<i class="far fa-thumbs-up"></i>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>
</div>

<script>
	<c:if test="${msg != null}">
		alert('${msg}');
	</c:if>
</script>

