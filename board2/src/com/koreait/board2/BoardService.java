package com.koreait.board2;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.koreait.board2.db.BoardDAO;
import com.koreait.board2.db.SQLInterUpdate;
import com.koreait.board2.model.BoardCmtVO;
import com.koreait.board2.model.BoardVO;

// 유지보수 및 서블릿에 코드를 단순화 해준다!!
public class BoardService {
	
	public static int selPageCnt(BoardVO param) {
		return BoardDAO.selPageCnt(param);
	}
	
	public static BoardVO detail(BoardVO param, HttpServletRequest request) {
		
		System.out.println(request.getClass().getName());
		String ip = request.getRemoteAddr();	// 요청한 호스트의 네트워크 주소를 구한다.
		System.out.println("ip : " + ip);
		
		String key = String.format("b_%d_%d", param.getTyp(), param.getI_board());
		
		// ServletContext : 하나의 서블릿이 서블릿 컨테이너와 통신하기 위해서 사용되어지는 메서드들을 가지고 있는 클래스.
		// application 객체에 저장된 내용은 하나의 프로젝트 내의 모든 JSP 페이지에서 공통적으로 사용 할 수 있다.
		ServletContext application = request.getServletContext();
		String savedIp = (String)application.getAttribute(key);	// 처음 들어오면 null값이 들어있다.
		
		if(!ip.equals(savedIp)) {
			application.setAttribute(key, ip);
			
			String sql = " UPDATE t_board_? "
					+ " SET hits = hits + 1 "
					+ " WHERE i_board = ? ";
			
			BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, param.getTyp());			
					ps.setInt(2, param.getI_board());
				}
			});
		}
		
		Enumeration<String> strArr = application.getAttributeNames();
		
		while(strArr.hasMoreElements()) {
			String str = strArr.nextElement();
			
			if(str.startsWith("b_")) {
				System.out.println("key : " + str);	
				System.out.println("value : " + application.getAttribute(str));
			}
		}
		
		return selBoard(param);
	}
	
	public static BoardVO selBoard(BoardVO param) {
		return BoardDAO.selBoard(param);
	}
	
	public static List<BoardVO> selBoardList(BoardVO param, int page) {
		// 글 목록에 보일 게시물 개수
		int s_idx = (page - 1) * param.getRowCntPerPage();
		param.setS_idx(s_idx);
		return BoardDAO.selBoardList(param);
	}
	
	public static int regmod(BoardVO param) {
		
		if(param.getI_board() > 0) { 	// 수정
			String sql = " UPDATE t_board_? "
					+ " SET title = ? "
					+ " , ctnt = ? "
					+ " WHERE i_board = ? ";
			
			return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, param.getTyp());
					ps.setNString(2, param.getTitle());
					ps.setNString(3, param.getCtnt());
					ps.setInt(4, param.getI_board());
				}
			});
		}
		return BoardDAO.insBoard(param);	// 등록
	}
	
	// 댓글 삭제
	public static int delBoard(BoardVO param) {
		String sql = " DELETE FROM t_board_? "
				+ " WHERE i_board = ? ";
		
		// 이름이 없는 클래스 -> 익명클래스
		// 익명 클래스로 만들면, param에 접근 가능!!
		// Call-Back 함수
		return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException{
				// 결국 아래 2개는 같은 객체를 가리킨다. 즉, ps의 주소값으로 접근!!
				ps.setInt(1, param.getTyp());
				ps.setInt(2, param.getI_board());
			}
		});
	}
	
	// 댓글 읽기
	public static List<BoardCmtVO> selBoardCmtList(BoardVO param) {
		return BoardDAO.selBoardCmtList(param);
	}
	
	// 댓글 쓰기
	public static int cmtIns(BoardCmtVO param) {
		String sql = " INSERT INTO t_board_cmt_? "
				+ " (i_board, ctnt) "
				+ " VALUES "
				+ " (?, ?) ";
		
		return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, param.getTyp());
				ps.setInt(2, param.getI_board());
				ps.setNString(3, param.getCtnt());
			}
		});
	}
}

