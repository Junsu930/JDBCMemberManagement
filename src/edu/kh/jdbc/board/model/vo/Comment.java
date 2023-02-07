package edu.kh.jdbc.board.model.vo;

public class Comment {


	int commentNo;
	String commentContent;
	String createDt;
	String deleteFl;
	int memberNo;
	int boardNo;
	String memberName;
	
	public Comment(int commentNo, String commentContent, String createDt, String deleteFl, int memberNo, int boardNo, String memberName) {
		super();
		this.commentNo = commentNo;
		this.commentContent = commentContent;
		this.createDt = createDt;
		this.deleteFl = deleteFl;
		this.memberNo = memberNo;
		this.boardNo = boardNo;
		this.memberName = memberName;
	}
	
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
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
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	
	
}
