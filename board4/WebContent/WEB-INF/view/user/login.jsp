<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h2>로그인</h2>
	
<div>
	<div>
		<form action="/user/loginProc.korea" method="post">
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
		
		<a href="/user/join.korea">회원가입</a>
	</div>
</div>
