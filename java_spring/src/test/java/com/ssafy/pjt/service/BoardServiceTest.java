package com.ssafy.pjt.service;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.pjt.AbstractTest;
import com.ssafy.pjt.model.dto.BoardDto;
import com.ssafy.pjt.model.service.BoardService;

public class BoardServiceTest extends AbstractTest {
	private static final Logger logger = LoggerFactory.getLogger(BoardServiceTest.class);

	// 픽스쳐
	@Autowired
	private BoardService boardService;

	@Test
	public void test() throws SQLException {
		List<BoardDto> list1 = boardService.getSearchBoard("".trim());
		for(BoardDto dto : list1) {
			logger.debug(dto.getSubject() +", " +dto.getContent() +"11111");
		}
		assertEquals(4, list1.size());

		
		List<BoardDto> list2 = boardService.getSearchBoard("테스트".trim());
		for(BoardDto dto : list2) {
			logger.debug(dto.getSubject() +", " +dto.getContent() +"22222");
		}
		assertEquals(3, list2.size());
	}
}
