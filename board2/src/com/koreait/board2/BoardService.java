package com.koreait.board2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.koreait.board2.db.BoardDAO;
import com.koreait.board2.db.SQLInterUpdate;
import com.koreait.board2.model.BoardCmtVO;
import com.koreait.board2.model.BoardVO;

public class BoardService {
	
	public static int selPageCnt(BoardVO param) {
		return BoardDAO.selPageCnt(param);
	}
	
	public static BoardVO detail(BoardVO param, HttpServletRequest request) {
		
		System.out.println(request.getClass().getName());
		String ip = request.getRemoteAddr();
		System.out.println("ip : " + ip);
		
		String key = String.format("b_%d_%d", param.getTyp(), param.getI_board());
		
		ServletContext application = request.getServletContext();
		String savedIp = (String)application.getAttribute(key);
		
		if(!ip.equals(savedIp)) {
			application.setAttribute(key, ip);
			
			String sql = " UPDATE t_board_? "
					+ " SET hits = hits + 1 "
					+ " WHERE i_board = ? ";
			
			BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, param.getTyp());			
					ps.setInt(2, param.getI_board());
				}
			});
		}
		
		Enumeration<String> strArr = application.getAttributeNames();
		
		while(strArr.hasMoreElements()) {
			String str = strArr.nextElement();	
			
			if(str.startsWith("b_")) {
				System.out.println("key : " + str);	
				System.out.println("value : " + application.getAttribute(str));
			}
		}
		
		return selBoard(param);
	}
	
	public static BoardVO selBoard(BoardVO param) {
		return BoardDAO.selBoard(param);
	}
	
	public static List<BoardVO> selBoardList(BoardVO param, int page) {
		int s_idx = (page - 1) * param.getRowcntPerPage();
		param.setS_idx(s_idx);
		return BoardDAO.selBoardList(param);
	}
	
	public static int regmod(BoardVO param) {
		
		if(param.getI_board() > 0) { 	// 수정
			String sql = " UPDATE t_board_? "
					+ " SET title = ? "
					+ " , ctnt = ? "
					+ " WHERE i_board = ? ";
			
			return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, param.getTyp());
					ps.setNString(2, param.getTitle());
					ps.setNString(3, param.getCtnt());
					ps.setInt(4, param.getI_board());
				}
			});
		}
		return BoardDAO.insBoard(param);		
	}
	
	public static int delBoard(BoardVO param) {
		String sql = " DELETE FROM t_board_? "
				+ " WHERE i_board = ? ";
		
		// 이름이 없는 클래스 -> 익명클래스
		// ps의 주소값으로 접근!!
		// Call-Back 함수
		return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException{
				ps.setInt(1, param.getTyp());
				ps.setInt(2, param.getI_board());
			}
		});
	}
	
	public static List<BoardCmtVO> selBoardCmtList(BoardVO param) {
		return BoardDAO.selBoardCmtList(param);
	}
	
	public static int cmtIns(BoardCmtVO param) {
		String sql = " INSERT INTO t_board_cmt_? "
				+ " (i_board, ctnt) "
				+ " VALUES "
				+ " (?, ?) ";
		
		return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getTyp());
				ps.setInt(2, param.getI_board());
				ps.setNString(3, param.getCtnt());
			}
		});
	}
}

