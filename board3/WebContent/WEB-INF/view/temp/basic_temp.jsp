<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<link rel="stylesheet" href="/res/css/common.css?ver=5">
<link rel="stylesheet" href="/res/css/board.css?ver=5">
<!-- js 파일을 배열로 전달 -->
<c:forEach items="${jsList }" var="item">
	<script defer src="/res/js/${item }.js?ver=17"></script>
</c:forEach>
<!-- /를 적으면 절대 경로를 적어줘야 한다! -->
<script defer src="/res/js/common.js"></script>
</head>
<body>
	<div id="container">
		<header>
			<ul>
				<!-- loginUser는 UserService.login에서 정해준다. -->
				<li><a href="/main">${loginUser.nm }님 환영합니다!!</a></li>
				<li><a href="/logout">로그아웃</a></li>
				<li><a href="/board/list?typ=1">게임</a></li>
				<li><a href="/board/list?typ=2">스포츠</a></li>
				<li><a href="/board/list?typ=3">연예/방송</a></li>
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

<!-- Servlet에서 이 파일만 연다!!!! -->
<!-- defer는 먼저 페이지 로드 후 연결 시킨다는 것(즉,맨 밑에 놔둔 효과), async는 화면을 안 느려지게 하는 효과 -->

