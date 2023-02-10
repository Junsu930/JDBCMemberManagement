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
import edu.kh.jdbc.member.vo.Member;

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
			e.printStackTrace();
		}
	}

	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @return boardList
	 * @throws Exception
	 */
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
		close(stmt);
		
		return boardList;
	}

	public int editBoard(Connection conn, String content, Member loginMember, Board board) {
		int result = 0;

		try {
			String sql = prop.getProperty("editBoard");
			pstmt = conn.prepareStatement(sql);
			//UPDATE BOARD SET BOARD_CONTENT = ? WHERE BOARD_NO = ?
			pstmt.setString(1, content);
			pstmt.setInt(2, board.getBoardNO());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteBoard(Connection conn, Member loginMember, Board board) {
		int result = 0;
		try {
			String sql = prop.getProperty("deleteBoard");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getBoardNO());
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		
		return result;
	}

	public int writeBoard(Connection conn, String titleInput, String contentInput, Member loginMember) {
		int result = 0;
		try {
			String sql = prop.getProperty("writeBoard");
			//INSERT INTO BOARD VALUES(SEQ_BOARD_NO.NEXTVAL, ?, ?, DEFAULT, 0, DEFAULT, ?)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, titleInput);
			pstmt.setString(2, contentInput);
			pstmt.setInt(3, loginMember.getMemberNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<Board> searchBoard(Connection conn, String var, Member loginMember, String searchInput) {
		List<Board> boardList = new ArrayList<>();
		searchInput = "%" + searchInput + "%";
		String sql;
		if(var.equals("titcon")) {
			sql = prop.getProperty("titcon");
		}else if(var.equals("writer")) {
			sql = prop.getProperty("writer");
		}else if(var.equals("title")){
			sql = prop.getProperty("title");
		}else {
			sql = prop.getProperty("content");
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			if(var.equals("titcon")) {
				pstmt.setString(1, searchInput);
				pstmt.setString(2, searchInput);
			}else {
				pstmt.setString(1, searchInput);
			}
			
			rs = pstmt.executeQuery();
			
			
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
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return boardList;
	}

	public int count(Connection conn, Board board) {
		int result = 0;
		try {
			String sql = prop.getProperty("count");
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getBoardNO());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}

	public int nextBoardNo(Connection conn) {
		return 0;
	}

}
