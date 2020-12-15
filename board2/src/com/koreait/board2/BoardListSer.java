package com.koreait.board2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board2.common.Utils;
import com.koreait.board2.model.BoardVO;

@WebServlet("/bList")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// typ=정수
		// 따라서, typ 위치에 문자를 쓰거나 template.jsp에서 typ=1,2,3 이외의 다른 수를
		// 넣어서 들어오면 무조건 1번 페이지로 넘어가게 했다.
		int typ = Utils.getIntParam(request, "typ", 1);
		int page = Utils.getIntParam(request, "page", 1);
		
		System.out.println("typ : " + typ);
		
		BoardVO param = new BoardVO();
		param.setTyp(typ);
		param.setRowCntPerPage(5);	// 한 페이지 당 보일 게시물의 최대 개수(가져오는 데이터 개수)를 5개로 한정.
		
		request.setAttribute("pageCnt", BoardService.selPageCnt(param));
		request.setAttribute("typ", typ);
		request.setAttribute("list", BoardService.selBoardList(param, page));
		
		Utils.forward("전체 리스트", "bList", request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
