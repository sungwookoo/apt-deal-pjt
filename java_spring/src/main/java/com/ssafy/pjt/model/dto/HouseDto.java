package com.ssafy.pjt.model.dto;

public class HouseDto {
	String aptCode;
	String dongCode;
	String apartmentName;
	String dealAmount;
	String area;
	int dealYear;
	int dealMonth;
	int dealDay;
	String lat;
	String lng;
	String no;
	
	HouseDto(){}

	public HouseDto(String aptCode, String dongCode, String apartmentName, String dealAmount, String area, int dealYear,
			int dealMonth, int dealDay, String lat, String lng, String no) {
		super();
		this.aptCode = aptCode;
		this.dongCode = dongCode;
		this.apartmentName = apartmentName;
		this.dealAmount = dealAmount;
		this.area = area;
		this.dealYear = dealYear;
		this.dealMonth = dealMonth;
		this.dealDay = dealDay;
		this.lat = lat;
		this.lng = lng;
		this.no = no;
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

	public int getDealYear() {
		return dealYear;
	}

	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}

	public int getDealMonth() {
		return dealMonth;
	}

	public void setDealMonth(int dealMonth) {
		this.dealMonth = dealMonth;
	}

	public int getDealDay() {
		return dealDay;
	}

	public void setDealDay(int dealDay) {
		this.dealDay = dealDay;
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

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HouseDto [aptCode=");
		builder.append(aptCode);
		builder.append(", dongCode=");
		builder.append(dongCode);
		builder.append(", apartmentName=");
		builder.append(apartmentName);
		builder.append(", dealAmount=");
		builder.append(dealAmount);
		builder.append(", area=");
		builder.append(area);
		builder.append(", dealYear=");
		builder.append(dealYear);
		builder.append(", dealMonth=");
		builder.append(dealMonth);
		builder.append(", dealDay=");
		builder.append(dealDay);
		builder.append(", lat=");
		builder.append(lat);
		builder.append(", lng=");
		builder.append(lng);
		builder.append(", no=");
		builder.append(no);
		builder.append("]");
		return builder.toString();
	}
}
