package com.koreait.board2.model;

public class BoardCmtVO {
	
	private int typ;
	private int i_cmt;		// 댓글 번호(= PK)
	private int i_board;	// 글 목록 테이블의 번호(= FK)
	private String ctnt;
	
	public int getTyp() {
		return typ;
	}
	public void setTyp(int typ) {
		this.typ = typ;
	}
	public int getI_cmt() {
		return i_cmt;
	}
	public void setI_cmt(int i_cmt) {
		this.i_cmt = i_cmt;
	}
	public int getI_board() {
		return i_board;
	}
	public void setI_board(int i_board) {
		this.i_board = i_board;
	}
	public String getCtnt() {
		return ctnt;
	}
	public void setCtnt(String ctnt) {
		this.ctnt = ctnt;
	}
}
