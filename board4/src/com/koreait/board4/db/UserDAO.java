package com.koreait.board4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.koreait.board4.model.UserModel;

public class UserDAO extends CommonDAO{
	// 로그인, 프로필에서 사용!!
	public static UserModel selUser(UserModel p) {
		
		String sql = "SELECT * FROM t_user WHERE ";
		
		if(p.getUser_id() != null) {
			sql += " user_id = ?";	// 로그인
		} else if(p.getI_user() > 0) {
			sql += " i_user = ?";	// 프로필
		}
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			
			if(p.getUser_id() != null) {	// 담지 않았다.
				ps.setString(1, p.getUser_id());
			}else if(p.getI_user() > 0){	// 담았다.
				ps.setInt(1, p.getI_user());
			}
			
			rs = ps.executeQuery();
			
			// 레코드가 1개라도 있으면,
			// 조회한 레크드의 각 칼럼 값을 받아 와서, 각 칼럼 값을 다시 UserModel 객체의 속성에 설정.
			if(rs.next()) {	// true -> 레코드가 1개 있었다는 말!
				UserModel um = new UserModel();
				
				// 첫번째 행에 대한 DB 컬렴명에 해당하는 값들을 가지고 온다.
				um.setI_user(rs.getInt("i_user"));
				um.setUser_id(rs.getString("user_id"));
				um.setUser_pw(rs.getString("user_pw"));
				um.setSalt(rs.getString("salt"));
				um.setNm(rs.getString("nm"));
				um.setGender(rs.getInt("gender"));
				um.setPh(rs.getString("ph"));
				um.setR_dt(rs.getString("r_dt"));
				um.setProfile_img(rs.getString("profile_img"));
				
				return um;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return null;
	}
}
