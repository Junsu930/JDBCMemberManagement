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
		
		// 게시글 번호 생성 dao 호출
		// 왜? 동시에 여러 사람이 게시글을 등록하면
		// 시퀀스가 한번에 증가하여 CURRVAL 구문을 이용하면 문제 발생
		// -> 게시글 등록 서비스를 호출한 순서대로
		// 미리 게시글 번호를 생성해서 얻어온 다음 이를 이용해 insert 진행
		int boardNo = dao.nextBoardNo(conn);
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
