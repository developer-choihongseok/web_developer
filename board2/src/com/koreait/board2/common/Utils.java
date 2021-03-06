package com.koreait.board2.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	
	public static void forward(String title, String target, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// 메인 페이지 경로 설정
		String jsp = "/WEB-INF/jsp/temp/template.jsp";
		// 서브 페이지 경로 설정
		request.setAttribute("page", String.format("/WEB-INF/jsp/%s.jsp", target));
		request.setAttribute("title", title);
		// forward 방식은 서버 상에서 페이지가 이동된다.
		// 즉, 요청 당시의 현재 페이지에 대한 URL만 나타난다!!
		request.getRequestDispatcher(jsp).forward(request, response);
	}
	
	public static void forwardErr(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		forward("에러발생", "err", request, response);
	}
	
	public static int getIntParam(HttpServletRequest request, String key) {
		return getIntParam(request, key, 0);
	}
	
	public static int getIntParam(HttpServletRequest request, String key, int defVal) {
		String param = request.getParameter(key);
		return parseStrToInt(param, defVal);
	}
	
	public static int parseStrToInt(String val) {
		return parseStrToInt(val, 0);
	}
	
	public static int parseStrToInt(String val, int defVal) {
		try {
			return Integer.parseInt(val);	// 오류가 나지 않았을 때, typ
		} catch(Exception e) {}
		
		return defVal;	// 오류가 났을 때 defVal값을 받는다.
	}
}