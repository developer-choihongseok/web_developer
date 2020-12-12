<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>
<!-- 인터넷 캐시 때문에 바뀐 css,js가 바로 적용이 안될 수 있다. reload할 때 ver를 수정해주는 방법 등이 있다 -->
<link rel="stylesheet" href="/res/css/common.css?ver=4">
<script defer src="/res/js/common.js"></script>
</head>
<body>
	<div id="container">
		<header>
			<ul>
				<li><a href="/bList?typ=1">게임</a></li>
				<li><a href="/bList?typ=2">스포츠</a></li>
				<li><a href="/bList?typ=3">연예/방송</a></li>
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


