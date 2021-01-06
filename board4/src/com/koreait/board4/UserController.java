package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.UserDAO;
import com.koreait.board4.model.UserModel;

public class UserController {
	// 로그인 페이지 띄우는 용도 -> Get방식
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Utils.forwardTemp("로그인", "temp/basic_temp", "user/login", request, response);
	}
	
	// 로그인 처리 -> Post방식
	public void loginProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 에러 : 0, 로그인 성공 : 1, 아이디 없음 : 2, 비밀번호 틀림 : 3
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		UserModel p = new UserModel();
		p.setUser_id(user_id);
		p.setUser_pw(user_pw);
		
		UserModel loginUser = UserDAO.selUser(p);
		
		if(loginUser == null) {
			login(request, response);	// 이상이 생겼음.
			System.out.println("아이디 없음");
			return;
		}
		
		String encryptPw = SecurityUtils.getSecurePassword(user_pw, loginUser.getSalt());
		
		if(encryptPw.equals(loginUser.getUser_pw())) {
			response.sendRedirect("/board/list.korea");	// 이상 없음.
			System.out.println("로그인 성공");
		}else {
			login(request, response);
			System.out.println("비밀번호 틀림");
		}
	}
}
