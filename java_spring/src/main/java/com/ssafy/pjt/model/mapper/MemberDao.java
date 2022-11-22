package com.ssafy.pjt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.MemberDto;

public interface MemberDao {

	MemberDto loginMember(MemberDto memberDto) throws SQLException;

	int joinMember(MemberDto memberDto) throws SQLException;

	String findPwd(MemberDto memberDto) throws SQLException;

	MemberDto detailMember(String userId) throws SQLException;

	int modifyMember(MemberDto memberDto) throws SQLException;

	int deleteMember(String userId) throws SQLException;

	List<MemberDto> userList(String userId) throws SQLException;

	MemberDto adminUserInfo(String id) throws SQLException;

	int saveRefreshToken(Map<String, String> map) throws SQLException;

	Object getRefreshToken(String userId) throws SQLException;

	MemberDto userInfo(String userId) throws SQLException;

	void deleteRefreshToken(Map<String, String> map) throws SQLException;

	String loginNaver(String id);

	int connectNaver(MemberDto memberDto);

}
