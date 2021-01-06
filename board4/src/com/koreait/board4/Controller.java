package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller {
	
	private static UserController uCont = new UserController();
	
	// 에러가 발생하면 호출
	public static void goToErr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		String jsp = "/WEB-INF/view/err.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);	// RequestDispatcher로 하면, 주소값이 안 바뀐다.
	}
	
	
	public static void navigation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		System.out.println(request.getRequestURI());	// / 기준으로 뜯을 것이다. 아래 참고!
		
		String[] urlArr = request.getRequestURI().split("/");	// / 기준으로 배열을 만들겠다는 의미.
//		System.out.println(urlArr.length);
		
		// /user/(login..).korea	-> / : 0번방, user : 1번방, login.korea : 2번방
		switch(urlArr[1]) {
		case "user":
			switch(urlArr[2]) {
			case "login.korea":
				uCont.login(request, response);
				return;
			case "loginProc.korea":	// 매핑 방식
				uCont.loginProc(request, response);
				return;
			}
		break;
		
		case "board":
			
			break;
		}
		goToErr(request, response);
	}
}
