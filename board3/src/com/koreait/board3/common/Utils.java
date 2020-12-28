package com.koreait.board3.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 매번 사용할 때 마다 디폴트 값으로 0을 입력 안하게 하기 위해서 구현!!
public class Utils {
	// basic_temp.jsp(= 공통페이지)를 이용해서 보여줄 때 쓴다.
	public static void forwardTemp(String title, String openPage
			, String innerPage, HttpServletRequest request
			, HttpServletResponse response) throws ServletException, IOException {		
		
		// 서브 페이지 경로 설정
		request.setAttribute("page", String.format("/WEB-INF/view/%s.jsp", innerPage));
		
		forward(title, openPage, request, response);
	}
	
	// basic_temp.jsp(= 공통페이지)를 제외한 모든 jsp 페이지 -> 로그인, 회원가입..
	public static void forward(String title, String openPage
			, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("title", title);
		
		String jsp = String.format("/WEB-INF/view/%s.jsp", openPage);
		request.getRequestDispatcher(jsp).forward(request, response);
	}
	
	// 통신 용도(jsp파일 <-> 서블릿 파일)
	public static int getIntParam(HttpServletRequest request, String key) {
		return getIntParam(request, key, 0);	// key값이 없을 때, 0을 리턴.
	}
	
	// 값을 못 받아올 때, 0을 받아오기 위한 메서드
	public static int getIntParam(HttpServletRequest request, String key, int defVal) {
		String param = request.getParameter(key);
		return parseStrToInt(param, defVal);	// 문자열을 숫자로 변환.
	}
	
	// 문자열을 숫자로 변환하는 메서드
	public static int parseStrToInt(String val) {
		return parseStrToInt(val, 0);	// val을 숫자로 변환.
	}
	
	// 변환 못하는게 들어오면, 에러가 나니까 defVal 값을 0으로 나오게 한다.
	public static int parseStrToInt(String val, int defVal) {
		try {
			return Integer.parseInt(val);	// 오류가 나지 않았을 때, 실행
		} catch(Exception e) {}				// 받은 문자열 값이 없거나 숫자로 받지 않을 때, 예외발생
		
		return defVal;	// 오류가 났을 때, 0을 리턴.
	}
}