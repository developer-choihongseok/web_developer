package com.koreait.board3.board.cmt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.Utils;
import com.koreait.board3.model.BoardCmtSEL;

@WebServlet("/board/cmt/reg")
public class BoardCmtRegSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int i_board = Utils.getIntParam(request, "i_board");
//		
//		String ctnt = request.getParameter("ctnt");
//		
//		BoardCmtSEL param = new BoardCmtSEL();
//		param.setI_board(i_board);
//		param.setCtnt(ctnt);
		
		String url = BoardCmtService.reg(request);
		
		response.sendRedirect(url);
	}
}
