package com.koreait.board2.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board2.model.BoardCmtVO;
import com.koreait.board2.model.BoardVO;

public class BoardDAO {
	// 페이징 처리
	public static int selPageCnt(final BoardVO param) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		// COUNT(i_board) / ? : 데이터의 총 개수 / 리스트에 보여질 게시물의 최대 개수
		String sql = "SELECT ceil(COUNT(i_board) / ?) "
				+ "from t_board_?";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getRowCntPerPage());
			ps.setInt(2, param.getTyp());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				// 첫번째 컬럼 값 가져오기.
				// 또는 sql문에 alias를 주어서, alias 이름을 넣어줘도 된다.
				return rs.getInt(1);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbUtils.close(con, ps, rs);
		}
		return 0;
	}
	
	// 해당 글 읽기
	public static BoardVO selBoard(final BoardVO param) {
		
		BoardVO vo = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT i_board, title, ctnt, hits, r_dt "
				+ " FROM t_board_? "
				+ " WHERE i_board = ? ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			ps.setInt(2, param.getI_board());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				vo = new BoardVO();
				vo.setI_board(param.getI_board());
				vo.setTyp(param.getTyp());
				vo.setTitle(rs.getNString("title"));
				vo.setCtnt(rs.getNString("ctnt"));
				vo.setHits(rs.getInt("hits"));
				vo.setR_dt(rs.getString("r_dt"));
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return vo;
	}
	
	// 서브 페이지의 글 목록 확인
	public static List<BoardVO> selBoardList(final BoardVO param) {
		List<BoardVO> list = new ArrayList();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT i_board, title, hits, r_dt "
				+ " FROM t_board_? "
				+ " ORDER BY i_board DESC "
				+ " LIMIT ?, ? ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);			
			ps.setInt(1, param.getTyp());
			ps.setInt(2, param.getS_idx());
			ps.setInt(3, param.getRowCntPerPage());	// 가져올 데이터 개수
			rs = ps.executeQuery();
			
			BoardVO vo = null;
			
			while(rs.next()) {
				vo = new BoardVO();
				list.add(vo);
				
				vo.setI_board(rs.getInt("i_board"));
				vo.setTitle(rs.getNString("title"));
				vo.setR_dt(rs.getString("r_dt"));
				vo.setHits(rs.getInt("hits"));
			}			
			
		} catch (Exception e) {		
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}		
		return list;
	}
	
	// 댓글 읽기
	public static List<BoardCmtVO> selBoardCmtList(final BoardVO param) {
		List<BoardCmtVO> list = new ArrayList();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT i_cmt, ctnt FROM t_board_cmt_?"
				+ " WHERE i_board = ? "
				+ " ORDER BY i_cmt DESC ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			ps.setInt(2, param.getI_board());
			
			rs = ps.executeQuery();
			
			BoardCmtVO vo = null;
			
			while(rs.next()) {
				vo = new BoardCmtVO();
				list.add(vo);
				
				vo.setI_cmt(rs.getInt("i_cmt"));
				vo.setCtnt(rs.getNString("ctnt"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return list;
	}
	
	// 글 쓰기
	public static int insBoard(final BoardVO param) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " INSERT INTO t_board_? "
				+ " (title, ctnt) "
				+ " VALUES "
				+ " (?, ?) ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, param.getTyp());
			ps.setNString(2, param.getTitle());
			ps.setNString(3, param.getCtnt());
			int result = ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();	// PK값을 가져온다.
			
			if(rs.next()) {
				// 1번쩨 컬럼을 얻어와서 
				int i_board = rs.getInt(1);
				// i_board의 값을 1번으로 바꾼다(원래 0번)
				param.setI_board(i_board);
			}
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return 0;
	}
	
	// 글 삭제
//	public static int delBoard(final BoardVO param) {
//		Connection con = null;
//		PreparedStatement ps = null;
//		String sql = " DELETE FROM t_board_? "
//				+ " WHERE i_board = ? ";
//		try {
//			con = DbUtils.getCon();
//			ps = con.prepareStatement(sql);
//			ps.setInt(1, param.getTyp());
//			ps.setInt(2, param.getI_board());			
//			return ps.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DbUtils.close(con, ps);
//		}
//		return 0;
//	}
	
	// 글 수정
//	public static int updBoard(final BoardVO param) {
//		Connection con = null;
//		PreparedStatement ps = null;
//		String sql = " UPDATE t_board_? "
//				+ " SET title = ? "
//				+ " , ctnt = ? "
//				+ " WHERE i_board = ? ";
//		try {
//			con = DbUtils.getCon();
//			ps = con.prepareStatement(sql);
//			ps.setInt(1, param.getTyp());
//			ps.setNString(2, param.getTitle());
//			ps.setNString(3, param.getCtnt());
//			ps.setInt(4, param.getI_board());			
//			return ps.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DbUtils.close(con, ps);
//		}
//		return 0;
//	}
	
	// 조회 수 추가
//	public static void addHits(BoardVO param) {
//		Connection con = null;
//		PreparedStatement ps = null;
//		String sql = " UPDATE t_board_? "
//				+ " SET hits = hits + 1 "
//				+ " WHERE i_board = ? ";
//				
//		try {
//			con = DbUtils.getCon();
//			ps = con.prepareStatement(sql);
//			ps.setInt(1, param.getTyp());			
//			ps.setInt(2, param.getI_board());			
//			ps.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			DbUtils.close(con, ps);
//		}	
//	}
	
	// AOP(= 관점 지향 프로그래밍)
	public static int myExecuteUpdate(String sql, SQLInterUpdate sqlInter) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			sqlInter.proc(ps);
			
			return ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		return 0;
	}
	

}