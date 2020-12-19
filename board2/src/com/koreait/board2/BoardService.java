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
	// DB에 총 칼럼 수
	public static int selPageCnt(BoardVO param) {
		return BoardDAO.selPageCnt(param);
	}
	
	// 조회 수 중복 방지 & 게시물 상세 내용
	public static BoardVO detail(BoardVO param, HttpServletRequest request) {
		
		String ip = request.getRemoteAddr();	// 요청한 호스트의 네트워크 주소를 구한다.
		System.out.println("현재 ip 주소 : " + ip);
		
		String key = String.format("b_%d_%d", param.getTyp(), param.getI_board());
		
		// application 객체에 저장된 내용은 하나의 프로젝트 내의 모든 jsp 페이지에서 공통적으로 사용 할 수 있다.
		// 현재 서버에 어플리케이션 객체를 가져오는 코드.
		ServletContext application = request.getServletContext();
		String savedIp = (String)application.getAttribute(key);	// 처음 들어오면 null값이 들어있다.
		
		// ip가 다르면 조회 수 증가, ip가 같으면 조회 수 증가 X
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
	
	// 게시물 상세 내용
	public static BoardVO selBoard(BoardVO param) {
		return BoardDAO.selBoard(param);
	}
	
	// 전체 게시글 확인
	public static List<BoardVO> selBoardList(BoardVO param, int page) {
		// 글 목록에 보일 게시물 개수
		int s_idx = (page - 1) * param.getRowCntPerPage();
		param.setS_idx(s_idx);
		
		return BoardDAO.selBoardList(param);
	}
	
	// 글 수정 & 글 등록
	public static int regmod(BoardVO param) {
		
		if(param.getI_board() > 0) {
			String sql = " UPDATE t_board_? "
					+ " SET title = ? "
					+ " , ctnt = ? "
					+ " WHERE i_board = ? ";
			
			return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, param.getTyp());
					ps.setString(2, param.getTitle());
					ps.setString(3, param.getCtnt());
					ps.setInt(4, param.getI_board());
				}
			});
		}
		return BoardDAO.insBoard(param);
	}
	
	// 글 삭제
	public static int delBoard(BoardVO param) {
		String sql = " DELETE FROM t_board_? "
				+ " WHERE i_board = ? ";
		
		// 익명클래스
		// 익명 클래스로 만들면, param에 접근 가능!!
		// "Call-Back 함수"라고 부른다.
		return BoardDAO.myExecuteUpdate(sql, new SQLInterUpdate() {
			@Override
			public void proc(PreparedStatement ps) throws SQLException{
				// 결국 아래 2개는 같은 객체를 가리킨다. 즉, ps의 주소값으로 접근!!
				ps.setInt(1, param.getTyp());
				ps.setInt(2, param.getI_board());
			}
		});
	}
	
	// 댓글 목록 확인
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
				ps.setString(3, param.getCtnt());
			}
		});
	}
}

