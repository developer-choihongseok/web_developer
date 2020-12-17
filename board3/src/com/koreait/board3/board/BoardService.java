package com.koreait.board3.board;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.BoardDAO;
import com.koreait.board3.db.SQLInterUpdate;
import com.koreait.board3.model.BoardPARAM;

// BoardService를 이용한다는 것은 로그인이 된 상태!!
public class BoardService {
	
	public static void selBoardList(HttpServletRequest request) {
		
		int typ = Utils.getIntParam(request, "typ");
		
		BoardPARAM p = new BoardPARAM();
		p.setTyp(typ);
		
		request.setAttribute("list", BoardDAO.selBoardList(p));
	}
	
	public static int regMod(HttpServletRequest request) {
		
		int i_board = Utils.getIntParam(request, "i_board");
		int typ = Utils.getIntParam(request, "typ");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		// 자주 쓰이기 때문에, SecurityUtils에 만들 것이다.
		int i_user = SecurityUtils.getLoginUserPK(request);
		
		// 글 등록
		// IFNULL: 해당 필드의 값이 NULL을 반환할 때, 다른 값으로 출력할 수 있도록 하는 함수.
		// 쿼리문에 IFNULL이 있는 이유: ifnull은 seq가 null이면 0이 나오니까 +1을 한다.
		// max값을 넣어주지 않으면 여러 값들이 들어가기 때문에, 한 값만 들어가야 한다.
		// select문 여러 줄을 한방에 넣을 수 잇다!! 원본은 변하지 않는다!!
		if(i_board == 0) {
			String sql = " INSERT INTO t_board "
					+ " (typ, seq, title, ctnt, i_user) "
					+ " SELECT "
					+ " ?, ifnull(max(seq), 0) + 1, ?, ?, ? "
					+ " FROM t_board "
					+ " WHERE typ = ?";
			
			BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
				
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, typ);
					ps.setString(2, title);
					ps.setString(3, ctnt);
					ps.setInt(4, i_user);
					ps.setInt(5, typ);
				}
			});
			
		}else {	// 글 수정
			
		}
		return 0;
	}
}
