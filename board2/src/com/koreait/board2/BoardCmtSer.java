package com.koreait.board2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board2.common.Utils;
import com.koreait.board2.model.BoardCmtVO;
import com.koreait.board2.model.BoardVO;

@WebServlet("/cmt")
public class BoardCmtSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int typ = Utils.getIntParam(request, "typ");
		int i_board = Utils.getIntParam(request, "i_board");
		String ctnt = request.getParameter("cmt_ctnt");
		
		BoardCmtVO param = new BoardCmtVO();
		param.setTyp(typ);
		param.setI_board(i_board);
		param.setCtnt(ctnt);
		
		int result = BoardService.cmtIns(param);
		String err = "";
		if(result == 0) {
			err = "&err=1";
		}
		response.sendRedirect("/bDetail?typ=" + typ + "&i_board=" + i_board + err);
		
	}

}
