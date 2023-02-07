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

import edu.kh.jdbc.board.model.vo.Comment;

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

}
