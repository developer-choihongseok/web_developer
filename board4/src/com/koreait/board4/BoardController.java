package com.koreait.board4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.common.Utils;
import com.koreait.board4.db.BoardDAO;
import com.koreait.board4.model.BoardPARAM;

public class BoardController {
	
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int typ = Utils.getIntParam(request, "typ", 1);
		
		BoardPARAM param = new BoardPARAM();
		param.setTyp(typ);
		
		request.setAttribute("list", BoardDAO.selBoardList(param));
		
		Utils.forwardTemp("리스트", "temp/basic_temp", "board/bList", request, response);
	}
	
	public void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Utils.forwardTemp("글 등록", "temp/basic_temp", "board/bRegmod", request, response);
	}
	
	public void regProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
	
	public void modProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
}
