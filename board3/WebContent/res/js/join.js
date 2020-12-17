function joinChk(){
	// joinfrm 아이디를 가진 요소를 찾는다.
	var joinfrm = document.querySelector('#joinfrm');
	
	var eleId = joinfrm.user_id;
	var reUserId = /^[A-Za-z0-9+]*$/;	// ^ : 시작, $ : 끝 , + 는 대/소 영문자, 숫자가 한번 이상 나온다는 의미
					
	if(!reUserId.test(eleId.value)){
		alert('아이디는 대/소 영문자와 숫자만 가능합니다.');
		eleId.focus();
		return false;
	}
	
	var pw = joinfrm.user_pw;
	var pw_chk = joinfrm.user_pw_chk;
	
	/* !== : 값과 변수 타입까지 고려 */
	if(pw.value !== pw_chk.value){
		alert('비밀번호가 맞지 않습니다.');
		pw.focus();
		return false;
	}
	
	var eleNm = joinfrm.nm;
	var reNm = /^[가-힣]*$/;
	
	if(!reNm.test(eleNm.value)){
		alert('이름은 한글만 가능합니다.');
		eleNm.focus();
		return false;
	}
}