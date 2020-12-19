package com.koreait.board3.db;

import java.sql.*;
import java.util.*;

import com.koreait.board3.model.*;

public class BoardDAO extends CommonDAO{
	// 전체 글 읽기
	public static List<BoardSEL> selBoardList(BoardPARAM p){
		
		List<BoardSEL> list = new ArrayList<BoardSEL>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/*
		 INNER JOIN(= 교집합)
		 	- 서로 매칭되는 것끼리 조인해서 조회
		 	- ON 절과 함께 사용되며, ON 절의 조건을 만족하는 데이터만을 가져온다.
		 	- 테이블의 이름이 길거나 복잡한 경우에는 별칭(alias)을 사용하여 SQL구문을 간략화할 수 있다.
		 	
		 LEFT JOIN(A - B = A) 
		 	- 첫 번째 테이블을 기준으로, 두 번째 테이블을 조합하는 조인.
		 	- 이때 ON 절의 조건을 만족하지 않는 경우에는 첫 번째 테이블의 필드 값은 그대로 가져온다.
		  	     하지만 해당 레코드의 두 번째 테이블의 필드 값은 모두 NULL로 표시된다.
		*/
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
				sel = new BoardSEL();	// 값들을 계속 다르게 다르게 담을 수 있다.
				
				sel.setI_board(rs.getInt("i_board"));
				sel.setSeq(rs.getInt("seq"));
				sel.setTitle(rs.getString("title"));
				sel.setR_dt(rs.getString("r_dt"));
				sel.setHits(rs.getInt("hits"));
				sel.setI_user(rs.getInt("i_user"));
				sel.setNm(rs.getString("nm"));
				sel.setTyp(p.getTyp());
				
				list.add(sel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		
		return list;
	}
	
	// 해당 글 내용 읽기
	public static BoardSEL selBoard(BoardPARAM param) {
		BoardSEL vo = null;
			
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
			
		String sql = "SELECT A.seq, A.typ, A.title, A.ctnt, A.r_dt, A.hits, A.i_user, B.nm "
				+ " FROM t_board A "
				+ " INNER JOIN t_user B "
				+ " ON A.i_user = B.i_user "
				+ " WHERE A.i_board = ? ";
			
		try {
			con = DbUtils.getCon();
				
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_board());
				
			rs = ps.executeQuery();
				
			if(rs.next()) {
				vo = new BoardSEL();
				
				vo.setI_board(param.getI_board());
				vo.setSeq(rs.getInt("seq"));
				vo.setTyp(rs.getInt("typ"));
				vo.setTitle(rs.getString("title"));
				vo.setCtnt(rs.getString("ctnt"));
				vo.setR_dt(rs.getString("r_dt"));
				vo.setHits(rs.getInt("hits"));
				vo.setI_user(rs.getInt("i_user"));
				vo.setNm(rs.getString("nm"));
					
				return vo;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return null;
	}
}
