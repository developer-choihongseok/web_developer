package com.koreait.board3.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/ajaxFavorite")
public class BoardFavoriteSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");	// JSON 객체로 파싱되는 텍스트
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(BoardService.ajaxFavorite(request));
	}
}

/*
 response.getWriter()를 통해 PrintWriter를 얻기 위한 클래스 
*/