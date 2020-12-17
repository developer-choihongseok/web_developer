package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CommonDAO {
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
