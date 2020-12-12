package com.koreait.board2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board2.common.Utils;
import com.koreait.board2.model.BoardVO;

@WebServlet("/bRegmod")
public class BoardRegmodSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int i_board = Utils.getIntParam(request, "i_board");	// 0
		int typ = Utils.getIntParam(request, "typ");			// 0
		
		if(typ == 0) {	// 에러페이지로 보냄
			Utils.forwardErr(request, response);
			return;
		}
		System.out.println("i_board : " + i_board);
		
		String title = "글등록";
		
		if(i_board > 0) {	// 글 수정! request에다가 자료를 담을꺼에요.
			title = "글수정";
			BoardVO param = new BoardVO();
			param.setTyp(typ);
			param.setI_board(i_board);
			request.setAttribute("data", BoardService.selBoard(param));
		}
		request.setAttribute("typ", typ);
		Utils.forward(title, "bRegmod", request, response);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int typ = Utils.getIntParam(request, "typ");
		
		if(typ == 0) {
			request.setAttribute("err", "에러가 발생하였습니다.");
			doGet(request, response);	// 에러 발생 시 doGet()으로 이동.
			return;	 // 밑의 코드 실행 안 되도록 리턴문 작성.
		}
		// 0이면 등록 , 1이면 수정.
		int i_board = Utils.getIntParam(request, "i_board");
		
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		
		BoardVO param = new BoardVO();
		param.setTyp(typ);
		param.setI_board(i_board);
		param.setTitle(title);
		param.setCtnt(ctnt);
		
		int result = BoardService.regmod(param);
		
		if(result == 0) {
			request.setAttribute("err", "에러가 발생하였습니다.");
			doGet(request, response);
			return;
		}
		response.sendRedirect("/bDetail?typ=" + typ + "&i_board=" + param.getI_board());
	}

}
