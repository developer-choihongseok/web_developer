package com.koreait.board4.db;

import java.sql.*;
import java.util.*;

import com.koreait.board4.model.*;

public class BoardDAO extends CommonDAO{
	// 각 게시판의 게시글 읽기
	public static BoardSEL selBoard(BoardPARAM param) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT A.i_board, A.typ, A.seq, A.title, A.ctnt, A.r_dt, A.hits "
				+ " , B.i_user, IFNULL(B.nm, '탈퇴회원') AS writer_nm, B.profile_img "
				+ " , IFNULL(C.favorite_cnt, 0) AS favorite_cnt "
				+ " , CASE WHEN D.i_board IS NULL THEN 0 ELSE 1 END AS is_favorite"
				+ " FROM t_board A "
				+ " LEFT JOIN t_user B "
				+ " ON A.i_user = B.i_user "
				+ " LEFT JOIN ( "
				+ " SELECT i_board, COUNT(i_board) AS favorite_cnt"
				+ "	FROM t_board_favorite" 
				+ "	GROUP BY i_board"
				+ " ) C ON A.i_board = C.i_board"
				
				+ " LEFT JOIN t_board_favorite D" 
				+ " ON A.i_board = D.i_board"
				+ " AND D.i_user = ?"
				+ " WHERE A.i_board = ? ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getI_user());	// 로그인 유저 PK
			ps.setInt(2, param.getI_board());
			rs = ps.executeQuery();
			
			// 레코드가 1개라도 있으면,
			// 조회한 레크드의 각 칼럼 값을 받아 와서, 각 칼럼 값을 다시 BoardSEL 객체의 속성에 설정.
			if(rs.next()) {
				BoardSEL vo = new BoardSEL();
				
				vo.setI_board(rs.getInt("i_board"));
				vo.setTyp(rs.getInt("typ"));
				vo.setSeq(rs.getInt("seq"));
				vo.setTitle(rs.getString("title"));
				vo.setCtnt(rs.getNString("ctnt"));
				vo.setR_dt(rs.getString("r_dt"));
				vo.setHits(rs.getInt("hits"));
				vo.setI_user(rs.getInt("i_user"));
				vo.setWriter_nm(rs.getString("writer_nm"));
				vo.setProfile_img(rs.getString("profile_img"));
				vo.setFavorite_cnt(rs.getInt("favorite_cnt"));
				vo.setIs_favorite(rs.getInt("is_favorite"));
				
				return vo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		
		return null;
	}
	
	// 각 게시판의 게시글 목록 확인
	public static List<BoardSEL> selBoardList(BoardPARAM param){
		
		List<BoardSEL> list = new ArrayList<BoardSEL>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// LEFT JOIN : table1의 내용은 그대로 있고 table2와 겹치는 부분의 내용을 추출.
		String sql = " SELECT A.i_board, A.seq, A.title, A.r_dt, A.hits "
				+ " , B.i_user, IFNULL(B.nm, '탈퇴회원') AS writer_nm, B.profile_img "
				+ " , IFNULL(C.favorite_cnt, 0) AS favorite_cnt "
				+ " FROM t_board A "
				+ " LEFT JOIN t_user B "
				+ " ON A.i_user = B.i_user "
				+ " LEFT JOIN ( "
				+ " SELECT i_board, COUNT(i_board) AS favorite_cnt"
				+ "	FROM t_board_favorite" 
				+ "	GROUP BY i_board"
				+ " ) C ON A.i_board = C.i_board"
				+ " WHERE A.typ = ? "
				+ " ORDER BY seq DESC";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			
			rs = ps.executeQuery();
			
			// 레코드가 1개라도 있으면,
			// 조회한 레크드의 각 칼럼 값을 받아 와서, 각 칼럼 값을 다시 BoardSEL 객체의 속성에 설정.
			// 그리고 설정된 BoardSEL 객체를 다시 ArrayList에 저장.
			while(rs.next()) {
				BoardSEL sel = new BoardSEL();
				
				sel.setI_board(rs.getInt("i_board"));
				sel.setSeq(rs.getInt("seq"));
				sel.setTitle(rs.getString("title"));
				sel.setR_dt(rs.getString("r_dt"));
				sel.setHits(rs.getInt("hits"));
				sel.setI_user(rs.getInt("i_user"));
				sel.setWriter_nm(rs.getString("writer_nm"));
				sel.setProfile_img(rs.getString("profile_img"));
				sel.setFavorite_cnt(rs.getInt("favorite_cnt"));
				
				list.add(sel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		
		return list;	// 조회한 레코드의 개수만큼 BoardSEL 객체를 저장한 ArrayList를 반환.
	}
}
