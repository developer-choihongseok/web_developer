<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>	<!-- Utils에 있는 title -->
</head>
<body>
	<h2>로그인 화면</h2>
	
	<div>
		<div>
			<form action="/login" method="post">
				<div>
					<input type="text" name="user_id" placeholder="아이디를 입력하세요." value="${user_id }" autofocus="autofocus">
				</div>
				<div>
					<input type="password" name="user_pw" placeholder="비밀번호를 입력하세요.">
				</div>
				<div>
					<input type="submit" value="로그인">
				</div>
			</form>
			
			<div style="color:red;">${msg }</div>	<!-- LoginSer.java의  msg -->
			
			<a href="/join">회원가입</a>
		</div>
	</div>
</body>
</html>