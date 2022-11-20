package com.ssafy.pjt.model.dto;

public class BuildingDto {
	private String aptCode;
	private  String dongCode;
	private  String apartmentName;
	private String dealAmount;
	private  String area;
	private  int buildYear;
	private  int dealYear;
	private  String lat;
	private  String lng;
	
	public BuildingDto () {}

	public BuildingDto(String aptCode, String dongCode, String apartmentName, String dealAmount, String area,
			int buildYear, int dealYear, String lat, String lng) {
		super();
		this.aptCode = aptCode;
		this.dongCode = dongCode;
		this.apartmentName = apartmentName;
		this.dealAmount = dealAmount;
		this.area = area;
		this.buildYear = buildYear;
		this.dealYear = dealYear;
		this.lat = lat;
		this.lng = lng;
	}

	public String getAptCode() {
		return aptCode;
	}

	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}

	public int getDealYear() {
		return dealYear;
	}

	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BuildingDto [aptCode=");
		builder.append(aptCode);
		builder.append(", dongCode=");
		builder.append(dongCode);
		builder.append(", apartmentName=");
		builder.append(apartmentName);
		builder.append(", dealAmount=");
		builder.append(dealAmount);
		builder.append(", area=");
		builder.append(area);
		builder.append(", buildYear=");
		builder.append(buildYear);
		builder.append(", dealYear=");
		builder.append(dealYear);
		builder.append(", lat=");
		builder.append(lat);
		builder.append(", lng=");
		builder.append(lng);
		builder.append("]");
		return builder.toString();
	}
	
}
