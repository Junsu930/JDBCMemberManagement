package edu.kh.jdbc.board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static edu.kh.jdbc.common.JDBCTemplate.*;

import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.member.vo.Member;

public class CommentDAO {
	Properties prop;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	
	public CommentDAO(){
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("comment-query.xml"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public List<Comment> commentList(Connection conn, int boardNo) {
		List<Comment> commentList = new ArrayList<>();
		
		try {
			
			String sql = prop.getProperty("commentList");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int commentNo = rs.getInt("COMMENT_NO");
				String commentContent = rs.getString("COMMENT_CONTENT");
				String createDt = rs.getString("CREATE_DT");
				String deleteFl = rs.getString("DELETE_FL");
				int memberNo = rs.getInt("MEMBER_NO");
				String memberName = rs.getString("MEMBER_NM");
				
				Comment cm = new Comment(commentNo, commentContent, createDt, deleteFl, memberNo, boardNo, memberName);
				commentList.add(cm);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return commentList;
	}

	public int writeComment(Connection conn, String comment, Member loginMember, Board board) { // 코멘트 작성 메서드
		
		int result = 0;
		try {
			String sql = prop.getProperty("writeComment");
			pstmt = conn.prepareStatement(sql);
			// INSERT INTO "COMMENT"(COMMENT_NO, COMMENT_CONTENT, MEMBER_NO, BOARD_NO) VALUES (?, ?, ?, ?)
			pstmt.setString(1, comment);
			pstmt.setInt(2, loginMember.getMemberNo());
			pstmt.setInt(3, board.getBoardNO());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int editComment(Connection conn ,String comment, Comment com) {
		int result = 0;
		try {
			String sql = prop.getProperty("editComment");
			pstmt = conn.prepareStatement(sql);
			// UPDATE "COMMENT" SET COMMENT_CONTENT = ? WHERE COMMENT_NO = ?
			pstmt.setString(1, comment);
			pstmt.setInt(2, com.getCommentNo());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteComment(Connection conn, Comment com) {
		int result = 0;
		try {
			//DELETE FROM "COMMENT" WHERE COMMENT_NO = ?
			String sql = prop.getProperty("deleteComment");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, com.getCommentNo());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			close(pstmt);
		}
		return result;
	}

}
