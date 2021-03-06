package com.koreait.board4.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 Connection : DB연걸
 PreparedStatement : 쿼리문 실행 준비
 ResultSet : select문 결과, 테이블에 있는 필드에 접근하고 가져오는 메소드로 구성
*/
public class DbUtils {
		
	public static Connection getCon() throws ClassNotFoundException, SQLException {
		
		final String URL = "jdbc:mysql://localhost:3307/board3?serverTimezone=UTC";
		final String USER = "root";
		final String PW = "koreait2020";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(URL, USER, PW);
		
		System.out.println("DB 연결 성공!");
		
		return con;
	}
	
	// 자원 해제
	public static void close(Connection con, PreparedStatement ps) {
		if(con != null) {
			try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		if(ps != null) {
			try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}
	
	public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		if(rs != null) {
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
		}		
		close(con, ps);
	}
}
