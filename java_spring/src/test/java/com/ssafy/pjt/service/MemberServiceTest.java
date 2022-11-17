package com.ssafy.pjt.service;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.pjt.AbstractTest;
import com.ssafy.pjt.model.dto.MemberDto;
import com.ssafy.pjt.model.service.MemberService;

public class MemberServiceTest extends AbstractTest {
	private static final Logger logger = LoggerFactory.getLogger(MemberServiceTest.class);

	// 픽스쳐
	@Autowired
	private MemberService memberService;

	@Test
	public void test() throws SQLException {

		List<MemberDto> list = memberService.userList("ES".trim());
		for (MemberDto dto : list) {
			logger.debug(dto + "1111");
		}
		assertEquals(1, list.size());

		List<MemberDto> list2 = memberService.userList("".trim());
		for (MemberDto dto : list2) {
			logger.debug(dto + "2222");
		}
		assertEquals(2, list2.size());

	}
}
