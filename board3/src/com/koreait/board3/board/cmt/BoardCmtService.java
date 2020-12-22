package com.koreait.board3.board.cmt;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.BoardCmtDAO;
import com.koreait.board3.db.SQLInterUpdate;
import com.koreait.board3.model.BoardCmtSEL;
import com.koreait.board3.model.BoardPARAM;

public class BoardCmtService {
	// 댓글 목록 확인
	public static List<BoardCmtSEL> selBoardcmtList(BoardPARAM p){
		return BoardCmtDAO.selCmtList(p);
	}
	
	// 댓글 쓰기
	public static String reg(HttpServletRequest request) {
		
		int i_board = Utils.getIntParam(request, "i_board");
		int i_user = SecurityUtils.getLoginUserPK(request);
		String ctnt = request.getParameter("ctnt");
		
		String sql = " INSERT INTO t_board_cmt "
				+ " (i_board, i_user, ctnt) "
				+ " VALUES(?, ?, ?)";
		
		
		int result = BoardCmtDAO.executeUpdate(sql, new SQLInterUpdate() {
			// 인터페이스를 객체화 한 것이 아니라, 구현한 것이다!!
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
				ps.setInt(2, i_user);
				ps.setString(3, ctnt);
			}
		});
		
		// ../ : 한 칸 올라가는 방법
		// 아니면  /board/detail 이렇게 해야한다.
		String url = "../detail?i_board=" + i_board;
		
		if(result != 1) {
			url += "&err=1";
		}
		return url;
	}
	
	// 댓글 삭제
	public static String del(HttpServletRequest request) {
		
		String i_board = request.getParameter("i_board");
		int i_cmt = Utils.getIntParam(request, "i_cmt");
		int i_user = SecurityUtils.getLoginUserPK(request);
		
		String sql = " DELETE FROM t_board_cmt "
				+ " WHERE i_cmt = ? "
				+ " AND i_user = ?";
	
		BoardCmtDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_cmt);
				ps.setInt(2, i_user);
			}
		});

		return "../detail?i_board=" + i_board;
	}
}
