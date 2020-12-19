package com.koreait.board3.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.model.BoardSEL;

@WebServlet("/board/bDetail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 상태가 아니면, 로그인으로 이동
		if(SecurityUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		
		// board.js 파일 가지고 온다.
		request.setAttribute("jsList", new String[]{"board"});
		
		BoardSEL data = BoardService.detail(request);
		request.setAttribute("data", data);
		
		Utils.forwardTemp(data.getTitle(), "temp/basic_temp", "board/bDetail", request, response);
	}
}
