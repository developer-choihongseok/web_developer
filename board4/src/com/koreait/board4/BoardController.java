package com.koreait.board4;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board4.common.SecurityUtils;
import com.koreait.board4.common.Utils;
import com.koreait.board4.db.BoardDAO;
import com.koreait.board4.db.SQLInterUpdate;
import com.koreait.board4.model.BoardPARAM;
import com.koreait.board4.model.BoardSEL;

public class BoardController {
	
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int typ = Utils.getIntParam(request, "typ", 1);	// 기본값을 typ=1인 게시판으로 설정
		
		BoardPARAM param = new BoardPARAM();
		param.setTyp(typ);
		
		request.setAttribute("list", BoardDAO.selBoardList(param));
		request.setAttribute("jsList", new String[] {"board"});	// basic_temp.jsp에서 설정, 배열에서 값 꺼내기.
		
		Utils.forwardTemp("리스트", "temp/basic_temp", "board/bList", request, response);
	}
	
	public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int i_board = Utils.getIntParam(request, "i_board");
		
		BoardPARAM param = new BoardPARAM();
		param.setI_board(i_board);
		param.setI_user(SecurityUtils.getLoginUserPK(request));
		
		// Board2 -> BoardService 참고!!
		// IP 주소로 구분하여 application 객체 이용하여 새로고침해서 조회수 안 올라가도록 조치!!
		
		// 1. IP주소값 얻어야 함
		// 2. application에서 "특정 키 값 만들어야 함" 즉, 어떤 글인지 구분이 가능해야한다.
		// 3. application에서 특정 키값으로 값이 있는지 체크
		// 	3-1) 없으면 그 특정 키값으로 나의 IP주소를 set한다.(조회수 올림)
		// 	3-2) 있으면 application에 저장된 IP주소가 나랑 같은지 확인
		// 		3-2-1) 같으면 무시
		// 		3-2-2) 다르면(조회수 올림) > 그 특정 키값으로 나의 IP주소를 set한다.
		
		// --------------------------------------- [조회수 처리 START]
		final String KEY = "b_" + i_board;
		String myIpAddr = request.getRemoteAddr();
		
		// 메모리에 저장되기때문에 용량을 많이 잡아먹는다 -> 메모리 낭비!!!!!!!!!
		// 테이블만들어서 거기에 조회수를 저장하는 것이 가장 좋다
		ServletContext application = request.getServletContext();
		String savedIpAddr = (String) application.getAttribute(KEY);
		
		if(savedIpAddr == null || !savedIpAddr.equals(myIpAddr)) {
			application.setAttribute(KEY, myIpAddr);
			
			String sql = " UPDATE t_board SET hits = hits + 1 "
					+ " WHERE i_board = ? ";
			
			BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
				
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, i_board);
				}
			});
		}
		// --------------------------------------- [조회수 처리 END]
		
		BoardSEL data = BoardDAO.selBoard(param);
		request.setAttribute("data", data);
		request.setAttribute("jsList", new String[] {"board", "axios.min"});	// basic_temp.jsp에서 설정, 배열에서 값 꺼내기.
		
		Utils.forwardTemp(data.getTitle(), "temp/basic_temp", "board/bDetail", request, response);
	}
	
	public void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Utils.forwardTemp("글 등록", "temp/basic_temp", "board/bRegmod", request, response);
	}
	
	public void mod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Utils.forwardTemp("글 수정", "temp/basic_temp", "board/bRegmod", request, response);
	}
	
	// 글 쓰기
	public void regProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int typ = Utils.getIntParam(request, "typ");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		int writerI_user = SecurityUtils.getLoginUserPK(request);
		
		String sql = "INSERT INTO t_board "
				+ " (typ, seq, title, ctnt, i_user) "
				+ " SELECT ?, IFNULL(MAX(seq), 0) + 1, ?, ?, ? "
				+ " FROM t_board "
				+ " WHERE typ = ?";
		
		int result = BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, typ);
				ps.setString(2, title);
				ps.setString(3, ctnt);
				ps.setInt(4, writerI_user);
				ps.setInt(5, typ);
			}
		});
		
		if(result == 0) {
			Controller.goToErr(request, response);
		}
		
		// 새로운 request가 만들어진다.
		response.sendRedirect("/board/list.korea?typ=" + typ);
		
		// 원래 쓰던 데이터가 그대로 남아서 가지고 온다.
//		request.getRequestDispatcher("/board/list.korea?typ=" + typ).forward(request, response);
	}
	
	// 글 수정
	public void modProc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
	
	public void ajaxFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int state = Utils.getIntParam(request, "state");
		int i_board = Utils.getIntParam(request, "i_board");
		
		System.out.println("state: " + state);
		System.out.println("i_board: " + i_board);
		
		// 1 : insert문
		String sql = " INSERT INTO t_board_favorite (i_board, i_user) VALUES(?, ?) ";
		
		if(state == 0) {
			sql = " DELETE FROM t_board_favorite WHERE i_board = ? AND i_user = ? ";
		}
		
		int result = BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
				ps.setInt(2, SecurityUtils.getLoginUserPK(request));
			}
		});
				
		response.setContentType("application/json");
		response.getWriter().print(String.format("{\"result\": %d}", result));	// ex) {"result":1} {"result":0}
	}
}
