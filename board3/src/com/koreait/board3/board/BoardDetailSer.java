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

@WebServlet("/board/detail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 상태가 아니면, 로그인으로 이동
		if(SecurityUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		
		int err = Utils.getIntParam(request, "err");
		
		switch(err) {
		case 1:
			request.setAttribute("err", "댓글 내용이 너무 깁니다..");
			break;
			
		}
		
		// board.js 파일 가지고 온다.
		// basic_temp.jsp에서 forEach구문을 보면 배열로 만들어 놓은게 하나하나씩 쓸려고 배열로 만들었다.
		// 그리고 board.js파일을 적용시키고, 나중에 다른 .js파일을 만들 때마다 계속 추가할 것이다.
		request.setAttribute("jsList", new String[]{"board"});
		
		BoardSEL data = BoardService.detail(request);
		request.setAttribute("data", data);
		
		Utils.forwardTemp(data.getTitle(), "temp/basic_temp", "board/bDetail", request, response);
	}
}
