package edu.kh.jdbc.board.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.member.vo.Member;

public class BoardView {

	List<Board> boardList = new ArrayList<Board>();
	List<Comment> comList = new ArrayList<>();
	BoardService service = new BoardService();
	CommentService cService = new CommentService();
	Member loginMember;
	
	Scanner sc = new Scanner(System.in);

	public void boardMenu(Member LoginMember) {
		loginMember = LoginMember;
		int input = -1;
		
		do {
			System.out.println("*****게시판 기능******");
			System.out.println(" 1. 게시글 목록 조회");
			System.out.println(" 2. 게시글 상세 조회");
			System.out.println(" 3. 게시글 작성");
			System.out.println(" 4. 게시글 검색");
			System.out.println(" 0. 이전 메뉴료");
			System.out.print("메뉴 입력 : ");
			input = sc.nextInt();
			sc.nextLine();
			
			switch(input) {
			
			case 1: boardList(); break;
			case 2: boardDetail(loginMember); break;
			case 3: writeBoard(loginMember); break;
			case 4: searchBoard(loginMember); break;
			case 0: return;
			default: System.out.println("정확한 번호를 입력해주세요");
			
			}
		
		}while(input != 0);
		
		
		/*
		 * 게시판 기능 (Board View, Service, DAO, board-query.xml)
		 * 
		 * 1. 게시글 목록 조회(작성일 내림차순)
		 * 	  (게시글 번호, 제목[댓글 수], 작성자명, 작성일, 조회수 )
		 * 
		 * 2. 게시글 상세 조회(게시글 번호 입력 받음)
		 *    (게시글 번호, 제목, 내용, 작성자명, 작성일, 조회수, 
		 *     댓글 목록(작성일 오름차순 )
		 *     
		 *     2-1. 댓글 작성
		 *     2-2. 댓글 수정 (자신의 댓글만)
		 *     2-3. 댓글 삭제 (자신의 댓글만)
		 * 
		 *     // 자신이 작성한 글 일때만 메뉴 노출
		 *     2-4. 게시글 수정
		 *     2-5. 게시글 삭제
		 *     
		 *     
		 * 3. 게시글 작성(제목, 내용 INSERT) 
		 * 	-> 작성 성공 시 상세 조회 수행
		 * 
		 * 4. 게시글 검색(제목, 내용, 제목+내용, 작성자)
		 * 
		 * */
		
	}


	private void searchBoard(Member loginMember) {
		int input = -1;
		do {
			System.out.println("***게시글 검색***");
			System.out.println("1. 제목으로 검색");
			System.out.println("2. 내용으로 검색");
			System.out.println("3. 제목 + 내용으로 검색");
			System.out.println("4. 작성자로 검색");
			System.out.println("0. 이전 메뉴로");
			System.out.print("메뉴 선택 : ");
			input = sc.nextInt();
			sc.nextLine();
			
			String tit = "title";
			String con = "content";
			String titcon = "titcon";
			String wri = "writer";
			
			switch(input) {
			case 1: searchBoard(tit,loginMember); break;
			case 2: searchBoard(con,loginMember); break;
			case 3: searchBoard(titcon,loginMember); break;
			case 4: searchBoard(wri,loginMember); break;
			case 0: break;
			default : System.out.println("다시 입력하세요");
			}
			
		}while(input != 0);
		
	}

	private void searchBoard(String var, Member loginMember) {
		System.out.print("검색어를 입력하세요 : ");
		String searchInput = sc.nextLine();
		
		boardList = service.searchBoard(var, loginMember, searchInput);
		
		System.out.println("=======검색 결과======");
		if(boardList.size()==0) {
			System.out.println("검색 결과가 없습니다."); return;
		} else {
			for(Board b : boardList) {
				System.out.println(b.getBoardNO() + " | " + b.getBoardTitle() + "["+ b.getBoardCount() +"] | " + b.getMemberName() +" | " + b.getCreateDate() +" | " 
						+ b.getReadCount() + " | ");
			}
			
			Board board = new Board(); // 상세 메뉴에 보낼 게시글 
			System.out.print("게시글을 선택하세요 : ");
			int input = sc.nextInt();
			
			
			boolean flag = false;
			
			for(Board b : boardList) {
				if(b.getBoardNO() == input) {
					
					board = b;
					System.out.printf("%d | %s | %s |  %s  | %d \n|   %s    |\n", b.getBoardNO(), b.getBoardTitle(), b.getMemberName(), b.getCreateDate(), b.getBoardCount(), b.getBoardContent());
					System.out.println("================================");
					comList = cService.commentList(b.getBoardNO());
					for(Comment c : comList) {
						System.out.println( c.getCommentNo()+  " | " + c.getCommentContent() + " | " + c.getMemberName() + " | "+ c.getCreateDt());
					}
					
					flag= true;
				}
			}
			
			if(!flag) {
				System.out.println("찾는 번호가 없습니다.");
				return;
			}
			
			do {
				if(loginMember.getMemberNo() == board.getMemberNo()) {
					System.out.println("***메뉴***");
					System.out.println("1. 댓글 작성");
					System.out.println("2. 댓글 수정");
					System.out.println("3. 댓글 삭제");
					System.out.println("4. 게시글 수정");
					System.out.println("5. 게시글 삭제");
					System.out.println("0. 이전 메뉴로");
					commentMenu1(loginMember, board); break;
				}else {
					System.out.println("***메뉴***");
					System.out.println("1. 댓글 작성");
					System.out.println("2. 댓글 수정");
					System.out.println("3. 댓글 삭제");
					System.out.println("0. 이전 메뉴로");
					commentMenu2(loginMember, board); break;
				}
			
				
			}while(input != 0);
		}
	}


	private void writeBoard(Member loginMember) {
		
		System.out.print("글 제목을 입력하세요 : ");
		String titleInput = sc.nextLine();
		System.out.println("내용을 입력하세용 : ");
		String contentInput = sc.nextLine();
		
		int result = service.writeBoard(titleInput, contentInput, loginMember);
		
		if (result > 0) {
			System.out.println("글이 등록되었습니다.");
			boardDetail(loginMember);
		}else {
			System.out.println("등록 실패");
		}
		
	}


	public void boardList() {
		System.out.println("-----게시글 목록-----");
	
		
		
		try {
			boardList = service.boardList();
			for(Board b : boardList) {
				System.out.println(b.getBoardNO() + " | " + b.getBoardTitle() + "["+ b.getBoardCount() +"] | " + b.getMemberName() +" | " + b.getCreateDate() +" | " 
			+ b.getReadCount() + " | ");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	public void boardDetail(Member loginMember) {
		
		boardList();
		
		Board board = new Board(); // 상세 메뉴에 보낼 게시글 
		System.out.print("게시글을 선택하세요 : ");
		int input = sc.nextInt();
		
		
		boolean flag = false;
		
		for(Board b : boardList) {
			if(b.getBoardNO() == input) {
				
				board = b;
				System.out.printf("%d | %s | %s |  %s  | %d \n|   %s    |\n", b.getBoardNO(), b.getBoardTitle(), b.getMemberName(), b.getCreateDate(), b.getBoardCount(), b.getBoardContent());
				System.out.println("================================");
				comList = cService.commentList(b.getBoardNO());
				for(Comment c : comList) {
					System.out.println( c.getCommentNo()+  " | " + c.getCommentContent() + " | " + c.getMemberName() + " | "+ c.getCreateDt());
				}
				
				flag= true;
			}
		}
		
		if(!flag) {
			System.out.println("찾는 번호가 없습니다.");
			return;
		}
		
		do {
			if(loginMember.getMemberNo() == board.getMemberNo()) {
				System.out.println("***메뉴***");
				System.out.println("1. 댓글 작성");
				System.out.println("2. 댓글 수정");
				System.out.println("3. 댓글 삭제");
				System.out.println("4. 게시글 수정");
				System.out.println("5. 게시글 삭제");
				System.out.println("0. 이전 메뉴로");
				commentMenu1(loginMember, board); break;
			}else {
				System.out.println("***메뉴***");
				System.out.println("1. 댓글 작성");
				System.out.println("2. 댓글 수정");
				System.out.println("3. 댓글 삭제");
				System.out.println("0. 이전 메뉴로");
				commentMenu2(loginMember, board); break;
			}
		
			
		}while(input != 0);
	}


	public void commentMenu1(Member loginMember, Board board) { // 게시글 본인의 글일 경우
		int input = -1;
		do {
			System.out.print("메뉴 입력 : ");
			input = sc.nextInt();
			sc.nextLine();
			
			switch(input) {
			case 1: writeComment(loginMember, board); break; // 댓글 작성
			case 2:	editComment(loginMember, board); break; // 댓글 수정
			case 3:	deleteComment(loginMember, board); break;
			case 4:	editBoard(loginMember, board); break;
			case 5: deleteBoard(loginMember, board); break;
			default: System.out.println("정확한 번호를 입력하세요"); break;
			
				
			}
			
		}while(input != 0);
		
	}
	
	private void deleteBoard(Member loginMember, Board board) {
		int result = 0;
		while(true) {
			System.out.print("정말 삭제하시겠습니까?(Y/N) ");
			String input = sc.next().toUpperCase();
			if(input.equals("Y")) {
				result = service.deleteBoard(loginMember, board);
				if(result>0) {
					System.out.println("삭제 완료"); break;
				}else {
					System.out.println("삭제 실패");
					break;
				}
			}else if(input.equals("N")) {
				System.out.println("삭제를 취소합니다."); break;
			}else {
				System.out.println("다시 입력해주세요");
			}
			
		}
		
	}


	private void editBoard(Member loginMember, Board board) {
		int result = 0;
		System.out.print("수정할 내용을 입력하세요 : ");
		String content = sc.nextLine(); // 수정 내용 입력
		
		result = service.editBoard(content ,loginMember, board);
		if(result>0) {
			System.out.println("수정 완료"); 
		}
		else System.out.println("수정 실패"); 
		
	}


	private void deleteComment(Member loginMember, Board board) {
		int result = 0;
		
		for(Comment c : comList) { // comList를 조회해서 선택한 번호의 댓글을 가져온다.
			if(c.getBoardNo() == board.getBoardNO() && c.getMemberNo() == loginMember.getMemberNo()) {
				System.out.print("삭제할 댓글의 번호를 입력하세요 : ");
				int input = sc.nextInt();
				sc.nextLine();
				if(input == c.getCommentNo()) { // 정확한 댓글번호를 입력하면
					// 삭제 메서드 실행 후 break;
					
					result =  cService.deleteComment(c);
					if(result>0) {
						System.out.println("삭제 완료"); break;
					}else System.out.println("삭제 실패"); break;
				}else{
					System.out.println("잘못된 번호를 입력했습니다."); break;
				}
			}
		}	
	}

	public void editComment(Member loginMember, Board board) { // 댓글 수정 (본인 댓글만)
		int result = 0;
		
		for(Comment c : comList) { // comList를 조회해서 선택한 번호의 댓글을 가져온다.
			if(c.getBoardNo() == board.getBoardNO() && c.getMemberNo() == loginMember.getMemberNo()) {
				System.out.print("수정할 댓글의 번호를 입력하세요 : ");
				int input = sc.nextInt();
				sc.nextLine();
				if(input == c.getCommentNo()) { // 정확한 댓글번호를 입력하면
					// 수정 메서드 실행 후 break;
					
					System.out.print("수정 댓글을 입력하세요 : ");
					String comment = sc.nextLine();
					result =  cService.editComment(comment , c);
					
					if(result>0) {
						System.out.println("수정 완료"); break;
					}else System.out.println("수정 실패"); break;
				}else{
					System.out.println("잘못된 번호를 입력했습니다."); break;
				}
			}
			System.out.println("잘못 입력했습니다.");
		}	
	}

	public void writeComment(Member loginMember, Board board) {
		System.out.print("댓글을 입력하세요 : ");
		String comment = sc.nextLine();
		
		int result = cService.writeComment(comment ,loginMember, board);
		
		if(result>0) {
			System.out.println("댓글 등록 완료");
		}else {
			System.out.println("등록 실패");
		}
		
	}

	public void commentMenu2(Member loginMember, Board board) { // 게시글 본인의 글이 아닐 경우
		int input = -1;
		do {
			System.out.print("메뉴 입력 : ");
			input = sc.nextInt();
			sc.nextLine();
			
			
			switch(input) {
			case 1: writeComment(loginMember, board); break;
			case 2:	editComment(loginMember, board); break;
			case 3:	deleteComment(loginMember, board); break;
			default: System.out.println("정확한 번호를 입력하세요 "); break;
				
			}
			
		}while(input != 0);
	}
}
