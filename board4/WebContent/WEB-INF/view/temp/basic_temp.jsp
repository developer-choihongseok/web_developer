<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<link rel="stylesheet" href="/res/css/common.css?ver=11">
<link rel="stylesheet" href="/res/css/board.css?ver=8">
<!-- js 파일을 배열로 전달 -->
<c:forEach items="${jsList}" var="item">
	<script defer src="/res/js/${item}.js?ver=16"></script>
</c:forEach>
<!-- /를 적으면 절대 경로를 적어줘야 한다! -->
<script defer src="/res/js/common.js"></script>
</head>
<body>
	<div id="container">
		<header>
			<ul>
				<c:if test="${loginUser == null }">
					<li><a href="/user/login.korea">로그인</a>
				</c:if>
				<c:if test="${loginUser != null }">
					<li>${loginUser.nm }님 환영합니다.</li>
					<li><a href="/user/logout.korea">로그아웃</a></li>
				</c:if>
				<!-- TODO : 메뉴 뿌리기 -->
				<c:forEach items="${menus }" var="item">
					<li class="${item.typ == param.typ ? 'selectedBoard' : '' }">
						<a href="/board/list.korea?typ=${item.typ }">
							${item.nm }
						</a>
					</li>
				</c:forEach>
				<c:if test="${loginUser != null }">
					<li><a href="/user/profile.korea">프로필</a></li>
					<li><a href="/user/changePw.korea">비밀번호 변경</a></li>
				</c:if>
			</ul>
		</header>
		
		<section>
			<jsp:include page="${page }"/>	<!-- Utils에 있는 page -->
		</section>
		
		<footer>
			
		</footer>
	</div>
</body>
</html>

<!-- defer는 먼저 페이지 로드 후 연결 시킨다는 것(즉,맨 밑에 놔둔 효과), async는 화면을 안 느려지게 하는 효과 -->

