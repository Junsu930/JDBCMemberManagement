package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Comment;

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

}
