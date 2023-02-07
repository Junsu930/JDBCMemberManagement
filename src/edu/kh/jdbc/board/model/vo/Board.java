package edu.kh.jdbc.board.model.vo;

public class Board {
	
	private int boardNO;
	private String boardTitle;
	private String boardContent;
	private String createDate;
	private int readCount;
	private String deleteFl;
	private int memberNo;
	private String memberName;
	private int boardCommentCount;
	

	public Board() {}

	
	public Board(int boardNO, String boardTitle, String createDate, int readCount, String memberName, int boardCommentCount) {
		super();
		this.boardNO = boardNO;
		this.boardTitle = boardTitle;
		this.createDate = createDate;
		this.readCount = readCount;
		this.memberName = memberName;
		this.boardCommentCount = boardCommentCount;
	}


	public Board(int boardNO, String boardTitle, String boardContent, String createDate, int readCount, String deleteFl,
			int memberNo, String memberName, int boardCommentCount) {
		super();
		this.boardNO = boardNO;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.createDate = createDate;
		this.readCount = readCount;
		this.deleteFl = deleteFl;
		this.memberNo = memberNo;
		this.memberName = memberName;
		this.boardCommentCount = boardCommentCount;
	}
	
	public int getBoardCount() {
		return boardCommentCount;
	}
	public void setBoardCount(int boardCount) {
		this.boardCommentCount = boardCount;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getBoardNO() {
		return boardNO;
	}
	public void setBoardNO(int boardNO) {
		this.boardNO = boardNO;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public String getDeleteFl() {
		return deleteFl;
	}
	public void setDeleteFl(String deleteFl) {
		this.deleteFl = deleteFl;
	}
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	
	

	
}
