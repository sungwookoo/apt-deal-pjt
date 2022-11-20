package com.ssafy.pjt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.BuildingDto;
import com.ssafy.pjt.model.dto.HouseDto;
import com.ssafy.pjt.model.dto.InterestDto;

public interface HouseDao {

	List<Map<String, String>> selectSidoNames() throws SQLException;

	List<Map<String, String>> selectGugunNames(String sidoCode) throws SQLException;

	List<Map<String, String>> selectDongNames(String gugunCode) throws SQLException;

	List<HouseDto> selectDeals(Map<String, String> map) throws SQLException;

	List<HouseDto> selectBuildingDetail(String aptCode) throws SQLException;
	
	List<BuildingDto> selectBuildingInfo(Map<String, String> map) throws SQLException;

	List<InterestDto> selectInterest(String userId) throws SQLException;

	int interestInsert(Map<String, String> map) throws SQLException;

	int interestDelete(Map<String, String> map) throws SQLException;

	int deleteAptDeal(String no) throws SQLException;

	int modifyAptDeal(Map<String, String> map) throws SQLException;

	List<String> selectAptCodes(String dongCode) throws SQLException;

	List<String> selectAptDeals(List<String> aptCode) throws SQLException;
	
	

}
