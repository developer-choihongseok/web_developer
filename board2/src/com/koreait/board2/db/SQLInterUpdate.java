package com.koreait.board2.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// AOP
public interface SQLInterUpdate {
	void proc(PreparedStatement ps) throws SQLException;
}
