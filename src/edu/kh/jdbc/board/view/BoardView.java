package edu.kh.jdbc.board.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;

public class BoardView {

	List<Board> boardList = new ArrayList<Board>();
	List<Comment> comList = new ArrayList<>();
	BoardService service = new BoardService();
	CommentService cService = new CommentService();
	
	Scanner sc = new Scanner(System.in);

	public void boardMenu() {
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
			case 2: boardDetail(); break;
			case 3: 
			case 4: 
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

	public void boardDetail() {
		
		boardList();
		
		System.out.print("게시글을 선택하세요 : ");
		int input = sc.nextInt();
		
		
		boolean flag = false;
		
		for(Board b : boardList) {
			if(b.getBoardNO() == input) {
	
				System.out.printf("%d | %s | %s |  %s  | %d \n|   %s    |\n", b.getBoardNO(), b.getBoardTitle(), b.getMemberName(), b.getCreateDate(), b.getBoardCount(), b.getBoardContent());
				
				comList = cService.commentList(b.getBoardNO());
				for(Comment c : comList) {
					System.out.println(c.getCommentNo() + " | " + c.getCommentContent() + " | " + c.getMemberName() + " | "+ c.getCreateDt());
				}
				
				flag= true;
			}
		}
		
		if(!flag) {
			System.out.println("찾는 번호가 없습니다.");
		}
	}
}
