package com.koreait.board3.board;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.board.cmt.BoardCmtService;
import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.BoardDAO;
import com.koreait.board3.db.SQLInterUpdate;
import com.koreait.board3.model.BoardPARAM;
import com.koreait.board3.model.BoardSEL;

// BoardService를 이용한다는 것은 로그인이 된 상태!!
public class BoardService {
	// 글 등록 & 글 수정
	public static String regMod(HttpServletRequest request) {
		
		int i_board = Utils.getIntParam(request, "i_board");
		int typ = Utils.getIntParam(request, "typ");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		int i_user = SecurityUtils.getLoginUserPK(request);
		
		// 글 등록
		// IFNULL: 해당 필드의 값이 NULL을 반환할 때, 다른 값으로 출력할 수 있도록 하는 함수
		//				-> c.f) oracle : nvl함수
		// select문 해석: seq가 null이면 0이므로 +1을 한다. seq가 null이 아니면 max(seq) + 1 값 출력.
		// max()를 넣어주지 않으면 여러 값들이 들어가기 때문에, 한 가지 값만 들어가야 한다.
		// select문 특징: 여러 줄을 한 방에 넣을 수 있다!! 단,원본은 변하지 않는다!!
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
			
			return "list?typ=" + typ;
					
		}else {	// 글 수정
			// i_user까지 해주어야 장난질을 막을 수 있다!!
			String sql = " UPDATE t_board "
					+ " SET title = ?, ctnt = ? "
					+ " WHERE i_board = ? "
					+ " AND i_user = ?";
			
			// 업데이트 되었으면 1, 문제가 생겼으면 0이 넘어간다.
			BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
					
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setString(1, title);
					ps.setString(2, ctnt);
					ps.setInt(3, i_board);
					ps.setInt(4, SecurityUtils.getLoginUserPK(request));
				}
			});
			
			return "detail?i_board=" + i_board;
		}
	}
	
	// 글 목록 확인
	public static void selBoardList(HttpServletRequest request) {
		int typ = Utils.getIntParam(request, "typ");
		
		BoardPARAM p = new BoardPARAM();
		p.setTyp(typ);
			
		request.setAttribute("list", BoardDAO.selBoardList(p));
	}
		
	// 글 목록 확인
//	public static List<BoardSEL> selBoardList(HttpServletRequest request){
//		int typ = Utils.getIntParam(request, "typ");
//			
//		BoardPARAM p = new BoardPARAM();
//		p.setTyp(typ);
//			
//		return BoardDAO.selBoardList(p);
//	}
	
	// 글 읽기
	public static BoardSEL detail(HttpServletRequest request) {
		int i_board = Utils.getIntParam(request, "i_board");
		
		if(i_board == 0) {
			return null;
		}
		
		BoardPARAM p = new BoardPARAM();
		p.setI_board(i_board);
		p.setI_user(SecurityUtils.getLoginUserPK(request));
			
		request.setAttribute("cmtList", BoardCmtService.selBoardcmtList(p));
		
		return BoardDAO.selBoard(p);	// return i_board != 0 ? BoardDAO.selBoard(p) : null;
	}
	
	// 글 삭제
	public static int del(HttpServletRequest request) {
		
		int i_board = Utils.getIntParam(request, "i_board");
		int i_user = SecurityUtils.getLoginUserPK(request);
		
		String sql = "DELETE FROM t_board WHERE i_board = ? AND i_user = ?";
		
		return BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
				ps.setInt(2, i_user);
			}
		});
	}
	
	
}
