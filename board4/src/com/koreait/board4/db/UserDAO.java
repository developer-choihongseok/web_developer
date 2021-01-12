package com.koreait.board4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.koreait.board4.model.UserModel;

// 이모지가 쓰이는 곳에는 getNString()이 좋다. 그게 아니라면 getString()이 낫다.
public class UserDAO extends CommonDAO{
	// 로그인
	public static UserModel selUser(UserModel p) {
		
		String sql = "SELECT i_user, user_pw, salt, nm FROM t_user WHERE user_id = ?";
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, p.getUser_id());
			
			rs = ps.executeQuery();
			
			// 레코드가 1개라도 있으면,
			// 조회한 레크드의 각 칼럼 값을 받아 와서, 각 칼럼 값을 다시 UserModel 객체의 속성에 설정.
			if(rs.next()) {	// true -> 레코드가 1개 있었다는 말!
				UserModel um = new UserModel();
				
				// 첫번째 행에 대한 DB 컬렴명에 해당하는 값들을 가지고 온다.
				um.setI_user(rs.getInt("i_user"));
				um.setUser_pw(rs.getString("user_pw"));
				um.setSalt(rs.getString("salt"));
				um.setNm(rs.getString("nm"));
				
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
