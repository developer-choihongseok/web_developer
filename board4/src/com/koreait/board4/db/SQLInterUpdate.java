package com.koreait.board4.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// 공통 기능으로 사용할 인터페이스 생성.
public interface SQLInterUpdate {
	void proc(PreparedStatement ps) throws SQLException;
}
