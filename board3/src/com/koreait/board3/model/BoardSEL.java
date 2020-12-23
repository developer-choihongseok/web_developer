package com.koreait.board3.model;

// 게시판 목록에 칼럼을 추가해서 보여줄 때, 여기에 필드를 추가해서 사용!!
public class BoardSEL extends BoardModel{
	
	private String nm;	// 작성자
	private int is_favorite;

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public int getIs_favorite() {
		return is_favorite;
	}

	public void setIs_favorite(int is_favorite) {
		this.is_favorite = is_favorite;
	}
	
	
	
}
