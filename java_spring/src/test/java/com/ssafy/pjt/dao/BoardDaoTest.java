package com.ssafy.pjt.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.pjt.AbstractTest;
import com.ssafy.pjt.model.dto.BoardDto;
import com.ssafy.pjt.model.mapper.BoardDao;

public class BoardDaoTest extends AbstractTest {
	private static final Logger logger = LoggerFactory.getLogger(BoardDaoTest.class);

	// 픽스쳐
	@Autowired
	BoardDao boardDao;

	@Test
	public void test() throws SQLException {
		List<BoardDto> list1 = boardDao.searchBoard("".trim());
		for(BoardDto dto : list1) {
			logger.debug(dto.getSubject() +", " +dto.getContent() +"11111");
		}
		assertEquals(4, list1.size());

		
		List<BoardDto> list2 = boardDao.searchBoard("테스트".trim());
		for(BoardDto dto : list2) {
			logger.debug(dto.getSubject() +", " +dto.getContent() +"22222");
		}
		assertEquals(3, list2.size());
	}
}
