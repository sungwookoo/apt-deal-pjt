package com.ssafy.pjt.service;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.pjt.AbstractTest;
import com.ssafy.pjt.model.dto.HouseDto;
import com.ssafy.pjt.model.service.HouseService;

public class HouseServiceTest extends AbstractTest {
	private static final Logger logger = LoggerFactory.getLogger(HouseServiceTest.class);
	
	// 픽스쳐
	@Autowired
	private HouseService houseService;
	
	@Test
	public void selectGugunTest() throws SQLException {
		String sidoCode = "11";
		
		List<Map<String, String>> result = houseService.selectGugunNames(sidoCode);
		logger.debug("--------------start3");
		result.forEach(o -> logger.debug(o.get("dongCode") +", " +o.get("gugunName")));
		assertEquals(25, result.size());
	}
	
	@Test
	public void selectDealsTest() throws SQLException {
		Map<String, String> map = new HashMap<>();
		map.put("code", "1111011500");
		List<HouseDto> result = houseService.selectDeals(map);
		assertEquals(310, result.size());
		
		map.put("year", "2015");
		map.put("month", "1");
		result = houseService.selectDeals(map);
		assertEquals(4, result.size());
	}
}
