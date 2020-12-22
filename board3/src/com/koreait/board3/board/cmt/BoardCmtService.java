package com.koreait.board3.board.cmt;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.common.Utils;
import com.koreait.board3.db.BoardCmtDAO;
import com.koreait.board3.db.BoardDAO;
import com.koreait.board3.db.SQLInterUpdate;
import com.koreait.board3.model.BoardCmtModel;
import com.koreait.board3.model.BoardCmtSEL;
import com.koreait.board3.model.BoardPARAM;

public class BoardCmtService {
	// 댓글 목록 확인
	public static List<BoardCmtSEL> selBoardcmtList(HttpServletRequest request){
		int i_cmt = Utils.getIntParam(request, "i_cmt");
		
		BoardPARAM p = new BoardPARAM();
		p.setI_cmt(i_cmt);
		
		return BoardCmtDAO.selCmtList(p);
	}
	
	// 댓글 쓰기
	public static int cmtInsert(BoardCmtModel param) {
		String sql = "INSERT INTO t_board_cmt "
				+ " (i_board, i_user, ctnt) "
				+ " VALUES(?, ?, ?)";
		
		return BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getI_board());
				ps.setInt(2, param.getI_user());
				ps.setString(3, param.getCtnt());
			}
		});
	}
}
