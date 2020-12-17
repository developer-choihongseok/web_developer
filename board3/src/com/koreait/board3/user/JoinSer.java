package com.koreait.board3.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;

@WebServlet("/join")
public class JoinSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// 화면 띄우기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 상태일 경우, 메인으로 이동
		if(!SecurityUtils.isLogout(request)) {
			response.sendRedirect("/main");
			return;
		}
		
		Utils.forward("회원가입", "user/join", request, response);
	}

	// 실제로 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = UserService.join(request);
		
		response.sendRedirect("/login");
	}
}
