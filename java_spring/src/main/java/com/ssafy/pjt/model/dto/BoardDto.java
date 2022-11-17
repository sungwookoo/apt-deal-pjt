package com.ssafy.pjt.model.dto;

public class BoardDto {
	private int boardNo;
	private String userId;
	private String userName;
	private String subject;
	private String content;
	private int hit;
	private String createDate;
	private String type;

	public BoardDto() {
	}

	public BoardDto(int boardNo, String userId, String userName, String subject, String content, int hit,
			String createDate, String type) {
		super();
		this.boardNo = boardNo;
		this.userId = userId;
		this.userName = userName;
		this.subject = subject;
		this.content = content;
		this.hit = hit;
		this.createDate = createDate;
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardDto [boardNo=");
		builder.append(boardNo);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", subject=");
		builder.append(subject);
		builder.append(", content=");
		builder.append(content);
		builder.append(", hit=");
		builder.append(hit);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
