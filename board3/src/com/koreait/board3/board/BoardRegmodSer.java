package com.koreait.board3.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;

@WebServlet("/board/regmod")
public class BoardRegmodSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 상태가 아니면, 로그인으로 이동
		if(SecurityUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		
		// 쿼리 스트링에 typ 값을 받아온다.
//		int typ = Utils.getIntParam(request, "typ");
//		request.setAttribute("typ", typ);
		
//		int i_board = Utils.getIntParam(request, "i_board");	// 0: 글등록, 0이상이면 수정.
//		if(i_board > 0) {
//			request.setAttribute("data", BoardService.detail(request));
//		}
		
		request.setAttribute("data", BoardService.detail(request));
		// board.js 파일 가지고 온다.
		request.setAttribute("jsList", new String[]{"board"});
		
		Utils.forwardTemp("등록/수정", "temp/basic_temp", "board/bRegmod", request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		int i_board = Utils.getIntParam(request, "i_board");
		
		int result = BoardService.regMod(request);
		
		// 수정 > 디테일 페이지
		if(i_board > 0) {
			response.sendRedirect("detail?i_board=" + i_board);
		}else {	// 등록 > 리스트 페이지
			String typ = request.getParameter("typ");	// 문자열로 합칠려고 String으로 했다.
			response.sendRedirect("list?typ=" + typ);
		}
		*/
		
		// 위 전체를 한 줄로 대체한다!!
		response.sendRedirect(BoardService.regMod(request));
	}
}
