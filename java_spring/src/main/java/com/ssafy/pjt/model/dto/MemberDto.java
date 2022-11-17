package com.ssafy.pjt.model.dto;

public class MemberDto {
	private String userId;
	private String userName;
	private	String userPassword;
	private String userPhone;
	private String emailId;
	private String emailDomain;
	private String joinDate;
	private String userType;
	
	public MemberDto() {}

	public MemberDto(String userId, String userName, String userPassword, String userPhone, String emailId,
			String emailDomain, String joinDate, String userType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.emailId = emailId;
		this.emailDomain = emailDomain;
		this.joinDate = joinDate;
		this.userType = userType;
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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberDto [userId=");
		builder.append(userId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userPassword=");
		builder.append(userPassword);
		builder.append(", userPhone=");
		builder.append(userPhone);
		builder.append(", emailId=");
		builder.append(emailId);
		builder.append(", emailDomain=");
		builder.append(emailDomain);
		builder.append(", joinDate=");
		builder.append(joinDate);
		builder.append(", userType=");
		builder.append(userType);
		builder.append("]");
		return builder.toString();
	}
}
