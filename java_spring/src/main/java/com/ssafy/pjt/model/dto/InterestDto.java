package com.ssafy.pjt.model.dto;

public class InterestDto {
	String dongCode;
	String userId;
	String sidoName;
	String gugunName;
	String dongName;
	
	public InterestDto(){}

	public InterestDto(String dongcode, String userId, String sidoName, String gugunName, String dongName) {
		super();
		this.dongCode = dongcode;
		this.userId = userId;
		this.sidoName = sidoName;
		this.gugunName = gugunName;
		this.dongName = dongName;
	}

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSidoName() {
		return sidoName;
	}

	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}

	public String getGugunName() {
		return gugunName;
	}

	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}

	public String getDongName() {
		return dongName;
	}

	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterestDto [dongCode=");
		builder.append(dongCode);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", sidoName=");
		builder.append(sidoName);
		builder.append(", gugunName=");
		builder.append(gugunName);
		builder.append(", dongName=");
		builder.append(dongName);
		builder.append("]");
		return builder.toString();
	}
}
