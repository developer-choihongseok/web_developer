package com.koreait.board4.model;

// model은 DB(= table)와 꼭 1:1 매칭!! (insert, update용)
public class UserModel {
	// 필드 : 값 저장용
	private int i_user;		// 회원 번호
	private String user_id;
	private String user_pw;
	private String salt;	// 비밀번호 암호화
	private String current_pw;
	private String chk_user_pw;
	private String nm;
	private int gender;
	private String ph;
	private String profile_img;
	private String r_dt;
	private String m_dt;
	
	public int getI_user() {
		return i_user;
	}
	public void setI_user(int i_user) {
		this.i_user = i_user;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getCurrent_pw() {
		return current_pw;
	}
	public void setCurrent_pw(String current_pw) {
		this.current_pw = current_pw;
	}
	public String getChk_user_pw() {
		return chk_user_pw;
	}
	public void setChk_user_pw(String chk_user_pw) {
		this.chk_user_pw = chk_user_pw;
	}
	public String getNm() {
		return nm;
	}
	public void setNm(String nm) {
		this.nm = nm;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getProfile_img() {
		return profile_img;
	}
	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}
	public String getR_dt() {
		return r_dt;
	}
	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}
	public String getM_dt() {
		return m_dt;
	}
	public void setM_dt(String m_dt) {
		this.m_dt = m_dt;
	}
}
