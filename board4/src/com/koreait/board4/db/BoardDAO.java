package com.koreait.board4.db;

import java.sql.*;
import java.util.*;

import com.koreait.board4.model.*;

public class BoardDAO extends CommonDAO{
	
	// 각 게시판의 게시글 목록 확인
	public static List<BoardSEL> selBoardList(BoardPARAM param){
		
		List<BoardSEL> list = new ArrayList<BoardSEL>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT A.i_board, A.seq, A.title, A.r_dt, A.hits "
				+ " , B.i_user, IFNULL(B.nm, '탈퇴회원') AS writer_nm "
				+ " , IFNULL(C.favorite_cnt, 0) AS favorite_cnt "
				+ " FROM t_board A "
				+ " LEFT JOIN t_user B "
				+ " ON A.i_user = B.i_user "
				+ " LEFT JOIN ( "
				+ " SELECT i_board, COUNT(i_board) AS favorite_cnt"
				+ "	FROM t_board_favorite" 
				+ "	GROUP BY i_board"
				+ " ) C ON A.i_board = C.i_board"
				+ " WHERE typ = ? "
				+ " ORDER BY seq DESC";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				BoardSEL sel = new BoardSEL();
				
				sel.setI_board(rs.getInt("i_board"));
				sel.setSeq(rs.getInt("seq"));
				sel.setTitle(rs.getString("title"));
				sel.setR_dt(rs.getString("r_dt"));
				sel.setHits(rs.getInt("hits"));
				sel.setI_user(rs.getInt("i_user"));
				sel.setWriter_nm(rs.getString("writer_nm"));
				sel.setFavorite_cnt(rs.getInt("favorite_cnt"));
				
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
