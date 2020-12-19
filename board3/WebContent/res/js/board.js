// 글 번호 클릭 시, 해당 url로 이동
function clkArticle(i_board) {
	var url = `bDetail?i_board=${i_board}`; 
	location.href = url;	// 주소값 이동
}

// 지금은 사용 X, 혹시나 나중에 비속어가 있는지 체크하는 용도로 사용.
// 제목 혹은 내용의 값이 없을 경우, 알람창 표시.
function chk() {
	var frm = document.querySelector('#frm');	// 없어도 실행 되는데, 확실하게 해주기 위함!!
	
	if(chkEmptyEle(frm.title, '제목') || chkEmptyEle(frm.ctnt, '내용')){
		return false;
	}
}

// 삭제 버튼 클릭
function clkDel(i_board, typ){
	if(confirm('삭제 하시겠습니까?')){
		location.href = `bDel?i_board=${i_board}&typ=${typ}`;
	}
}