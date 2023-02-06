package edu.kh.jdbc.main.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.commit;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;
import static edu.kh.jdbc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.member.vo.Member;


// Service : 데이터 가공, 트랜잭션 제어 처리
public class MainService {
	
	private MainDAO dao = new MainDAO();


	/** 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 */
	public Member login(String memberId, String memberPw) throws Exception {
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		// 2. DAO 메서드 호출 후 결과 반환받기
		Member loginMember = dao.login(conn, memberId, memberPw);
		
		// 3. 커넥션 반환
		close(conn);
		
		return loginMember;
	}


	/** 아이디 중복 검사 서비스
	 * @param memberId
	 * @return result
	 * @thorws Exception
	 */
	public int idDupCheck(String memberId) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.idDupCheck(conn, memberId);

		close(conn);
		
		return result;
	}


	/** 회원가입 서비스
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Member member) throws Exception{
		// 1. 커넥션 생성
		Connection conn = getConnection();
		
		// 2. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.signUp(conn, member);
				
		// 3. 트랜잭션 제어 처리
		if(result>0) commit(conn);
		else rollback(conn);
		
		// 4. 커넥션 반환
		close(conn);
		
		return result;
	}


	public List<Member> selectAll() throws Exception{
		List<Member> memberList = new ArrayList<Member>();
		
		Connection conn = getConnection();
		
		memberList = dao.selectAll(conn);
		
		
		close(conn);
		return memberList;
	}


	public int updateMember(Member loginMember) throws Exception {
		Connection conn = getConnection();
		
		int result = dao.updateMember(conn, loginMember);
		
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}


	/** 탈퇴용 메서드
	 * @param loginMember
	 * @return result
	 */
	public int secession(Member loginMember) throws Exception {
		int result = 0;
		Connection conn = getConnection();
		
		result = dao.secession(conn, loginMember);
		
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
	


}
