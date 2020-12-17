<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>
<link rel="stylesheet" href="/res/css/common.css?ver=4">
<c:forEach items="${jsList }" var="item">
	<!-- defer는 맨 밑에 놔둔 효과, async는 화면을 안 느려지게 하는 효과 -->
	<script defer type="text/javascript" src="/res/js/${item }.js"></script>
</c:forEach>
<script defer type="text/javascript" src="/res/js/common.js"></script>
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
			<jsp:include page="${page }"/>
		</section>
		
		<footer>
			
		</footer>
	</div>
</body>
</html>
<!-- Servlet에서 이 파일만 연다!!!! -->


