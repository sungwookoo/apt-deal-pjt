package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.HashMap;
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
	public MemberDto loginMember(MemberDto memberDto) throws SQLException {
		if (memberDto.getUserId() == null || memberDto.getUserPassword() == null)
			return null;
		return memberDao.loginMember(memberDto);
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

	@Override
	public int saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		return memberDao.saveRefreshToken(map);
//		sqlSession.getMapper(MemberMapper.class).saveRefreshToken(map);
	}
	
	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberDao.getRefreshToken(userId);
	}

	@Override
	public MemberDto userInfo(String userId) throws Exception {
		MemberDto dto = memberDao.userInfo(userId);
		return dto;
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberDao.deleteRefreshToken(map);
//		sqlSession.getMapper(MemberMapper.class).deleteRefreshToken(map);
	}
	
	@Override
	public String loginNaver(String id) {
		return memberDao.loginNaver(id);
	}
	
	@Override
	public int connectNaver(MemberDto memberDto) {
		return memberDao.connectNaver(memberDto);
	}
	
	@Override
	public int unconnectNaver(String userId) {
		return memberDao.unconnectNaver(userId);
	}

}