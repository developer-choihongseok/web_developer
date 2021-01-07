package com.koreait.board4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board4.model.ManageBoardModel;

public class CommonDAO {
	// 게시판 목록 확인
	public static List<ManageBoardModel> selManageBoardList(){
		List<ManageBoardModel> list = new ArrayList<ManageBoardModel>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT typ, nm FROM t_manage_board ORDER BY orderby";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ManageBoardModel vo = new ManageBoardModel();
				
				list.add(vo);
				
				vo.setTyp(rs.getInt("typ"));
				vo.setNm(rs.getString("nm"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		
		return list;
	}
	
	// 나중에 DAO를 많이 만드는데, update, insert, delete를 따로 만들지 않고 이 메서드를 불러서 쓰기 위함!!
	public static int executeUpdate(String sql, SQLInterUpdate siu) {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			
			siu.proc(ps);
			
			return ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps);
		}
		return 0;
	}
}
