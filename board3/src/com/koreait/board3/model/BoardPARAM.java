package com.koreait.board3.model;

// 페이징 처리에 쓰이고, 자주 쓰이는 변수를 한 군데에 모으는 클래스
public class BoardPARAM {
	
	private int i_board;
	private int typ;
	private int i_user;
	
	public int getI_board() {
		return i_board;
	}

	public void setI_board(int i_board) {
		this.i_board = i_board;
	}

	public int getTyp() {
		return typ;
	}

	public void setTyp(int typ) {
		this.typ = typ;
	}

	public int getI_user() {
		return i_user;
	}

	public void setI_user(int i_user) {
		this.i_user = i_user;
	}
	
	
}
