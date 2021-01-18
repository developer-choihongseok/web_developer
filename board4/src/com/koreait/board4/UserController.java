package com.koreait.board4;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.SQLInterUpdate;
import com.koreait.board4.db.UserDAO;
import com.koreait.board4.model.UserModel;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

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
			
			loginUser.setUser_id(null);
			loginUser.setSalt(null);
			loginUser.setUser_pw(null);
			loginUser.setPh(null);
			loginUser.setProfile_img(null);
			loginUser.setR_dt(null);
			
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
	
	// 프로필 화면
	public void profile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		UserModel param = new UserModel();
		param.setI_user(SecurityUtils.getLoginUserPK(request));
		
		request.setAttribute("data", UserDAO.selUser(param));	// data : profile.jsp에서 사용.
		request.setAttribute("jsList", new String[] {"axios.min", "user"});
		
		Utils.forwardTemp("프로필", "temp/basic_temp", "user/profile", request, response);
	}
	
	// 이미지 업로드 Proc
	// throws가 우선 순위가 try-catch문 보다 높으므로, try-catch문을 삭제하였다.
	public void profileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int i_user = SecurityUtils.getLoginUserPK(request);
		// C:\study\chs_eclipse\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\board4\res\img -> 여기에 이미지 파일이 들어간다!!
		String savePath = request.getServletContext().getRealPath("/res/img/" + i_user);	// 실제 소스 파일에 들어오지 않는다.
		System.out.println(savePath);
		
		File folder = new File(savePath);
		
		// 파일을 삭제 한다면... 방법!!
//		File imgFile = new File(savePath + "/파일명.jpg");
//		
//		if(imgFile.exists()) {
//			imgFile.delete();
//		}
		
		// 기존에 이미지가 있었다면 삭제 처리, 없었다면 폴더 생성.
		if(folder.exists()) {
			File[] folder_list = folder.listFiles();
			
			for(File file : folder_list) {
				if(file.isFile()) {
					file.delete();
				}
			}
			folder.delete();
		}
		folder.mkdirs();

		int sizeLimit = 104_857_600;	// 100 * 1024 * 1024 : 100MB 제한
		
		// DefaultFileRenamePolicy() : 파일 이름이 중복 될 경우 , 파일 이름을 rename(_1 ..) 해주는 역할을 한다.
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
			
		// 전송된 파일 타입의 파라미터 이름들을 Enumeration타입으로 반환 -> 파일 이름 얻어오기
		// Enumeration : Map과 비슷.
		Enumeration files = multi.getFileNames();
			
		// 한 사람당 사진 1개만 올릴 수 있게 하였기 떄문에, if문을 사용.
		// 여러 사진을 올릴려면, while문을 사용하고 sql문이 INSERT문으로 바꿔야 한다.
		if(files.hasMoreElements()) {
			// input에 설정해 놓은 name.
			String eleName = (String)files.nextElement();
//			System.out.println(eleName);	// 중복된 파일이 없으면 문제가 없다.
				
			// 실제로 저장되는 파일 이름
			String fileNm = multi.getFilesystemName(eleName);
			System.out.println("fileNm: " + fileNm);
			
			// 파일 타입
//			String fileType = multi.getContentType(eleName);
//			System.out.println("fileType:"+ fileType);
				
			String sql = " UPDATE t_user SET profile_img = ? "
						+ " WHERE i_user = ?";
				
			UserDAO.executeUpdate(sql, new SQLInterUpdate() {
					
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setString(1, fileNm);
					ps.setInt(2, i_user);
				}
			});
		}
		
		response.sendRedirect("/user/profile.korea");
	}
	
	// 기본 이미지로 되돌리는 메서드
	public void delProfileImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int i_user = SecurityUtils.getLoginUserPK(request);
		String savePath = request.getServletContext().getRealPath("/res/img/" + i_user);	
		
		File folder = new File(savePath);
		
		if(folder.exists()) {	// 기존 이미지 삭제 처리
			File[] folder_list = folder.listFiles();
			
			for(File file : folder_list) {
				if(file.isFile()) {
					file.delete();
				}
			}
			folder.delete();	// 하위 파일들까지 전부 삭제.
		}
		
		String sql = " UPDATE t_user SET profile_img = null "
				+ " WHERE i_user = ?";
		
		UserDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_user);
			}
		});
		
		String result = "{\"result\": 1}";
		
		response.setContentType("application/json");
		response.getWriter().print(result);
	}
	
	public void changePw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("jsList", new String[] {"user"});
		Utils.forwardTemp("비밀번호 변경", "temp/basic_temp", "user/changePw", request, response);
	}
	
	public void changePwProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int i_user = SecurityUtils.getLoginUserPK(request);
		String current_pw = request.getParameter("current_pw");
		String user_pw = request.getParameter("user_pw");
		
		UserModel param = new UserModel();
		param.setI_user(i_user);
		
		UserModel userInfo = UserDAO.selUser(param);
		
		String encryptCurrentPw = SecurityUtils.getSecurePassword(current_pw, userInfo.getSalt());
		
		if(!userInfo.getUser_pw().equals(encryptCurrentPw)) {	// 비밀번호가 틀린 경우
			request.setAttribute("msg", "기존 비밀번호를 확인해 주세요.");
			changePw(request, response);
		}
		
		String encryptUserPw = SecurityUtils.getSecurePassword(user_pw, userInfo.getSalt());
		
		String sql = " UPDATE t_user SET user_pw = ? WHERE i_user = ? ";
		
		UserDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setString(1, encryptUserPw);
				ps.setInt(2, SecurityUtils.getLoginUserPK(request));
			}
		});
		
		logout(request, response);
	}
}
