package com.koreait.board4;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.SQLInterUpdate;
import com.koreait.board4.db.UserDAO;
import com.koreait.board4.model.UserModel;

public class UserController {
	// 로그인 페이지 띄우는 용도 -> Get방식
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Utils.forwardTemp("로그인", "temp/basic_temp", "user/login", request, response);
	}
	
	// 로그인 처리 -> Post방식
	public void loginProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 에러 : 0, 로그인 성공 : 1, 아이디 없음 : 2, 비밀번호 틀림 : 3
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		UserModel p = new UserModel();
		p.setUser_id(user_id);
		
		UserModel loginUser = UserDAO.selUser(p);
		
		if(loginUser == null) {
			request.setAttribute("msg", "아이디를 확인해 주세요.");
			login(request, response);	// 이상이 생겼음.
			return;	// 리턴을 꼭 넣어야 한다! 안 그러면, 밑에꺼까지 다 실행이 되므로.
		}
		
		String encryptPw = SecurityUtils.getSecurePassword(user_pw, loginUser.getSalt());
		
		if(encryptPw.equals(loginUser.getUser_pw())) {	// 성공
			loginUser.setSalt(null);
			loginUser.setUser_pw(null);
			
			HttpSession session = request.getSession();	// 세션 얻어오는 방법
			session.setAttribute("loginUser", loginUser);	// basic_temp.jsp에서 쓰인다.
			
			response.sendRedirect("/board/list.korea?typ=1");
			
		}else {	// 비밀번호 틀림
			request.setAttribute("msg", "비밀번호를 확인해 주세요.");
			login(request, response);
		}
	}
	
	// 회원가입 페이지 띄우기 -> Get방식
	public void join(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Utils.forwardTemp("회원가입", "temp/basic_temp", "user/join", request, response);
	}
	
	// 회원가입 처리 -> Post방식
	public void joinProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String salt = SecurityUtils.getSalt();
		String encryptPw = SecurityUtils.getSecurePassword(user_pw, salt);
		String nm = request.getParameter("nm");
		int gender = Utils.getIntParam(request, "gender");
		String ph = request.getParameter("ph");
		
		String sql = "INSERT INTO t_user "
				+ " (user_id, user_pw, salt, nm, gender, ph) "
				+ " VALUES "
				+ " (?, ?, ?, ?, ?, ?)";
		
		int result = UserDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {	// JAVA Call Back Function
				ps.setString(1, user_id);
				ps.setString(2, encryptPw);
				ps.setString(3, salt);
				ps.setString(4, nm);
				ps.setInt(5, gender);
				ps.setString(6, ph);
			}
		});
		// 회원가입 오류가 발생되면 (아이디가 엄청 길다 등등), 다시 회원가입 페이지로 가게 하기.
		if(result == 0) {
			request.setAttribute("msg", "회원가입에 실패하였습니다.");
			join(request, response);
			return;
		}
		// 회원가입 완료되면 로그인 화면으로 이동.
		response.sendRedirect("/user/login.korea");
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		hs.invalidate();
		
		response.sendRedirect("/user/login.korea");
	}
}
