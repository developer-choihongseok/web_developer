'use strict';	// 에러 잡기가 수월하다!!

function chkEmptyEle(ele, eleNm) {
	if(ele.value == '') {
		alert(eleNm + '을(를) 입력해 주세요');
		ele.focus();
		return true;
	}
	return false;
}