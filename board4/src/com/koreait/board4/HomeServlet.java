package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.korea")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Controller.navigation(request, response);	// navigation에서 관리.
		} catch(Exception e) {
			Controller.goToErr(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Controller.navigation(request, response);
		} catch(Exception e) {
			Controller.goToErr(request, response);	// 여기서 에러가 터지면, throws로 간다.
		}
	}
}

/*
 .korea 붙은 것은 단계가 들어가든 안 들어가든 HomeServlet이 잡을 것이다.
*/
