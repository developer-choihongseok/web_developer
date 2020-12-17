package com.koreait.board3.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;

@WebServlet("/login")
public class LoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// 화면 띄우기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 상태일 경우, 메인으로 이동
		if(!SecurityUtils.isLogout(request)) {
			response.sendRedirect("/main");
			return;
		}
		
		Utils.forward("로그인", "user/login", request, response);
	}
	
	// 실제로 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = UserService.login(request);
		
		// 1 : 로그인 성공
		if(result == 1) {
			response.sendRedirect("/main");
			return;
		}
		
		// 1번이 아니였을 때, 전부 실행
		switch(result) {
		case 2:
			request.setAttribute("msg", "아이디를 확인해 주세요.");
			break;
		case 3:
			request.setAttribute("msg", "비밀번호를 확인해 주세요.");
			break;
		}
		
		// 로그인 실패 시, 입력한 아이디 값 유지
		String user_id = request.getParameter("user_id");
		request.setAttribute("user_id", user_id);
		
		doGet(request, response);

		System.out.println("result: " + result);
	}
}
