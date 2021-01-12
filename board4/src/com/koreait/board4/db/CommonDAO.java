package com.koreait.board4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board4.model.ManageBoardModel;

public class CommonDAO {
	// 리스트 헤더 부분
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
			
			// 레코드가 1개라도 있으면,
			// 조회한 레크드의 각 칼럼 값을 받아 와서, 각 칼럼 값을 다시 ManageBoardModel 객체의 속성에 설정.
			// 그리고 설정된 ManageBoardModel 객체를 다시 ArrayList에 저장.
			while(rs.next()) {
				ManageBoardModel vo = new ManageBoardModel();
				
				vo.setTyp(rs.getInt("typ"));
				vo.setNm(rs.getString("nm"));
				
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		
		return list;	// 조회한 레코드의 개수만큼 ManageBoardModel 객체를 저장한 ArrayList를 반환.
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
