package com.koreait.board4;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.common.SecurityUtils;
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
		System.out.println(Arrays.toString(urlArr));	// 배열 내용 출력.
		
		// 메뉴 리스트 가져오기
		ServletContext application = request.getServletContext();
		
		if(application.getAttribute("menus") == null) {
			application.setAttribute("menus", CommonDAO.selManageBoardList());	// basic_temp.jsp에서 쓰인다.
		}
		
		// /user/(login..).korea	-> / : 0번방, user : 1번방, login.korea : 2번방
		switch(urlArr[1]) {
		case "user":	// 로그인 영역
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
			case "logout.korea":
				uCont.logout(request, response);
				return;
			}
		break;
		
		case "board":	// 게시판 영역
			switch(urlArr[2]) {
			case "list.korea":
				bCont.list(request, response);
				return;
			case "detail.korea":
				bCont.detail(request, response);
				return;
			}
		break;
		}
		
		// 로그인이 되어있는 상태
		if(SecurityUtils.getLoginUserPK(request) > 0) {
			switch(urlArr[1]) {
			case "board":
				switch(urlArr[2]) {
				case "reg.korea":
					bCont.reg(request, response);
					return;
				case "mod.korea":
					bCont.mod(request, response);
					return;
				case "regProc.korea":	// 글 쓰기
					bCont.regProc(request, response);
					return;
				case "modProc.korea":	// 글 수정
					bCont.modProc(request, response);
					return;
				}
			}
		}
		goToErr(request, response);	// 로그인이 안되어 있다면, 에러 띄우기.
	}
}
