package com.koreait.board3.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.SQLInterUpdate;
import com.koreait.board3.db.UserDAO;
import com.koreait.board3.model.UserModel;

public class UserService {
	// 회원가입
	public static int join(HttpServletRequest request) {
		
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String salt = SecurityUtils.getSalt();
		String encryptPw = SecurityUtils.getSecurePassword(user_pw, salt);	// 암호화 된 PW
		String nm = request.getParameter("nm");
		int gender = Utils.getIntParam(request, "gender");
		String ph = request.getParameter("ph");
		
//		System.out.println("salt: " + salt);
//		System.out.println("encryptPw: " + encryptPw);
		
		UserModel p = new UserModel();
		// 아래 정보들이 DB에 insert가 되어야 한다.
		p.setUser_id(user_id);
		p.setUser_pw(encryptPw);
		p.setSalt(salt);
		p.setNm(nm);
		p.setGender(gender);
		p.setPh(ph);
		
		String sql = "INSERT INTO t_user "
				+ "(user_id, user_pw, salt, nm, gender, ph) "
				+ "VALUES"
				+ "(?, ?, ?, ?, ?, ?)";
		
//		MySqlInter dd = new MySqlInter(p);
//		
//		return UserDAO.executeUpdate(sql, dd);
		
		// 자바 이름 없이 implements 한 것이다.
		// 익명클래스로 해서 객체 주소값을 바로 보낸 것!!
		return UserDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setString(1, p.getUser_id());
				ps.setString(2, p.getUser_pw());
				ps.setString(3, p.getSalt());
				ps.setString(4, p.getNm());
				ps.setInt(5, p.getGender());
				ps.setString(6, p.getPh());
			}
		});
	}
	
	// 1: 로그인 성공, 2: 아이디 없음, 3: 비밀번호 틀림
	public static int login(HttpServletRequest request) {
		
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		UserModel p = new UserModel();
		p.setUser_id(user_id);
		p.setUser_pw(user_pw);
		
//		return UserDAO.login(p);	// 여기서 0,1,2,3 다 처리.
		
		// m은 selUser에서 i_user, nm, user_pw, salt 값에 담겨있는 값을 담았다.
		UserModel m = UserDAO.selUser(p);
		
		if(m == null) {
			return 2;	// 아이디 없음
		}
		
		String encryptPw = SecurityUtils.getSecurePassword(user_pw, m.getSalt());
		// 로그인 성공
		if(encryptPw.equals(m.getUser_pw())) {
			// 세션 객체 얻어 온다.
			HttpSession hs = request.getSession();
			// loginUser : session 키 값
			hs.setAttribute("loginUser", m);
			
			// 로그인 후, Session에 PW가 남아있지 않도록 설정 -> 이유: 보안
			// 따라서, 암호화 된 PW와 클라이언트가 입력한 PW는 null로 한다.
			m.setSalt(null);
			m.setUser_pw(null);
			
			return 1;	// 로그인 성공
		}
		return 3;	// 비밀번호 틀림
	}
}

/*
// 2번째 방법
class MySqlInter extends Object implements SQLInterUpdate{
	
	private UserModel p;
	
	public MySqlInter(UserModel p) {
		super();	// 부모 기본 생성자 호출
		this.p = p;
	}
	
	@Override
	public void proc(PreparedStatement ps) throws SQLException {
		ps.setString(1, p.getUser_id());
		ps.setString(2, p.getUser_pw());
		ps.setString(3, p.getSalt());
		ps.setString(4, p.getNm());
		ps.setInt(5, p.getGender());
		ps.setString(6, p.getPh());
	}
}
*/
