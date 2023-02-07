package edu.kh.jdbc.board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static edu.kh.jdbc.common.JDBCTemplate.*;

import edu.kh.jdbc.board.model.vo.Board;

public class BoardDAO {
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Properties prop = null;
	Properties propc = null;
	
	public BoardDAO(){
		try {
			prop = new Properties();
			propc = new Properties();
			prop.loadFromXML(new FileInputStream("board-query.xml"));
			propc.loadFromXML(new FileInputStream("comment-query.xml"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public List<Board> boardList(Connection conn) throws Exception{
		String sql = prop.getProperty("boardList");
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		List<Board> boardList = new ArrayList<>();
		
		while(rs.next()) {
			int boardNo = rs.getInt("BOARD_NO");
			String boardTitle = rs.getString("BOARD_TITLE");
			String boardContent = rs.getString("BOARD_CONTENT");
			String createDate = rs.getString("CREATE_DT");
			int readCount = rs.getInt("READ_COUNT");
			String deleteFl = rs.getString("DELETE_FL");
			String memberName = rs.getString("MEMBER_NM");
			int memberNo = rs.getInt("MEMBER_NO");
			int boardCommentCount = rs.getInt("COMMENT_COUNT");
			
			
			Board board = new Board(boardNo, boardTitle, boardContent, createDate, readCount, deleteFl, memberNo, memberName, boardCommentCount);
			
			boardList.add(board);
		}
		
		close(rs);
		close(pstmt);
		return boardList;
	}

}
