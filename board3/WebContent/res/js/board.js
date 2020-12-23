// 글 번호 클릭 시, 해당 url로 이동
function clkArticle(i_board) {
	var url = `detail?i_board=${i_board}`; 
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
		location.href = `del?i_board=${i_board}&typ=${typ}`;
	}
}

// 댓글 삭제 버튼 클릭
function clkCmtDel(i_cmt, i_board){
	if(confirm('삭제 하시겠습니까?')){
		location.href = `cmt/del?i_cmt=${i_cmt}&i_board=${i_board}`;
	}
}

// 댓글에서 수정버튼 클릭 > 수정 FORM 나타나게 처리
function clkCmtMod(i_cmt){
	/*console.log('i_cmt : ' + i_cmt);	// 연결이 잘 되었는지 체크하는 것이 좋다!!*/
	var trForm = document.querySelector('#mod_' + i_cmt);
	// bDetail.jsp에서 61번째 줄에 class 엘리먼트만 유일하게 접근할 때, classList로 접근!!
	trForm.classList.remove('cmd_mod_form');
	
	console.log(trForm);
}

function clkCmtClose(i_cmt){
	var trForm = document.querySelector('#mod_' + i_cmt);
	trForm.classList.add('cmd_mod_form');	// push도 가능.
	
	console.log(trForm);
}