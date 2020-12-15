/* 
 Strict Mode 선언 방식 : 자바스크립트 코드에 더욱 엄격한 오류 검사를 적용.
	- 문법과 런타임 동작을 모두 검사하여, 실수를 에러로 변환하고, 즉시 수정할 수 있게 한다.
	- 보안에 강한 자바스크립트를 작성.
*/
'use strict';	// 전체 스크립트를 strict 모드로 설정.

function chkEmptyEle(ele, eleNm) {
	if(ele.value == '') {
		alert(eleNm + '을(를) 입력해 주세요');
		ele.focus();
		return true;
	}
	return false;
}