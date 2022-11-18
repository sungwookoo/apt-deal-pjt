package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.MemberDto;

public interface MemberService {

	MemberDto loginMember(MemberDto memberDto) throws SQLException;

	int joinMember(MemberDto memberDto) throws SQLException;

	String findPwd(MemberDto memberDto) throws SQLException;

	MemberDto detailMember(String userId) throws SQLException;

	int modifyMember(MemberDto memberDto) throws SQLException;

	int deleteMember(String id) throws SQLException;

	List<MemberDto> userList(String userId) throws SQLException;

	MemberDto adminUserInfo(String id) throws SQLException;

	int saveRefreshToken(String userId, String refreshToken) throws Exception;

	Object getRefreshToken(String userId) throws Exception;

	int deleteRefreshToken(String userId) throws Exception;

	MemberDto userInfo(String userid) throws Exception;

	void deleRefreshToken(String userId) throws Exception;

}
