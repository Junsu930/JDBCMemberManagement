package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.member.vo.Member;

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


	public int editBoard(String content, Member loginMember, Board board) {
		int result = 0;
		conn = getConnection();
		result = dao.editBoard(conn, content, loginMember, board);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}


	public int deleteBoard(Member loginMember, Board board) {
		int result = 0;
		conn = getConnection();
		result = dao.deleteBoard(conn, loginMember, board);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}


	public int writeBoard(String titleInput, String contentInput, Member loginMember) {
		int result = 0;
		conn = getConnection();
		result = dao.writeBoard(conn, titleInput, contentInput, loginMember);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		return result;
	}


	public List<Board> searchBoard(String var, Member loginMember, String searchInput) {
		List<Board> boardList = new ArrayList<>();
		conn = getConnection();
		boardList = dao.searchBoard(conn, var, loginMember, searchInput);
		
		close(conn);
		
		return boardList;
	}


	public void count(Board board) {
		int result = 0;
		conn = getConnection();
		result = dao.count(conn, board);
		
		
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
	}

}
