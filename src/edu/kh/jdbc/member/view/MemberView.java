package edu.kh.jdbc.member.view;

import java.util.List;

import java.util.Scanner;
import static edu.kh.jdbc.main.view.MainView.*;
import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.main.view.MainView;
import edu.kh.jdbc.member.vo.Member;

public class MemberView {
	
	
	private Scanner sc = new Scanner(System.in);
	private Member loginMember = null;
	private List<Member> memberList = null;
	private MainService service = new MainService();
	

	/** 회원 기능 메뉴
	 * @param LoginMember (회원 기능 정보)
	 */
	public void memberMenu(Member LoginMember) {
		
		this.loginMember = LoginMember;
		
		int input = -1;
		/* 회원기능 (Member View, Service, DAO, member-query.xml)
		 * 
		 * 1. 내 정보 조회  selectMyInfo();
		 * 2. 회원 목록 조회(아이디, 이름, 성별) selectAll() 
		 * 3. 내 정보 수정(이름, 성별)  updateMember() 
		 * 4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인) updatePw()
		 * 5. 회원 탈퇴 secession()
		 */
		
		do {
			System.out.println("*****회원 기능*****");
			System.out.println(" 1. 내 정보 조회");
			System.out.println(" 2. 회원 목록 조회(아이디, 이름, 성별)");
			System.out.println(" 3. 내 정보 수정(이름, 성별)");
			System.out.println(" 4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)");
			System.out.println(" 5. 회원 탈퇴");
			System.out.println(" 9. 이전 메뉴로");
			System.out.print("메뉴 선택 : ");
			input = sc.nextInt();
			sc.nextLine();
			
			switch(input) {
			
			case 1: selectMyInfo(); break;
			case 2: selectAll(); break;
			case 3: updateMember(loginMember); break;
			case 4: updatePw(loginMember); break;
			case 5: if(secession(loginMember) == 1) {
				input=0; break;
			}else{
				break;
			}
			case 9: input = -1; return;
			default : System.out.println("다시 입력해주세요"); break;
			}
			
			
		}while(input != 0);
		
	
	}
	
	private int secession(Member loginMember) {
		while(true) {
			System.out.print("정말 탈퇴하시겠습니까?(Y/N) : ");
			String input = sc.next().toUpperCase();
			sc.nextLine();
			
			if(input.equals("Y")|| input.equals("N")) { // 탈퇴 진행 여부 
				if(input.equals("Y")) {
					System.out.print("비밀번호를 입력하세요 : "); // 탈퇴 시 비밀번호 받기
					String pw = sc.nextLine();
					if(loginMember.getMemberPw().equals(pw)) {
						try {
							int result = service.secession(loginMember);
							
							if(result > 0) {
								System.out.println("탈퇴가 완료되었습니다.");
								// 완료되면 로그아웃되고 초기 메뉴로 돌아간다.
								LoginMember=null;
								return 1;
								
							}else{
								System.out.println("탈퇴 중 오류 발생");
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}else { // 비밀번호가 일치하지 않을 경우
						System.out.println("비밀번호가 일치하지 않습니다.");
						break;
					}
				}else { // N를 선택했을 경우
					System.out.println("넵");
					break;
				}
				
			}else {
				System.out.println("Y나 N 중 하나를 입력해주세요");
				System.out.println();
			}
		}
		return 0;
	}

	private void updatePw(Member loginMember) {
		
		while(true) {
			
			System.out.println("***비밀번호 변경***");
			System.out.println();
			System.out.print("비밀번호를 입력하세요 : ");
			String inputPassword = sc.nextLine();
			
			if(loginMember.getMemberPw().equals(inputPassword)) {
				System.out.println();
				System.out.print("변경할 비밀번호 입력 : ");
				String pw1 = sc.nextLine();
				System.out.println();
				System.out.print("비밀번호 확인 : ");
				String pw2 = sc.nextLine();
				
				if(pw1.equals(pw2)) {
					loginMember.setMemberPw(pw2);
					try {
						int result = service.updateMember(loginMember);
						
						if(result>0) {
							System.out.println();
							System.out.println("수정 완료");
							break;
						}else {
							System.out.println();
							System.out.println("수정 실패");
							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else {
					System.out.println("비밀번호가 일치하지 않습니다.");
					break;
				}
				
			}else {
				System.out.println("비밀번호가 일치하지 않습니다.");
				break;
			}
		}
		
	}

	/** 
	 * 회원 목록 조회
	 */
	private void selectAll() {
		System.out.println("회원 목록 조회(탈퇴 회원 미포함)");
		MainService service = new MainService();
		try {
			memberList = service.selectAll(); // 회원 목록 조회 서비스 호출 후 결과 반환 받기
			// 조회 결과 없으면 없다고 출력
			if(memberList.isEmpty()) {
				System.out.println("조회 결과가 없습니다.");
			}else {
				for(Member i : memberList) {
					System.out.println(i.toString2()); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 내 정보 조회 메서드
	 * 
	 */
	public void selectMyInfo() {
		System.out.println("내 정보 조회");
		System.out.println(loginMember.toString());
	}
	
	/** 멤버 정보 수정 메서드
	 * @param loginMember
	 */
	public void updateMember(Member loginMember) {
		System.out.println("***정보 수정***");
		System.out.print("변경할 이름 : ");
		String name = sc.nextLine();
		loginMember.setMemberName(name);
		String gender = null;
		while(true) {
			System.out.print("변경할 성별 입력(M/F) : ");
			gender = sc.next().toUpperCase();
			
			System.out.println();
			if(gender.equals("M") || gender.equals("F")) {
				loginMember.setMemberGender(gender);
				break;
			}else {
				System.out.println("[M 또는 F만 입력하세요!");
			}
		}
		
		try {
			int result = service.updateMember(loginMember);
			
			if(result > 0) {
				System.out.println("변경 완료");
			}else {
				System.err.println(result);
				System.out.println("변경 중 오류 발생");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("변경 중 오류 발생");
		}
	}
}
