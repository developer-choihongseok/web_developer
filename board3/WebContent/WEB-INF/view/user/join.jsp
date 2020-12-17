<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title }</title>
<script defer type="text/javascript" src="/res/js/join.js"></script>
</head>
<body>	
	<div>
		<div>
			<form action="/join" method="post" id="joinfrm" onsubmit="return joinChk();">
				<div>
					<input type="text" name="user_id" placeholder="아이디 입력" required="required" autofocus="autofocus">
				</div>
				<div>
					<input type="password" name="user_pw" placeholder="비밀번호 입력" required="required">
				</div>
				<div>
					<input type="password" name="user_pw_chk" placeholder="비밀번호 확인" >
				</div>
				<div>
					<input type="text" name="nm" placeholder="이름 입력" required="required">
				</div>
				<!-- 레이블로 묶으면, 라디오 버튼을 직접 클릭하지 않아도 클릭이 된다. -->
				<div>
					성별 :
					<label>
						여성<input type="radio" name="gender" value="0" checked="checked">
					</label>
					<label>
						남성<input type="radio" name="gender" value="1">
					</label>
				</div>
				<div>
					<input type="text" name="ph" placeholder="휴대폰 번호 입력">
				</div>
				<div>
					<input type="submit" value="회원가입">
				</div>
			</form>
			
			<a href="/login">로그인 화면</a>
		</div>
	</div>
</body>
</html>