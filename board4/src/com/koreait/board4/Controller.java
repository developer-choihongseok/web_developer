package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.db.CommonDAO;

public class Controller {
	
	private static UserController uCont = new UserController();
	private static BoardController bCont = new BoardController();
	
	// 에러가 발생하면 호출
	public static void goToErr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{	
		String jsp = "/WEB-INF/view/err.jsp";
		request.getRequestDispatcher(jsp).forward(request, response);	// RequestDispatcher로 하면, 주소값이 안 바뀐다.
	}
	
	// 보통 navigation이 아니라 router로 많이 쓴다.
	public static void navigation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
//		request.setCharacterEncoding("UTF-8");	// 한글 설정 : web.xml에서 해도 된다.
		
		String[] urlArr = request.getRequestURI().split("/");	// / 기준으로 배열을 만들겠다는 의미.
//		System.out.println(urlArr.length);
		
		// 메뉴 리스트 가져오기
		ServletContext application = request.getServletContext();
		
		if(application.getAttribute("menus") == null) {
			application.setAttribute("menus", CommonDAO.selManageBoardList());
		}
		
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
			case "join.korea":
				uCont.join(request, response);
				return;
			case "joinProc.korea":
				uCont.joinProc(request, response);
				return;
			}
		break;
		
		case "board":
			switch(urlArr[2]) {
			case "list.korea":
				bCont.list(request, response);
				return;
			}
		break;
		}
		goToErr(request, response);
	}
}
