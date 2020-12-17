package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.koreait.board3.model.UserModel;

// Service단에서 처리
public class UserDAO extends CommonDAO {
	// 로그인 확인
	public static UserModel selUser(UserModel p) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT i_user, nm, user_pw, salt "
				+ "FROM t_user "
				+ "WHERE user_id = ? ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, p.getUser_id());
			
			rs = ps.executeQuery();
			
			// 쿼리문을 실행하여, user_id가 있다면
			if(rs.next()) {
				UserModel um = new UserModel();
				
				um.setI_user(rs.getInt("i_user"));
				um.setNm(rs.getString("nm"));
				um.setUser_pw(rs.getString("user_pw"));
				um.setSalt(rs.getString("salt"));
				
				return um;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return null;
	}
	
	/* 더 이상 사용 X
	public static int login(UserModel p) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT i_user, nm, user_pw, salt "
				+ " FROM t_user "
				+ " WHERE user_id = ? ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, p.getUser_id());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				// 이미 DB에서 암호화된 비밀번호
				String dbPw = rs.getString("user_pw");
				String dbSalt = rs.getString("salt");
				
				String encryptPw = SecurityUtils.getSecurePassword(p.getUser_pw(), dbSalt);
				
				// 암호화한 비밀번호와 입력한 비밀번호가 같다면,
				if(encryptPw.equals(dbPw)) {
					return 1;	// 로그인 성공
				}else {
					return 3;	// 비밀번호 틀림
				}
			}else {
				return 2;	// 아이디 없음
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return 0;	// 에러
	}
	*/
}
