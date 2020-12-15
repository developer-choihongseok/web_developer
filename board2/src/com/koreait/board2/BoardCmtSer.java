package com.koreait.board2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board2.common.Utils;
import com.koreait.board2.model.BoardCmtVO;

// 서블릿 클래스의 요청을 위한 URL매핑을 보다 쉽게 자바 클래스에서 설정할 수 있도록 제공되는 어노테이션.
@WebServlet("/cmt")
public class BoardCmtSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	// 댓글 입력 기능 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int typ = Utils.getIntParam(request, "typ");	// int typ = Integer.parseInt(request.getParameter("typ"));
		int i_board = Utils.getIntParam(request, "i_board");
		// 원하는 값을 얻기 위해서는 입력 양식의 name 속성값을 getParameter()의 매개변수로 기술.
		// getParameter() : 파라미터 값을 항상 문자열(String) 형태로만 얻어온다.
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
