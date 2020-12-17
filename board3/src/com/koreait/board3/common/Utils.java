package com.koreait.board3.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board3.model.UserModel;

public class Utils {
	
	// Template.jsp(= 공통페이지)를 이용해서 보여줄 때 쓴다.
	public static void forwardTemp(String title, String openPage
			, String innerPage, HttpServletRequest request
			, HttpServletResponse response) throws ServletException, IOException {		
		
		// 서브 페이지 경로 설정
		request.setAttribute("page", String.format("/WEB-INF/view/%s.jsp", innerPage));
		
		forward(title, openPage, request, response);
	}
	
	// Template.jsp(= 공통페이지)를 제외한 모든 jsp 페이지 -> 로그인, 회원가입..
	public static void forward(String title, String openPage
			, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("title", title);
		
		String jsp = String.format("/WEB-INF/view/%s.jsp", openPage);
		request.getRequestDispatcher(jsp).forward(request, response);
	}
	
	// 
	public static int getIntParam(HttpServletRequest request, String key) {
		return getIntParam(request, key, 0);
	}
	
	// 
	public static int getIntParam(HttpServletRequest request, String key, int defVal) {
		String param = request.getParameter(key);
		return parseStrToInt(param, defVal);
	}
	
	// 
	public static int parseStrToInt(String val) {
		return parseStrToInt(val, 0);
	}
	
	// 
	public static int parseStrToInt(String val, int defVal) {
		try {
			return Integer.parseInt(val);	// 오류가 나지 않았을 때, gender.
		} catch(Exception e) {}
		
		return defVal;	// 오류가 났을 때, defVal 값.
	}
}