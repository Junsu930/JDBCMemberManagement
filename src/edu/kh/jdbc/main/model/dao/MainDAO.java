package edu.kh.jdbc.main.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

public class MainDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Properties prop = null;
	// Map<String, String> 제한, XML 파일 읽고 쓰는 데 특화
	private Properties memberProp = null;
	
	public MainDAO() {
		
		try {
			prop = new Properties(); // Properties 객체 생성
			prop.loadFromXML(new FileInputStream("main-query.xml"));
			// main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장
			memberProp = new Properties();
			memberProp.loadFromXML(new FileInputStream("member-query.xml"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		
		// 1. 결과 저장용 변수 선언
		Member loginMember = null;
		
		try {
			// 2. SQL 얻어오기(main-query.xml에 작성된 SQL)
			String sql = prop.getProperty("login");
			
			// 3. PreparedStatement 객채 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?에 알맞은 값 대입
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			// 5. SQL(SELECT) 수행 결과(ResultSet) 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과가 있을 경우 컬럼값을 모두 얻어와
			// 	  컬럼값을 모두 얻어와
			//    Member 객체를 생성해서 loginMember 대입
			if(rs.next()) {
				
				loginMember = new Member(rs.getInt("MEMBER_NO"), memberId, memberPw, rs.getString("MEMBER_NM"), 
						rs.getString("MEMBER_GENDER"), rs.getString("ENROLL_DATE"));
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}	
		return loginMember;
	}


	/** 아이디 중복 검사 DAO
	 * @param conn
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String memberId) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("idDupCheck");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);  // 컬럼 순서
			}
			
		}finally{
			close(rs);
			close(pstmt);
		}
		
		return result;
	}

	/** 화원 가입 서비스
	 * @param conn
	 * @param member
	 * @return result
	 */
	public int signUp(Connection conn, Member member) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			result = pstmt.executeUpdate();
			
			
		}finally {
			close(pstmt);
		}
		return result;
	}



	public List<Member> selectAll(Connection conn) throws Exception {
		List<Member> memberList = new ArrayList<Member>();
		try {
			String sql = memberProp.getProperty("selectAll");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				memberList.add(new Member(rs.getString("MEMBER_ID"), rs.getString("MEMBER_NM"), rs.getString("MEMBER_GENDER")));
			}
			
		}finally {
			close(rs);
			close(stmt);
		}
		return memberList;
	}

	/** 정보 수정 메서드
	 * @param conn
	 * @param loginMember
	 * @return result
	 */
	public int updateMember(Connection conn, Member loginMember) throws Exception {
		int result = 0;
		try {
			String sql = memberProp.getProperty("update");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginMember.getMemberName());
			pstmt.setString(2, loginMember.getMemberGender());
			pstmt.setString(3, loginMember.getMemberPw());
			pstmt.setString(4, loginMember.getMemberId());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int secession(Connection conn, Member loginMember) throws Exception {
		
		
		int result = 0;
		
		try {
			String sql = memberProp.getProperty("secession");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginMember.getMemberId());
			
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	

}
