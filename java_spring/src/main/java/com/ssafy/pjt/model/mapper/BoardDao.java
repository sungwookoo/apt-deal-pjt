package com.ssafy.pjt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.model.dto.BoardDto;

public interface BoardDao {

	List<BoardDto> listBoard(Map<String, String> map) throws SQLException;

	int writeBoard(BoardDto boardDto) throws SQLException;

	BoardDto getBoard(int boardNo) throws SQLException;

	int updateHit(int boardNo) throws SQLException;

	int modifyBoard(BoardDto boardDto) throws SQLException;

	int deleteBoard(int articleNo) throws SQLException;

	List<BoardDto> searchBoard(String keyword) throws SQLException;

}
