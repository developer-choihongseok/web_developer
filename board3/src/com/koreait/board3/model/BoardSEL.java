package com.koreait.board3.model;

// 게시판 목록에 칼럼을 추가해서 보여줄 때, 여기에 필드를 추가해서 사용.
public class BoardSEL extends BoardModel{
	
	private String nm;	// 작성자

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}
	
}
