package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.MemberDto;

public interface MemberService {

	MemberDto loginMember(Map<String, String> map) throws SQLException;

	int joinMember(MemberDto memberDto) throws SQLException;

	String findPwd(MemberDto memberDto) throws SQLException;

	MemberDto detailMember(String userId) throws SQLException;

	int modifyMember(MemberDto memberDto) throws SQLException;

	int deleteMember(String id) throws SQLException;

	List<MemberDto> userList(String userId) throws SQLException;

	MemberDto adminUserInfo(String id) throws SQLException;

}
