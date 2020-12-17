package com.koreait.board3.db;

import java.sql.*;
import java.util.*;
import com.koreait.board3.model.BoardPARAM;
import com.koreait.board3.model.BoardSEL;

public class BoardDAO extends CommonDAO{
	
	public static List<BoardSEL> selBoardList(final BoardPARAM p){
		
		List<BoardSEL> list = new ArrayList<BoardSEL>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT " 
				+ " A.i_board, A.seq, A.title, A.r_dt, A.hits, A.i_user, B.nm "
				+ " FROM t_board A "
				+ " INNER JOIN t_user B "
				+ " ON A.i_user = B.i_user " 
				+ " WHERE A.typ = ? " 
				+ " ORDER BY A.seq DESC ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, p.getTyp());
	
			rs = ps.executeQuery();
			
			BoardSEL sel = null;
			
			while(rs.next()) {
				sel = new BoardSEL();	// 값들을 계속 다르게 다르게 담을수 있다.
				
				sel.setI_board(rs.getInt("i_board"));
				sel.setSeq(rs.getInt("seq"));
				sel.setTitle(rs.getString("title"));
				sel.setR_dt(rs.getString("r_dt"));
				sel.setHits(rs.getInt("hits"));
				sel.setI_user(rs.getInt("i_user"));
				sel.setNm(rs.getString("nm"));
				
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
