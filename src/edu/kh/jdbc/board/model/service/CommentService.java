package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.member.vo.Member;

public class CommentService {
	CommentDAO dao = new CommentDAO();
	Connection conn;
	List<Comment> commentList = new ArrayList<>();

	public List<Comment> commentList(int boardNo) {
		conn = getConnection();
		commentList = dao.commentList(conn, boardNo);
		
		close(conn);
		
		return commentList;
	}

	public int writeComment(String comment ,Member loginMember, Board board) {
		int result = 0;
		conn = getConnection();
		result = dao.writeComment(conn, comment, loginMember, board);
		
		
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int editComment(String comment ,Comment com) {
		int result = 0;
		conn = getConnection();
		
		result = dao.editComment(conn, comment, com);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
		
	}

	public int deleteComment(Comment com) {
		int result = -1;
		conn = getConnection();
		
		result = dao.deleteComment(conn, com);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}

}
