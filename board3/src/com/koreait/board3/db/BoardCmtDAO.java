package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board3.model.BoardCmtSEL;
import com.koreait.board3.model.BoardPARAM;

public class BoardCmtDAO extends CommonDAO{
	// 댓글 목록 읽기
	public static List<BoardCmtSEL> selCmtList(BoardPARAM p){
		
		List<BoardCmtSEL> list = new ArrayList<BoardCmtSEL>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT A.i_cmt, A.i_board, A.i_user, A.ctnt, A.r_dt, B.nm"
				+ " FROM t_board_cmt A "
				+ " INNER JOIN t_user B "
				+ " ON A.i_user = B.i_user "
				+ " WHERE A.i_board = ? "
				+ " ORDER BY A.i_cmt DESC";
	
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, p.getI_board());
			
			rs = ps.executeQuery();
			
			BoardCmtSEL sel = null;
			
			while(rs.next()) {
				sel = new BoardCmtSEL();
				
				sel.setI_cmt(rs.getInt("i_cmt"));
				sel.setI_board(rs.getInt("i_board"));
				sel.setI_user(rs.getInt("i_user"));
				sel.setCtnt(rs.getString("ctnt"));
				sel.setR_dt(rs.getString("r_dt"));
				sel.setUser_nm(rs.getString("nm"));
				
				list.add(sel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		
		return list;
	}
}
