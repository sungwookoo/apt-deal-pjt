package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.HouseDto;
import com.ssafy.pjt.model.dto.InterestDto;
import com.ssafy.pjt.model.mapper.HouseDao;

@Service
public class HouseServiceImpl implements HouseService {
	
	// 동작.
	private HouseDao houseDao;

	@Autowired
	public HouseServiceImpl(HouseDao houseDao) {
		this.houseDao = houseDao;
	}
	
	
	@Override
	public List<Map<String, String>> selectSidoNames() throws SQLException {
		return houseDao.selectSidoNames();
	}
	
	@Override
	public List<Map<String, String>> selectGugunNames(String sidoCode) throws SQLException {
		return houseDao.selectGugunNames(sidoCode);
	}
	
	@Override
	public List<Map<String, String>> selectDongNames(String gugunCode) throws SQLException {
		return houseDao.selectDongNames(gugunCode);
	}
	
	@Override
	public List<HouseDto> selectDeals(Map<String, String> map) throws SQLException {
		return houseDao.selectDeals(map);
	}
	
	@Override
	public List<InterestDto> selectInterest(String userId) throws SQLException {
		return houseDao.selectInterest(userId);
	}
	
	@Override
	public int interestRegister(Map<String, String> map) throws SQLException {
		return houseDao.interestInsert(map);
	}
	
	@Override
	public int interestDelete(Map<String, String> map) throws SQLException {
		
		return houseDao.interestDelete(map);
	}
	
	@Override
	public int deleteAptDeal(String no) throws SQLException {
		return houseDao.deleteAptDeal(no);
	}

	@Override
	// String deal, String area, String year, String month, String day, String no
	public int modifyAptDeal(Map<String, String> map) throws SQLException {
		return houseDao.modifyAptDeal(map);
	}
	
	@Override
	public List<String> selectAptCodes(String dongCode) throws SQLException {
		return houseDao.selectAptCodes(dongCode);
	}
	
	@Override
	public List<String> selectAptDeals(List<String> aptCodes) throws SQLException {
		return houseDao.selectAptDeals(aptCodes);
	}
}
