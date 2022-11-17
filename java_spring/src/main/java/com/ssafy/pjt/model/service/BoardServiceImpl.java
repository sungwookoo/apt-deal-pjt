package com.ssafy.pjt.model.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pjt.model.dto.BoardDto;
import com.ssafy.pjt.model.mapper.BoardDao;
import com.ssafy.pjt.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService {
	private BoardDao boardDao;
		
	@Autowired
	private BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Override
	public List<BoardDto> listBoard(Map<String, String> map) throws SQLException {
		return boardDao.listBoard(map);
	}
	
	@Override
	public void writeBoard(BoardDto boardDto) throws SQLException {
		boardDao.writeBoard(boardDto);
	}
	
	@Override
	public BoardDto getBoard(int boardNo) throws SQLException {
		return boardDao.getBoard(boardNo);
	}
	
	@Override
	public void updateHit(int boardNo) throws SQLException {
		boardDao.updateHit(boardNo);
	}
	@Override
	public void modifyBoard(BoardDto boardDto) throws SQLException {
		boardDao.modifyBoard(boardDto);
	}
	
	@Override
	public void deleteBoard(int articleNo) throws SQLException {
		boardDao.deleteBoard(articleNo);
	}
	
	@Override
	public List<BoardDto> getSearchBoard(String keyword) throws SQLException {
		List<BoardDto> list = null;
		
		list = boardDao.searchBoard(keyword);
		
		Collections.sort(list, (o1, o2) -> kmp(o2, keyword) - kmp(o1, keyword));
		
		return list;
	}

	private int kmp(BoardDto dto, String keyword) {
		int cnt = 0;
		
		StringBuilder sb = new StringBuilder();
		sb.append(dto.getSubject()).append(" ").append(dto.getContent());
		String str = sb.toString();
		
		int[] table = makeTable(keyword);
		
		int n1 = str.length();
		int n2 = keyword.length();
		int begin = 0, matched = 0;
		while(begin <= n1 - n2) {
			System.out.println(1);
			if(matched < n2 && str.charAt(begin+matched) == keyword.charAt(matched)) {
				++matched;
				
				if(matched == n2) {
					cnt++;
					begin += matched;
					matched = 0;
				}
			}else {
				if(matched == 0) {
					begin++;
				}else {
					begin += matched - table[matched-1];
					matched = table[matched-1];
				}
			}
		}
		
		return cnt;
	}

	private int[] makeTable(String keyword) {
		int n = keyword.length();
		int[] table = new int[n];
			
		int idx=0;
		for(int i=1; i<n; i++) {
			while(idx>0 && keyword.charAt(i) != keyword.charAt(idx)) {
				idx = table[idx-1];
			}
				
			if(keyword.charAt(i) == keyword.charAt(idx)) {
				idx += 1;
				table[i] = idx;  
			}
		}
		return table;
	}
}