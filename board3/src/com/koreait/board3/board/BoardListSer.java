package com.koreait.board3.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;

@WebServlet("/board/list")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 상태가 아니면, 로그인으로 이동
		if(SecurityUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		
		// 쿼리 스트링에 typ 값을 받아온다.
		int typ = Utils.getIntParam(request, "typ");
		request.setAttribute("typ", typ);
		
		// board.js 파일 가지고 온다.
		request.setAttribute("jsList", new String[]{"board"});	// String[] jsList = {"board"};
		
		BoardService.selBoardList(request);	// request.setAttribute("list", BoardService.selBoardList(request));
		
		Utils.forwardTemp("리스트", "temp/basic_temp", "board/bList", request, response);
	}
}
