package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.MemberDto;
import com.ssafy.pjt.model.mapper.MemberDao;

@Service
public class MemberServiceImpl implements MemberService {
	private MemberDao memberDao;
	
	@Autowired
	private MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Override
	public MemberDto loginMember(Map<String, String> map) throws SQLException {
		return memberDao.loginMember(map);
	}
	
	@Override
	public int joinMember(MemberDto memberDto) throws SQLException {
		return memberDao.joinMember(memberDto);
	}
	
	@Override
	public String findPwd(MemberDto memberDto) throws SQLException {
		return memberDao.findPwd(memberDto);
	}
	
	@Override
	public MemberDto detailMember(String userId) throws SQLException {
		return memberDao.detailMember(userId);
	}
	
	@Override
	public int modifyMember(MemberDto memberDto) throws SQLException {
		return memberDao.modifyMember(memberDto);
	}
	
	@Override
	public int deleteMember(String user_id) throws SQLException {
		return memberDao.deleteMember(user_id);
	}
	
	@Override
	public List<MemberDto> userList(String userId) throws SQLException {
		List<MemberDto> list = memberDao.userList(userId);
		return list;
	}
	
	@Override
	public MemberDto adminUserInfo(String id) throws SQLException {
		MemberDto dto = memberDao.adminUserInfo(id);
		return dto;
	}
}