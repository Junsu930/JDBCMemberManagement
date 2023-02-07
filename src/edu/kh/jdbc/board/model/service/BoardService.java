package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.vo.Board;

public class BoardService {
	
	Connection conn = null;
	BoardDAO dao = new BoardDAO();

	
	public List<Board> boardList() throws Exception {
		conn = getConnection();
		
		List<Board> boardList = new ArrayList<>();
		boardList = dao.boardList(conn);
		close(conn);
		
		return boardList;
	}

}
