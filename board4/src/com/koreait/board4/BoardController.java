package com.koreait.board4;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.BoardDAO;
import com.koreait.board4.db.SQLInterUpdate;
import com.koreait.board4.model.BoardPARAM;
import com.koreait.board4.model.BoardSEL;

public class BoardController {
	
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int typ = Utils.getIntParam(request, "typ", 1);	// 기본값을 typ=1인 게시판으로 설정
		
		BoardPARAM param = new BoardPARAM();
		param.setTyp(typ);
		
		request.setAttribute("list", BoardDAO.selBoardList(param));
		request.setAttribute("jsList", new String[] {"board"});
		
		Utils.forwardTemp("리스트", "temp/basic_temp", "board/bList", request, response);
	}
	
	public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int i_board = Utils.getIntParam(request, "i_board");
		
		BoardPARAM param = new BoardPARAM();
		param.setI_board(i_board);
		param.setI_user(SecurityUtils.getLoginUserPK(request));
		
		BoardSEL data = BoardDAO.selBoard(param);
		request.setAttribute("data", data);
		
		Utils.forwardTemp(data.getTitle(), "temp/basic_temp", "board/bDetail", request, response);
	}
	
	public void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Utils.forwardTemp("글 등록", "temp/basic_temp", "board/bRegmod", request, response);
	}
	
	public void mod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Utils.forwardTemp("글 수정", "temp/basic_temp", "board/bRegmod", request, response);
	}
	
	public void regProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int typ = Utils.getIntParam(request, "typ");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		int writerI_user = SecurityUtils.getLoginUserPK(request);
		
		String sql = "INSERT INTO t_board "
				+ " (typ, seq, title, ctnt, i_user) "
				+ " SELECT "
				+ " typ, IFNULL(MAX(seq), 0) + 1, ?, ?, ?"
				+ " FROM t_board "
				+ " WHERE typ = ?";
		
		int result = BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setNString(1, title);
				ps.setNString(2, ctnt);
				ps.setInt(3, writerI_user);
				ps.setInt(4, typ);
			}
		});
		
		if(result == 0) {
			Controller.goToErr(request, response);
		}
		
		// 새로운 request가 만들어진다.
		response.sendRedirect("/board/list.korea?typ=" + typ);
		
		// 원래 쓰던 데이터가 그대로 남아서 가지고 온다.
//		request.getRequestDispatcher("/board/list.korea?typ=" + typ).forward(request, response);
	}
	
	public void modProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
}
